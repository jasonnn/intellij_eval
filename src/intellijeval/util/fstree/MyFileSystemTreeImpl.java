package intellijeval.util.fstree;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.ex.FileSystemTreeImpl;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.util.containers.Convertor;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public
class MyFileSystemTreeImpl extends FileSystemTreeImpl {
    public
    MyFileSystemTreeImpl(@Nullable Project project,
                         FileChooserDescriptor descriptor) {
        super(project, descriptor);
        System.out.println("MyFileSystemTreeImpl.MyFileSystemTreeImpl");
    }

    public
    MyFileSystemTreeImpl(@Nullable Project project,
                         FileChooserDescriptor descriptor,
                         Tree tree,
                         @Nullable TreeCellRenderer renderer,
                         @Nullable Runnable onInitialized,
                         @Nullable Convertor<TreePath, String> speedSearchConverter) {
        super(project, descriptor, tree, renderer, onInitialized, speedSearchConverter);
        System.out.println("MyFileSystemTreeImpl.MyFileSystemTreeImpl(....)");
    }
}
