package intellijeval.toolwindow2;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import intellijeval.EvalComponent;
import intellijeval.Util;
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

        Collection<String> pluginPaths = EvalComponent.pluginIdToPathMap().values();
        List<VirtualFile> files = convertPathsToVFS(pluginPaths);

         setRoots(files);

    }

    private static List<VirtualFile> convertPathsToVFS(Collection<String>paths){
        if(paths.isEmpty()) return Collections.emptyList();
        List<VirtualFile> ret = new ArrayList<VirtualFile>(paths.size());
         VirtualFileManager mgr = VirtualFileManager.getInstance();
        for(String path: paths){
           ret.add(mgr.findFileByUrl("file://"+path));
        }

        return ret;

    }

    @Override
    public Icon getIcon(VirtualFile file) {
        if (EvalComponent.pluginIdToPathMap().values().contains(file.getPath())) return Util.PLUGIN_ICON;
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
