package intellijeval.project;

import com.google.common.cache.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import groovy.lang.GroovyClassLoader;
import intellijeval.EvalAppService;
import intellijeval.project.script.EvalPluginFactory;
import intellijeval.project.script.EvalPluginFactoryImpl;
import intellijeval.project.toolwindow2.EvalToolWindow;
import intellijeval.util.map.EvalBindingsMap;
import intellijeval.util.RefType;
import intellijeval.util.map.cache.ObservableCache;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalProjectService implements Disposable.Parent {
    private final EvalToolWindow window;
    private final Project project;


    //TODO: for adding project/module libraries.
    private GroovyClassLoader projectClassLoader;

    private EvalBindingsMap projectBindings;
    private  ObservableCache<String, Object> projectBindings2;


    private final RemovalListener<String,EvalPlugin> removalListener = new RemovalListener<String, EvalPlugin>() {
        @Override
        public void onRemoval(RemovalNotification<String, EvalPlugin> notification) {
            handleRemove(notification.getValue());
        }
    } ;



    private final LoadingCache<String,EvalPlugin> pluginCache =
            CacheBuilder.newBuilder()
            .weakValues()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .removalListener(removalListener)
            .build(new CacheLoader<String, EvalPlugin>() {
                @Override
                public EvalPlugin load(String s) throws Exception {
                    return pluginFactory.createPlugin(s);
                }
            });


     private final EvalPluginFactory pluginFactory;


    public EvalProjectService(Project project) {
        this.project = project;
        this.window = new EvalToolWindow(project);
        this.projectClassLoader = new GroovyClassLoader(EvalAppService.getInstance().getAppClassLoader());
        this.projectBindings = new EvalBindingsMap(new WeakHashMap<String, Object>(), RefType.WEAK);
         this.projectBindings2 = new ObservableCache<String, Object>();
        this.pluginFactory=new EvalPluginFactoryImpl(this);

    }




    private void handleRemove(EvalPlugin value) {
        Disposer.dispose(value);
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

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeTreeDispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
