package intellijeval.project;

import com.google.common.cache.*;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import intellijeval.EvalAppService;
import intellijeval.project.script.EvalPluginFactory;
import intellijeval.project.script.EvalPluginFactoryImpl;
import intellijeval.project.toolwindow2.EvalToolWindow;
import intellijeval.util.RefType;
import intellijeval.util.map.EvalBindingsMap;
import intellijeval.util.map.ObservableMap;
import intellijeval.util.cache.ObservableCache;

import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public
class EvalProjectService implements Disposable.Parent {

    private static final Logger log = Logger.getLogger(EvalProjectService.class.getName());
    private final EvalToolWindow window;
    private final Project project;
    private final RemovalListener<String, EvalPlugin> removalListener = new RemovalListener<String, EvalPlugin>() {
        @Override
        public
        void onRemoval(RemovalNotification<String, EvalPlugin> notification) {
            handleCacheEviction(notification.getValue());
        }
    };
    private final LoadingCache<String, EvalPlugin> pluginCache =
            CacheBuilder.newBuilder()
                    //.weakValues()
                    .expireAfterAccess(5, TimeUnit.MINUTES)
                    .removalListener(removalListener)
                    .build(new CacheLoader<String, EvalPlugin>() {
                        @Override
                        public
                        EvalPlugin load(String s) throws Exception {
                            return pluginFactory.createPlugin(s);
                        }
                    });
    private final EvalPluginFactory pluginFactory;
    //TODO: implement
    private final
    ObservableMap.VetoableListener<String, Object> mapListener = new ObservableMap.ListenerAdapter<String, Object>();
    //TODO: for adding project/module libraries.
    private GroovyClassLoader projectClassLoader;
    private EvalBindingsMap projectBindings;



    private ObservableCache<String, Object> projectBindings2;

    public
    EvalProjectService(Project project) {
        this.project = project;
        this.window = new EvalToolWindow(project);
        this.projectClassLoader = new GroovyClassLoader(EvalAppService.getInstance().getAppClassLoader());
        this.projectBindings = new EvalBindingsMap(new WeakHashMap<String, Object>(), RefType.WEAK);
        this.projectBindings2 = new ObservableCache<String, Object>();
        this.pluginFactory = new EvalPluginFactoryImpl(this);

    }



    public
    ObservableMap.Listener<String, Object> getMapListener() {
        return mapListener;
    }

    public
    void observeMap(ObservableMap<String, Object> map) {
        map.addListener(mapListener);
    }

    public
    EvalPlugin getPlugin(String name) throws ExecutionException {
        return pluginCache.get(name);
    }

    public
    void disposePlugin(String name) {
        pluginCache.invalidate(name);
    }

    private
    void handleCacheEviction(EvalPlugin value) {
        Disposer.dispose(value);
    }

    public
    GroovyClassLoader getProjectClassLoader() {
        return projectClassLoader;
    }

    public
    EvalToolWindow getWindow() {
        return window;
    }

    public
    Project getProject() {
        return project;
    }

    public
    EvalBindingsMap getProjectBindings() {
        return projectBindings;
    }

    public
    ObservableCache<String, Object> getProjectBindings2() {
        return projectBindings2;
    }

    @Override
    public
    void dispose() {
        log.warning("implement!"); //TODO
    }

    @Override
    public
    void beforeTreeDispose() {
        log.warning("implement?"); //TODO
    }

    public static
    class SERVICE {
        private
        SERVICE() {
        }

        public static
        EvalProjectService getInstance(Project project) {
            return ServiceManager.getService(project, EvalProjectService.class);
        }

    }
}
