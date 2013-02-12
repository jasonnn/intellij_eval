package intellijeval.project.toolwindow2;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalWindowFactory implements ToolWindowFactory,DumbAware {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        System.out.println("EvalWindowFactory.createToolWindowContent");
         EvalToolWindow window = EvalToolWindow.getInstance(project);
       // EvalToolWindow window = new EvalToolWindow(project);
        Content content = ContentFactory.SERVICE.getInstance().createContent(window,"",false);


        
        
        toolWindow.getContentManager().addContent(content);

    }
}
