package intellijeval.tmp.browser;

import ch.randelshofer.quaqua.JBrowser;
import ch.randelshofer.quaqua.JBrowserViewport;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
public
class MyToolWindow {


    private JComponent component;

    public MyToolWindow(){

         initUI();


    }


    private void initUI(){
        component = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JBScrollPane();
         component.add(scrollPane,BorderLayout.CENTER);
        JBrowser browser = new JBrowser();
        browser.setPreviewRenderer(new MyPreviewRenderer());

        scrollPane.setViewport(new JBrowserViewport());
        scrollPane.setViewportView(browser);
    }

    public
    JComponent getComponent() {
        return component;
    }

    public static abstract class SERVICE{
      private SERVICE(){}

        public static MyToolWindow getInstance(Project project){
            return ServiceManager.getService(project,MyToolWindow.class);
        }
    }
}
