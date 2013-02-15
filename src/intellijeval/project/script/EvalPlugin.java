package intellijeval.project.script;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import intellijeval.EvalAppService;
import intellijeval.project.EvalProjectService;
import intellijeval.util.map.EvalBindingsMap;
import intellijeval.util.map.cache.CacheMapAdapterThingForBinding;

import java.net.URI;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class EvalPlugin implements Disposable {
    private final String id;
    private final URI pluginBase;
    private final EvalContext context;

    private Project project;

    private EvalProjectService projectService;
    private EvalAppService appService;

    private GroovyClassLoader loader;
    private GroovyScriptEngine engine;

    private EvalBindingsMap projectBindingContributions;
    private EvalBindingsMap appBindingsContributions;

    private CacheMapAdapterThingForBinding projectContributions;

    public EvalPlugin(String id, URI pluginBase, EvalContext context, Project project) {
        this.id = id;
        this.pluginBase = pluginBase;
        this.context = context;
        this.project = project;
//        this.appService=EvalAppService.getInstance();
//        this.projectService=EvalProjectService.getInstance(project);

        init();
    }
    public EvalPlugin(String id, String basePath, EvalContext context,Project project){
        this(id,URI.create(basePath), context, project);
    }

    public EvalPlugin(String id,String basePath,EvalContext context,EvalProjectService projectService,EvalAppService appService){
        this(id,URI.create(basePath), context, projectService.getProject());
        this.projectService=projectService;
        this.appService=appService;

       projectContributions=new CacheMapAdapterThingForBinding(projectService.getProjectBindings2(),new HashMap<String, Object>());

    }

    private void init(){
        loader = new GroovyClassLoader(EvalProjectService.getInstance(project).getProjectClassLoader());

    }



    public Binding createBinding(){


        return null;//TODO
    }

    public String getId() {
        return id;
    }

    public URI getPluginBase() {
        return pluginBase;
    }

    public EvalContext getContext(){
        return context;
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
