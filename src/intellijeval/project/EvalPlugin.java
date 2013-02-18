package intellijeval.project;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import intellijeval.EvalAppService;
import intellijeval.project.script.ctx.EvalContext;
import intellijeval.util.map.EvalBindingsMap;
import intellijeval.util.map.cache.CacheMapAdapterThingForBinding;

import java.net.URI;

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
    private final String id;
    private final URI pluginBase;
    private final EvalContext context;
    private final Project project;
    private final EvalProjectService projectService;
    private final EvalAppService appService;
    private GroovyClassLoader loader;
    private GroovyScriptEngine engine;
    private EvalBindingsMap projectBindingContributions;
    private EvalBindingsMap appBindingsContributions;
    private CacheMapAdapterThingForBinding projectContributions;


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
    }

    public
    Binding createBinding() {


        return null;//TODO
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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
