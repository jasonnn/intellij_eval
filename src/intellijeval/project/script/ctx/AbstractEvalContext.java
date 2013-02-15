package intellijeval.project.script.ctx;

import intellijeval.project.script.EvalContext;
import intellijeval.project.script.ScriptOutputHandler;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/13/13
 * Time: 9:30 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEvalContext implements EvalContext{

    private ScriptOutputHandler outputHandler;

    protected AbstractEvalContext(ScriptOutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    @Override
    public void println(Object o) {
        outputHandler.println(o);
    }

    @Override
    public void print(Object value) {
        outputHandler.print(value);
    }

    @Override
    public void printf(String format, Object value) {
        outputHandler.printf(format, value);
    }

    @Override
    public void printf(String format, Object[] values) {
        outputHandler.printf(format, values);
    }
}
