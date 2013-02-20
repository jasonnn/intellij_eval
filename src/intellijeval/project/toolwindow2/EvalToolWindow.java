package intellijeval.project.toolwindow2;

import com.intellij.ide.DataManager;
import com.intellij.ide.actions.CollapseAllAction;
import com.intellij.ide.actions.ExpandAllAction;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileChooser.FileSystemTree;
import com.intellij.openapi.fileChooser.FileSystemTreeFactory;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.util.EditSourceOnEnterKeyHandler;
import com.intellij.util.OpenSourceUtil;
import intellijeval.Util;
import intellijeval.project.PluginUtil;
import intellijeval.project.RunPluginAction;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 *
 * @see {@link com.intellij.platform.FilesystemToolwindow}
 */
public
class EvalToolWindow implements Disposable {
    private static final Logger log = Logger.getLogger(EvalToolWindow.class.getName());
    public static final
    Key<EvalToolWindow> EVAL_TOOL_WINDOW_KEY = Key.create(EvalToolWindow.class.getName());  //TODO:what??
    private static final String ServiceID = "EvalWindow";
    private FileSystemTree fsTree;
    private MyContentPanel contentPanel;
    private Project project;
    private String selectedPluginId;

    public
    EvalToolWindow(Project project) {


        this.project = project;
        this.contentPanel = new MyContentPanel();

        EvalFileChooserDescriptor descriptor = new EvalFileChooserDescriptor();
        descriptor.setRoots(PluginUtil.getDefaultRoot()); //TODO: ctor param

        FileSystemTreeFactory factory = FileSystemTreeFactory.SERVICE.getInstance();
        fsTree = factory.createFileSystemTree(project, descriptor);
        DefaultActionGroup actionGroup = factory.createDefaultFileSystemActions(fsTree);
        contentPanel.setToolbar(createToolbar(actionGroup));
        contentPanel.setContent(ScrollPaneFactory.createScrollPane(fsTree.getTree()));

        intsallOpenFileHandlers(fsTree.getTree());
        //TODO: is this still necessary?
        //TODO: this isnt working either
        //why is fsTree always showing null for selectedFiles?
        fsTree.addListener(new FileSystemTree.Listener() {
            @Override
            public
            void selectionChanged(List<VirtualFile> selection) {
                if (selection.isEmpty()) return;
                VirtualFile dir = PluginUtil.pluginFolderOf(selection.get(0));
                selectedPluginId = PluginUtil.virtualFile2Id(dir);
                System.out.println("selectedPluginId = " + selectedPluginId);
            }
        }, this);

        fsTree.getTree().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    private static
    void intsallOpenFileHandlers(JTree component) {
        EditSourceOnEnterKeyHandler.install(component);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public
            void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DataContext dataContext = DataManager.getInstance().getDataContext((Component) e.getSource());
                    Project p = PlatformDataKeys.PROJECT.getData(dataContext);
                    assert p != null;
                    OpenSourceUtil.openSourcesFrom(dataContext, false);
                }
            }
        });

    }

    private static
    AnAction withIcon(Icon icon, AnAction action) {
        action.getTemplatePresentation().setIcon(icon);
        return action;
    }

    private static
    JComponent createToolbar(DefaultActionGroup actionGroup) {
        actionGroup.add(new RunPluginAction(), Constraints.FIRST);
        actionGroup.addSeparator();
        actionGroup.add(withIcon(Util.EXPAND_ALL_ICON, new ExpandAllAction()));
        actionGroup.add(withIcon(Util.COLLAPSE_ALL_ICON, new CollapseAllAction()));

        JPanel toolbarPanel = new JPanel(new GridLayout());
        String place = ActionPlaces.EDITOR_TOOLBAR;
        toolbarPanel.add(ActionManager.getInstance().createActionToolbar(place, actionGroup, true).getComponent());
        return toolbarPanel;
    }

    public
    MyContentPanel getContentPanel() {
        return contentPanel;
    }

    public
    String getSelectedPluginId() {
        return selectedPluginId;
    }

//    public
//    Collection<String> getSelectedPluginsIDs() {
//        VirtualFile[] selected = (VirtualFile[]) selectedFiles.toArray();// fsTree.getSelectedFiles();
//        //TODO: this is also ugly
//        return virtualFilesToIDs(findPluginRootsFor(selected));
//    }
//
//    public
//    Collection<URI> getSelectedPluginsBasePaths() {
//        VirtualFile[] selected = (VirtualFile[]) selectedFiles.toArray();// fsTree.getSelectedFiles();
//
//        //TODO: this is ugly!
//        return virtualFilesToURI(findPluginRootsFor(selected));
//    }

    @Override
    public
    void dispose() {
        log.log(Level.WARNING,"not yet implemented");
        //TODO
    }

//    @Deprecated
//    public static
//    class SERVICE {
//        private
//        SERVICE() {
//        }
//        @Deprecated
//        public static
//        EvalToolWindow getInstance(Project project) {
//            return ServiceManager.getService(project, EvalToolWindow.class);
//        }
//    }

    class MyContentPanel extends SimpleToolWindowPanel {
        public
        MyContentPanel() {
            super(true);
            setProvideQuickActions(false);
//            JScrollPane scrollPane = new JBScrollPane(fsTree.getTree());
//            setContent(scrollPane);


        }

        @Nullable
        public
        Object getData(@NonNls final String dataId) {
            // System.out.println("dataId = [" + dataId + "]");
            if (PlatformDataKeys.NAVIGATABLE.is(dataId)) {
                final VirtualFile file = fsTree.getSelectedFile();
                if (file != null) {
                    return new OpenFileDescriptor(project, file);
                }
            }
            else if (PlatformDataKeys.VIRTUAL_FILE.is(dataId)) {
                return fsTree.getSelectedFile();
            }
            return super.getData(dataId);
        }
    }
}
