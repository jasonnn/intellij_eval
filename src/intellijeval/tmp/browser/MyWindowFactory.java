package intellijeval.tmp.browser;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public
class MyWindowFactory implements ToolWindowFactory {
    @Override
    public
    void createToolWindowContent(Project project, ToolWindow toolWindow) {
        MyToolWindow window = MyToolWindow.SERVICE.getInstance(project);
        Content content = ContentFactory.SERVICE.getInstance().createContent(window.getComponent(), "", false);
        toolWindow.getContentManager().addContent(content);

    }
}
