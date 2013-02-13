package intellijeval.project;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import intellijeval.util.MyUncaughtExceptionHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalProjectComponent implements ProjectComponent {
    private final Project project;

    static {
        MyUncaughtExceptionHandler.initDefault();
    }

    public EvalProjectComponent(Project project) {
        this.project = project;
        System.out.println("EvalProjectComponent.EvalProjectComponent");
    }

    public void initComponent() {
        System.out.println("thread handler = " + Thread.currentThread().getUncaughtExceptionHandler());
    }

    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @NotNull
    public String getComponentName() {
        return "EvalProjectComponent";
    }

    public void projectOpened() {
            throw new RuntimeException("FAIL");
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
