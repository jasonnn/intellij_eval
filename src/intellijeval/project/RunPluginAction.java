package intellijeval.project;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import intellijeval.Util;
import intellijeval.project.toolwindow2.EvalToolWindow;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public
class RunPluginAction extends AnAction {
    private static final Logger log = Logger.getLogger(RunPluginAction.class.getName());

    public
    RunPluginAction() {
        super("Run Plugin", "Run selected plugins", Util.EVAL_ICON);
    }

    @Override
    public
    void actionPerformed(AnActionEvent e) {
        doEval(e);

    }

    private
    void doEval(AnActionEvent event) {
        EvalProjectService projectService = EvalProjectService.SERVICE.getInstance(event.getProject());
        EvalToolWindow window = projectService.getWindow();
        String id = window.getSelectedPluginId();
        if (id == null) {
            log.warning("id was NULL!. Not executing plugin.");
            return;
        }
        //Collection<String> ids = window.getSelectedPluginsIDs();
//        if(ids.isEmpty()) return;
//        String first = ids.iterator().next();
        EvalPlugin plugin;
        try {
            plugin = projectService.getPlugin(id);
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        plugin.runAsync(event);
    }
}
