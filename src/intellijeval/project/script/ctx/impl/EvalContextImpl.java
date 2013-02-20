package intellijeval.project.script.ctx.impl;

import groovy.lang.Closure;
import groovy.lang.MissingFieldException;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;
import intellijeval.project.script.ctx.AbstractEvalContext;
import intellijeval.project.script.ctx.ScriptOutputHandler;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/19/13
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
//TODO!
public
class EvalContextImpl extends AbstractEvalContext {
    private static final Logger log = Logger.getLogger(EvalContextImpl.class.getName());
    protected
    EvalContextImpl(ScriptOutputHandler outputHandler) {
        super(outputHandler);
    }

    @Override
    public
    <T> T runOnce(String id, Closure<T> closure) {
        log.entering(EvalContextImpl.class.getName(),"runOnce",new Object[]{id,closure});
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public
    Object handleMissingProperty(String name) throws MissingPropertyException {
       throw new MissingPropertyException(name,EvalContextImpl.class);
    }

    @Override
    public
    Object handleMissingMethod(String name, Object[] args) throws MissingMethodException {
       throw new MissingMethodException(name, EvalContextImpl.class,args);
    }

    @Override
    public
    Object handleMissingAttribute(String attribute) throws MissingFieldException {
        throw new MissingFieldException(attribute,EvalContextImpl.class);
    }

    @Override
    public
    Object handleMissingProperty(String name, Object value) throws MissingPropertyException {
        throw new MissingPropertyException(name,EvalContextImpl.class);
    }

    @Override
    public
    void handleMissingAttribute(String attribute, Object newValue) {
            throw new MissingFieldException(attribute,EvalContextImpl.class);
    }

    @Override
    public
    <T> void disposeCallback(Closure<T> closure) {
            log.entering(EvalContextImpl.class.getName(),"disposeCallback");
    }
}
