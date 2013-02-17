package intellijeval.project.script;

import groovy.lang.Script;


import groovy.lang.*;
import groovy.util.slurpersupport.GPathResult;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EvalScriptBase extends Script {

    private EvalContext ctx;

    protected EvalScriptBase() {
        System.out.println("EvalScriptBase.EvalScriptBase");
        setMetaClass(getMetaClass());

    }

    protected EvalScriptBase(Binding binding) {
        super(binding);
        System.out.println("EvalScriptBase.EvalScriptBase(Binding)");

        getCtx();
        setMetaClass(getMetaClass());
    }



    private void getCtx() {
        Binding b = getBinding();
         if (b.hasVariable("ctx")) ctx= (EvalContext) b.getVariable("ctx");
    }

    @Override
    public void setBinding(Binding binding) {
        super.setBinding(binding);
        getCtx();
    }

    public <T> T runOnce(String id, Closure<T> closure) {
        return ctx.runOnce(id, closure);
    }


    //see groovy.util.slurpersupport.GPathResult#setMetaClass
    @Override
    public void setMetaClass(MetaClass metaClass) {
        System.out.println("EvalScriptBase.setMetaClass");
        MetaClass newMetaClass = new DelegatingMetaClass(metaClass){
            @Override
            public Object getAttribute(Object object, String attribute) {
              return EvalScriptBase.this.getProperty("@"+attribute);
            }

            @Override
            public void setAttribute(final Object object, final String attribute, final Object newValue) {
                EvalScriptBase.this.setProperty("@" + attribute, newValue);
            }
        }  ;

        super.setMetaClass(newMetaClass);
    }

    public Object propertyMissing(String name) throws MissingPropertyException {
//        System.out.println("EvalScriptBase.propertyMissing[getter]");
//        System.out.println("name = [" + name + "]");
        if (ctx != null) {
            return ctx.handlePropertyMissing(name);
        } else {
            throw new MissingPropertyException(name, getClass());
        }

    }

    public Object propertyMissing(String name, Object value) throws MissingPropertyException{
//        System.out.println("EvalScriptBase.propertyMissing[setter]");
//        System.out.println("name = [" + name + "], value = [" + value + "]");
        if (ctx != null) {
           return ctx.handlePropertyMissing(name,value);
        } else {
            throw new MissingPropertyException(name,getClass());
        }
    }

    public Object methodMissing(String name,Object args){
//        System.out.println("EvalScriptBase.methodMissing");
//        System.out.println("name = [" + name + "], args = [" + args + "]");
        if (ctx != null) {
          return  ctx.handleMethodMissing(name, (Object[]) args);
        } else {
            throw new MissingMethodException(name,getClass(), (Object[]) args);
        }
    }

    @Override
    public void println() {
        if (ctx == null) super.println();
        else ctx.println("\n");
    }

    @Override
    public void print(Object value) {
        if (ctx == null) super.print(value);
        else ctx.print(value);
    }

    @Override
    public void println(Object value) {
        if (ctx == null) super.println(value);
        else ctx.println(value);
    }

    @Override
    public void printf(String format, Object value) {
        if (ctx == null) super.printf(format, value);
        else ctx.printf(format, value);
    }

    @Override
    public void printf(String format, Object[] values) {
        if (ctx == null) super.printf(format, values);
        else ctx.printf(format, values);
    }
}
