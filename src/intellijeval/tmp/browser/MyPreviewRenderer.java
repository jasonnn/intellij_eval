package intellijeval.tmp.browser;

import ch.randelshofer.quaqua.BrowserPreviewRenderer;
import ch.randelshofer.quaqua.JBrowser;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 9:40 PM
 * To change this template use File | Settings | File Templates.
 */
public
class MyPreviewRenderer extends JLabel implements BrowserPreviewRenderer {
    @Override
    public
    Component getPreviewRendererComponent(JBrowser browser, TreePath[] paths) {
        String text = paths.length == 1 ? paths[0].getLastPathComponent().toString()
                                        : paths.length + " selected items";
        setText(text);
        return this;
    }
}
