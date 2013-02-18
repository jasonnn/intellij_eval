package intellijeval.project.script;

import com.intellij.openapi.diagnostic.Logger;
import groovy.lang.*;
import intellijeval.project.script.ctx.EvalContext;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract
class EvalScriptBase extends Script {

    private static final Logger log = Logger.getInstance(EvalScriptBase.class);
    private EvalContext ctx;

    protected
    EvalScriptBase() {
        setMetaClass(getMetaClass());
    }

    protected
    EvalScriptBase(Binding binding) {
        super(binding);
        getCtx();
        setMetaClass(getMetaClass());
    }

    //see groovy.util.slurpersupport.GPathResult#setMetaClass
    @Override
    public
    void setMetaClass(MetaClass metaClass) {
        MetaClass newMetaClass = new DelegatingMetaClass(metaClass) {
            @Override
            public
            Object getAttribute(Object object, String attribute) {
                Object attrib;
                try {
                    attrib = super.getAttribute(object, attribute);
                }
                catch (MissingFieldException e) {
                    if (ctx != null) {
                        attrib = ctx.handleMissingAttribute(attribute);
                    }
                    else {
                        throw e;
                    }
                }
                return attrib;
            }

            @Override
            public
            void setAttribute(final Object object, final String attribute, final Object newValue) {
                try {
                    super.setAttribute(object, attribute, newValue);
                }
                catch (MissingFieldException e) {
                    if (ctx != null) {
                        ctx.handleMissingAttribute(attribute, newValue);
                    }
                    else {
                        throw e;
                    }
                }
            }
        };
        super.setMetaClass(newMetaClass);
    }

    private
    void getCtx() {
        if (ctx == null) {
            Binding b = getBinding();
            if (b.hasVariable("ctx")) ctx = (EvalContext) b.getVariable("ctx");
            else log.warn("script has no context!");
        }
    }

    @Override
    public
    void setBinding(Binding binding) {
        super.setBinding(binding);
        getCtx();
    }

    @Override
    public
    void println() {
        if (ctx == null) super.println();
        else ctx.println("\n");
    }

    @Override
    public
    void print(Object value) {
        if (ctx == null) super.print(value);
        else ctx.print(value);
    }

    @Override
    public
    void println(Object value) {
        if (ctx == null) super.println(value);
        else ctx.println(value);
    }

    @Override
    public
    void printf(String format, Object value) {
        if (ctx == null) super.printf(format, value);
        else ctx.printf(format, value);
    }

    @Override
    public
    void printf(String format, Object[] values) {
        if (ctx == null) super.printf(format, values);
        else ctx.printf(format, values);
    }

    public
    <T> T runOnce(String id, Closure<T> closure) {
        if (ctx != null) return ctx.runOnce(id, closure);
        else {
            log.warn("runOnce: no context");
            return closure.call();
        }
    }

    public
    <T> void dispose(Closure<T> closure) {
        if (ctx != null) {
            ctx.disposeCallback(closure);
        }
    }

    public
    Object propertyMissing(String name) throws MissingPropertyException {

        if (ctx != null) {
            return ctx.handleMissingProperty(name);
        }
        else {
            throw new MissingPropertyException(name, getClass());
        }

    }

    public
    Object propertyMissing(String name, Object value) throws MissingPropertyException {

        if (ctx != null) {
            return ctx.handleMissingProperty(name, value);
        }
        else {
            throw new MissingPropertyException(name, getClass());
        }
    }

    public
    Object methodMissing(String name, Object args) {

        if (ctx != null) {
            return ctx.handleMissingMethod(name, (Object[]) args);
        }
        else {
            throw new MissingMethodException(name, getClass(), (Object[]) args);
        }
    }
}
