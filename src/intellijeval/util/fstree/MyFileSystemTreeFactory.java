package intellijeval.util.fstree;

import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileSystemTree;
import com.intellij.openapi.fileChooser.ex.FileSystemTreeFactoryImpl;
import com.intellij.openapi.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public
class MyFileSystemTreeFactory extends FileSystemTreeFactoryImpl {
    @Override
    public
    FileSystemTree createFileSystemTree(Project project, FileChooserDescriptor fileChooserDescriptor) {
        return null;  //TODO:IMPLEMENT
    }

    @Override
    public
    DefaultActionGroup createDefaultFileSystemActions(FileSystemTree fileSystemTree) {
        return null;  //TODO:IMPLEMENT
    }
}
