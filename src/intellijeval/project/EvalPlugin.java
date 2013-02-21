package intellijeval.project;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import groovy.lang.*;
import groovy.util.GroovyScriptEngine;
import intellijeval.EvalAppService;
import intellijeval.ExecutionResult;
import intellijeval.project.script.DefaultBindingKeys;
import intellijeval.project.script.EvalBinding;
import intellijeval.project.script.ScriptUtil;
import intellijeval.project.script.ctx.EvalContext;
import intellijeval.util.map.EvalBindingsMap;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */

//TODO: this class is particularly bad
//TODO: also look into using the ij message system to synchronize map views
// http://confluence.jetbrains.com/display/IDEADEV/IntelliJ+IDEA+Messaging+infrastructure
public
class EvalPlugin implements Disposable {
    private static final Logger log = Logger.getLogger(EvalPlugin.class.getName());
    private final String id;
    private final URI pluginBase;
    private final EvalContext context;
    private final Project project;
    private final EvalProjectService projectService;
    private final EvalAppService appService;
    private GroovyClassLoader loader;
    private GroovyScriptEngine engine;
    private EvalBindingsMap projectBindingContributions;
    private EvalBindingsMap pluginBindings;
    //  private EvalBindingsMap appBindingsContributions;  //todo
    private
    ListeningExecutorService runner;

    public
    EvalPlugin(String id,
               URI pluginBase,
               EvalContext context,
               Project project,
               EvalProjectService projectService,
               EvalAppService appService) {

        this.id = id;
        this.pluginBase = pluginBase;
        this.context = context;
        this.project = project;
        this.projectService = projectService;
        this.appService = appService;
        this.runner = appService.getPluginRunner();

        initGroovy();
        initBindings();
    }

    public
    EvalPlugin(File dir, EvalContext ctx, EvalProjectService projectService, EvalAppService appService) {
        this(PluginUtil.dir2Id(dir), dir.toURI(), ctx, projectService.getProject(), projectService, appService);

    }

    public
    EvalPlugin(File dir, EvalContext ctx, EvalProjectService projectService) {
        this(dir, ctx, projectService, EvalAppService.getInstance());
    }

    private
    void initGroovy() {
        CompilerConfiguration cc = ScriptUtil.createDefaultCompilerConfiguration();

        loader = new GroovyClassLoader(projectService.getProjectClassLoader());
        engine = new GroovyScriptEngine(PluginUtil.getSubDirs(new File(pluginBase)), loader);
        engine.setConfig(cc);

    }

    private
    void initBindings() {
        this.projectBindingContributions = new EvalBindingsMap(new HashMap<String, Object>());
        projectService.observeMap(projectBindingContributions);
        this.pluginBindings = new EvalBindingsMap(new HashMap<String, Object>()){
            @Override
            protected
            Binding createBinding() {
                return new EvalBinding(this){
                    @Override
                    public
                    Object getProperty(String property) {
                        try{
                        return super.getProperty(property);
                        } catch (MissingPropertyException e){
                        if(DefaultBindingKeys.APP.is(property)) return appService; //TODO
                        if(DefaultBindingKeys.PROJECT.is(property)) return projectService.getProjectBindings(); //TODO
                         throw e;
                        }
                    }
                };
            }
        };
    }

    ExecutionResult run(AnActionEvent event) {
        ExecutionResult reporter = new ExecutionResult();
        pluginBindings.getDelegate().put(Constants.kEvent, event);

        try {
            Object result = engine.run(Constants.kPluginScriptName, pluginBindings.getBinding());
            reporter.setResult(result);
        }
        catch (CompilationFailedException e) {
            reporter.addLoadingError(id, "Error while compiling script. " + e.getMessage());
        }
        catch (VerifyError e) {
            reporter.addLoadingError(id, "Error while compiling script. " + e.getMessage());
        }
        catch (Exception e) {
            reporter.addEvaluationException(id, e);
        }
        return reporter;
    }

    Callable<ExecutionResult> createRunCallable(final AnActionEvent event) {
        return new Callable<ExecutionResult>() {
            @Override
            public
            ExecutionResult call() throws Exception {
                return run(event);
            }
        };
    }

    public
    void runAsync(final AnActionEvent event) {
        ListenableFuture<ExecutionResult> resultFuture = runner.submit(createRunCallable(event));
        Futures.addCallback(resultFuture, new FutureCallback<ExecutionResult>() {
            @Override
            public
            void onSuccess(ExecutionResult executionResult) {
                log.info("");
                //TODO
            }

            @Override
            public
            void onFailure(Throwable throwable) {
                log.info("");
                //TODO
            }
        });
    }

    public
    String getId() {
        return id;
    }

    public
    URI getPluginBase() {
        return pluginBase;
    }

    public
    EvalContext getContext() {
        return context;
    }

    @Override
    public
    void dispose() {
        log.warning("implement me"); //TODO
    }

    static
    class Constants {
        public static final String kPluginScriptName = "plugin.groovy";
        public static final String kEvent = "event";
        public static final String kContext = "context";
        public static final String kProject = "project";
        public static final String kApp = "app";
    }
}
