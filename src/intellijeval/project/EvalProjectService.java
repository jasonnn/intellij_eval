package intellijeval.project;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import groovy.lang.GroovyClassLoader;
import intellijeval.EvalAppService;
import intellijeval.project.toolwindow2.EvalToolWindow;
import intellijeval.util.map.EvalBindingsMap;
import intellijeval.util.map.ObservableMap;
import intellijeval.util.RefType;

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

    private EvalBindingsMap projectBindings;

    //TODO: for adding project/module libraries.
    private GroovyClassLoader projectClassLoader;

    private final ObservableMap.Listener<String,Object> bindingsListener = new ObservableMap.Listener<String, Object>() {
        @Override
        public void entryAdded(String key, Object value) {
            projectBindings.put(key,value);
        }

        @Override
        public void entryRemoved(String key, Object value) {
            projectBindings.remove(key);
        }
    };


    public EvalProjectService(Project project){
        this.project=project;
        this.window=new EvalToolWindow(project);
        this.projectBindings=new EvalBindingsMap(new WeakHashMap<String, Object>(), RefType.WEAK);
        this.projectClassLoader=new GroovyClassLoader(EvalAppService.getInstance().getAppClassLoader());
    }

   public void addBindingsContributor(EvalBindingsMap contributor){
      contributor.addListener(this.bindingsListener);
    }

    public void removeBindingsContributor(EvalBindingsMap contributor){
        contributor.removeListener(this.bindingsListener);
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

    public static EvalProjectService getInstance(Project project){
        return ServiceManager.getService(project,EvalProjectService.class);
    }
}
