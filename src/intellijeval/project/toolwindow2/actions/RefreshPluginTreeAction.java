package intellijeval.project.toolwindow2.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.RefreshQueue;
import intellijeval.EvalComponent;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RefreshPluginTreeAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                VirtualFile pluginsRoot = VirtualFileManager.getInstance().findFileByUrl("file://" + EvalComponent.pluginsRootPath());
                if (pluginsRoot == null) return;

                RefreshQueue.getInstance().refresh(false,true,new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                    }
                },pluginsRoot);

            }
        });
    }
}
