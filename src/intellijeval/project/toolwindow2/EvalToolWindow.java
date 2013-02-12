package intellijeval.project.toolwindow2;

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
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.EditSourceOnDoubleClickHandler;
import com.intellij.util.ui.tree.TreeUtil;
import intellijeval.EvalComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.net.URI;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalToolWindow extends SimpleToolWindowPanel {
     private static final String ServiceID="EvalWindow";

    private final Ref<FileSystemTree> fsTreeRef = Ref.create();

    public EvalToolWindow(Project project) {
        super(true);
        setProvideQuickActions(false);


        FileSystemTree fsTree = createFSTree(project);
        fsTreeRef.set(fsTree);
        JScrollPane scrollPane = new JBScrollPane(fsTree.getTree());
        setContent(scrollPane);
        


    }

    public Collection<String> getSelectedPluginsIDs(){
        //TODO: this is also ugly
     return virtualFilesToIDs(findPluginRootsFor(fsTreeRef.get().getSelectedFiles()));
    }

    public Collection<URI>  getSelectedPluginsBasePaths(){
        //TODO: this is ugly!
        return virtualFilesToURI(findPluginRootsFor(fsTreeRef.get().getSelectedFiles()));
    }
    private static Collection<String> virtualFilesToIDs(Collection<VirtualFile> files){
        Collection<String> ret = new ArrayList<String>(files.size());
        for(VirtualFile file: files){
            ret.add(file.getName());
        }
        return ret;
    }

    private static Collection<URI> virtualFilesToURI(Collection<VirtualFile> virtualFiles){
        Collection<URI> ret = new ArrayList<URI>(virtualFiles.size());
        for(VirtualFile file:virtualFiles){
            ret.add(URI.create(file.getPath()));
        }

        return ret;
    }

    private static Collection<VirtualFile> findPluginRootsFor(VirtualFile[] files){
        Set<VirtualFile> ret = new HashSet<VirtualFile>();
        for(VirtualFile file: files){
            VirtualFile root = pluginFolderOf(file);
            if(root!=null) ret.add(root);

        }

        return ret;
    }

    private static VirtualFile pluginFolderOf(VirtualFile file){
        VirtualFile parent = file.getParent();
        if(parent==null) return null;
        String base = EvalComponent.pluginsRootPath();

        if(!base.equals(parent.getPath())){
            return pluginFolderOf(parent);

        }
      return file;
    }

    private static JComponent createToolBar(){
        JPanel toolbarPanel = new JPanel(new GridLayout());
          DefaultActionGroup actionGroup = new DefaultActionGroup();


        return toolbarPanel;
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
