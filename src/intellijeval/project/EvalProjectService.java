package intellijeval.project;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import intellijeval.project.toolwindow2.EvalToolWindow;
import intellijeval.util.NonUpdatingMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<String,?> projectBindings;


    public EvalProjectService(Project project){
        this.project=project;
        this.window=new EvalToolWindow(project);
        this.projectBindings=new NonUpdatingMap<String, Object>();
    }

    public EvalToolWindow getWindow() {
        return window;
    }

    public Project getProject() {
        return project;
    }

    public Map<String, ?> getProjectBindings() {
        return projectBindings;
    }

    public static EvalProjectService getInstance(Project project){
        return ServiceManager.getService(project,EvalProjectService.class);
    }
}
