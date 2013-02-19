package intellijeval.project.toolwindow2;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import intellijeval.EvalComponent;
import intellijeval.Util;
import intellijeval.project.PluginUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalFileChooserDescriptor extends FileChooserDescriptor {
    public EvalFileChooserDescriptor() {
        super(true, true, true, false, true, true);

        setShowFileSystemRoots(false);
        setIsTreeRootVisible(false);


    }



    @Override
    public Icon getIcon(VirtualFile file) {
        if (PluginUtil.isPluginFolder(file)) return Util.PLUGIN_ICON;
        return super.getIcon(file);
    }

    @Override
    public String getName(VirtualFile virtualFile) {
        return virtualFile.getName();
    }

    @Nullable
    @Override
    public String getComment(VirtualFile virtualFile) {
        return "";
    }
}
