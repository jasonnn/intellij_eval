package intellijeval.project;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import groovy.lang.GroovyClassLoader;
import intellijeval.EvalAppService;
import intellijeval.project.toolwindow2.EvalToolWindow;
import intellijeval.util.map.EvalBindingsMap;
import intellijeval.util.map.ObservableMap;
import intellijeval.util.RefType;
import intellijeval.util.map.cache.ObservableCache;

import java.util.WeakHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalProjectService {
    private final EvalToolWindow window;
    private final Project project;


    //TODO: for adding project/module libraries.
    private GroovyClassLoader projectClassLoader;

    private EvalBindingsMap projectBindings;
    private  ObservableCache<String, Object> projectBindings2;




    public EvalProjectService(Project project) {
        this.project = project;
        this.window = new EvalToolWindow(project);
        this.projectClassLoader = new GroovyClassLoader(EvalAppService.getInstance().getAppClassLoader());
        this.projectBindings = new EvalBindingsMap(new WeakHashMap<String, Object>(), RefType.WEAK);
         this.projectBindings2 = new ObservableCache<String, Object>();

    }



    public GroovyClassLoader getProjectClassLoader() {
        return projectClassLoader;
    }

    public EvalToolWindow getWindow() {
        return window;
    }

    public Project getProject() {
        return project;
    }

    public EvalBindingsMap getProjectBindings() {
        return projectBindings;
    }

    public ObservableCache<String,Object> getProjectBindings2(){
        return projectBindings2;
    }
    public static EvalProjectService getInstance(Project project) {
        return ServiceManager.getService(project, EvalProjectService.class);
    }
}
