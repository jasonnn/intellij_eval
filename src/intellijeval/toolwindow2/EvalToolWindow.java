package intellijeval.toolwindow2;

import com.intellij.ide.DeleteProvider;
import com.intellij.ide.util.treeView.AbstractTreeBuilder;
import com.intellij.ide.util.treeView.AbstractTreeStructure;
import com.intellij.ide.util.treeView.NodeDescriptor;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileSystemTree;
import com.intellij.openapi.fileChooser.ex.FileNodeDescriptor;
import com.intellij.openapi.fileChooser.ex.FileSystemTreeImpl;
import com.intellij.openapi.fileChooser.ex.RootFileElement;
import com.intellij.openapi.fileChooser.impl.FileTreeBuilder;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.util.Ref;
import com.intellij.pom.Navigatable;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.EditSourceOnDoubleClickHandler;
import com.intellij.util.EditSourceOnEnterKeyHandler;
import com.intellij.util.ui.tree.TreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalToolWindow extends SimpleToolWindowPanel {

    public EvalToolWindow(Project project) {
        super(true);
        setProvideQuickActions(false);

        FileSystemTree fsTree = createFSTree(project);
        JScrollPane scrollPane = new JBScrollPane(fsTree.getTree());
        setContent(scrollPane);
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("e = " + e);
                super.componentShown(e);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("e = " + e);
                super.componentHidden(e);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });

    }

    private static FileSystemTree createFSTree(Project project) {
        Ref<FileSystemTree> fsTreeRef = Ref.create();
        MyTree myTree = new MyTree(project);

        //  EditSourceOnDoubleClickHandler.install(myTree, new DisableHighlightingRunnable(project, myFsTreeRef));
        EditSourceOnDoubleClickHandler.install(myTree);

        FileSystemTree ret = new FileSystemTreeImpl(project, new EvalFileChooserDescriptor(), myTree, null, null, null) {
            @Override
            protected AbstractTreeBuilder createTreeBuilder(JTree tree, DefaultTreeModel treeModel, AbstractTreeStructure treeStructure, Comparator<NodeDescriptor> comparator, FileChooserDescriptor descriptor, @Nullable Runnable onInitialized) {
                return new FileTreeBuilder(tree, treeModel, treeStructure, comparator, descriptor, onInitialized) {
                    @Override
                    protected boolean isAutoExpandNode(NodeDescriptor nodeDescriptor) {
                        return nodeDescriptor.getElement() instanceof RootFileElement;
                    }
                };
            }
        };

        fsTreeRef.set(ret);
      //  EditSourceOnEnterKeyHandler.install(myTree, new DisableHighlightingRunnable(project, myFsTreeRef));
        EditSourceOnDoubleClickHandler.install(myTree);

        return ret;
    }

    public static EvalToolWindow getInstance(Project project) {
        return ServiceManager.getService(project, EvalToolWindow.class);
    }

    private static class MyTree extends Tree implements TypeSafeDataProvider {

        private final Project project;
        private final DeleteProvider deleteWithRefresh = new DelegatingDeleteProvider() {
            @Override
            public void deleteElement(@NotNull DataContext dataContext) {
                super.deleteElement(dataContext);
                //TODO refresh
            }
        };

        private MyTree(Project project) {
            this.project = project;
            getEmptyText().setText("No plugins to show");
            setRootVisible(false);
        }

        @Override
        public void calcData(DataKey key, DataSink sink) {
            if (key == PlatformDataKeys.NAVIGATABLE_ARRAY) { // need this to be able to open files in toolwindow on double-click/enter
                List<FileNodeDescriptor> nodeDescriptors = TreeUtil.collectSelectedObjectsOfType(this, FileNodeDescriptor.class);
                List<Navigatable> navigatables = new ArrayList<Navigatable>();
                for (FileNodeDescriptor nodeDescriptor : nodeDescriptors) {
                    navigatables.add(new OpenFileDescriptor(project, nodeDescriptor.getElement().getFile()));
                }
                sink.put(PlatformDataKeys.NAVIGATABLE_ARRAY, navigatables.toArray(new Navigatable[navigatables.size()]));
            } else if (key == PlatformDataKeys.DELETE_ELEMENT_PROVIDER) {
                sink.put(PlatformDataKeys.DELETE_ELEMENT_PROVIDER, deleteWithRefresh);
            }
        }
    }
}
