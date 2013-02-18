package intellijeval;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.unscramble.UnscrambleDialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/18/13
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public
class ExecutionResult {
    private final List<String> loadingErrors = new LinkedList<String>();
    private final LinkedHashMap<String, Exception> evaluationExceptions = new LinkedHashMap<String, Exception>();
    private Object result;
    private boolean success=true;

    public void addLoadingError(String pluginId, String message) {
        loadingErrors.add("Error loading plugin: \"" + pluginId + "\". " + message);
        success=false;
    }

    public void addEvaluationException(String pluginId, Exception e) {
        //noinspection ThrowableResultOfMethodCallIgnored
        evaluationExceptions.put(pluginId, e);
        success=false;
    }

    public boolean wasSuccess(){
        return success;
    }

    public
    Object getResult() {
        return result;
    }

    public
    void setResult(Object result) {
        this.result = result;
    }

    public void reportLoadingErrors(AnActionEvent actionEvent) {
        StringBuilder text = new StringBuilder();
        for (String s : loadingErrors) text.append(s);
        if (text.length() > 0)
            Util.displayInConsole("Loading errors", text.toString(), ConsoleViewContentType.ERROR_OUTPUT, actionEvent.getData(
                    PlatformDataKeys.PROJECT));
    }

    public void reportEvaluationExceptions(AnActionEvent actionEvent) {
        for (Map.Entry<String, Exception> entry : evaluationExceptions.entrySet()) {
            StringWriter writer = new StringWriter();

            //noinspection ThrowableResultOfMethodCallIgnored
            entry.getValue().printStackTrace(new PrintWriter(writer));
            String s = UnscrambleDialog.normalizeText(writer.getBuffer().toString());

            Util.displayInConsole(entry.getKey(), s, ConsoleViewContentType.ERROR_OUTPUT, actionEvent.getData(PlatformDataKeys.PROJECT));
        }
    }
}
