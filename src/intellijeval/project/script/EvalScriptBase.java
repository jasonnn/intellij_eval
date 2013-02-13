package intellijeval.project.script;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.Script;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EvalScriptBase extends Script {
    
    private EvalContext ctx;

    protected EvalScriptBase(Binding binding) {
        super(binding);
        initFieldsFromBinding();

    }

    private void initFieldsFromBinding(){
      ctx= (EvalContext) getBinding().getVariable("ctx");
    }

    @Override
    public void setBinding(Binding binding) {
        super.setBinding(binding);
        initFieldsFromBinding();
    }


    public <T> T runOnce(String id,Closure<T>closure){
        return ctx.runOnce(id,closure);
    }

    @Override
    public void println() {
        if(ctx ==null)super.println();
        else ctx.println("\n");
    }

    @Override
    public void print(Object value) {
      if(ctx ==null)  super.print(value);
      else ctx.print(value);
    }

    @Override
    public void println(Object value) {
      if(ctx ==null) super.println(value);
      else ctx.println(value);
    }

    @Override
    public void printf(String format, Object value) {
       if(ctx ==null) super.printf(format, value);
        else ctx.printf(format,value);
    }

    @Override
    public void printf(String format, Object[] values) {
       if(ctx ==null) super.printf(format, values);
        else ctx.printf(format,values);
    }
}
