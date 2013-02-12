package intellijeval.project.script;

import groovy.lang.Binding;
import groovy.lang.Script;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EvalScriptBase extends Script {
    private final ScriptOutputHandler outputHandler;
    protected EvalScriptBase(Binding binding) {
        super(binding);
        outputHandler = (ScriptOutputHandler) binding.getVariable("outputHandler");
    }

    @Override
    public void println() {
        if(outputHandler ==null)super.println();
        else outputHandler.println("\n");
    }

    @Override
    public void print(Object value) {
      if(outputHandler ==null)  super.print(value);
      else outputHandler.print(value);
    }

    @Override
    public void println(Object value) {
      if(outputHandler ==null) super.println(value);
      else outputHandler.println(value);
    }

    @Override
    public void printf(String format, Object value) {
       if(outputHandler ==null) super.printf(format, value);
        else outputHandler.printf(format,value);
    }

    @Override
    public void printf(String format, Object[] values) {
       if(outputHandler ==null) super.printf(format, values);
        else outputHandler.printf(format,values);
    }
}
