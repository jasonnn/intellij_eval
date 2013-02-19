package intellijeval.project;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import intellijeval.Util;
import intellijeval.project.toolwindow2.EvalToolWindow;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class RunPluginAction extends AnAction {

    public RunPluginAction(){
        super("Run Plugin", "Run selected plugins", Util.EVAL_ICON);
    }
    @Override
    public void actionPerformed(AnActionEvent e) {
        doEval(e);

    }

    private void doEval(AnActionEvent event){
        EvalToolWindow window = EvalToolWindow.SERVICE.getInstance(event.getProject());
        Collection<String> ids = window.getSelectedPluginsIDs();
        if(ids.isEmpty()) return;
        String first = ids.iterator().next();
        EvalProjectService projectService = EvalProjectService.SERVICE.getInstance(event.getProject());
        EvalPlugin plugin;
        try {
             plugin = projectService.getPlugin(first);
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        plugin.runAsync(event);
    }
}
