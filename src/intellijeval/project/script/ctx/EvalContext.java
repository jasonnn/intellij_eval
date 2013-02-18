package intellijeval.project.script.ctx;

import groovy.lang.Closure;
import groovy.lang.MissingFieldException;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EvalContext extends ScriptOutputHandler {


    <T> T runOnce(String id, Closure<T> closure);

    Object handleMissingProperty(String name) throws MissingPropertyException;

    Object handleMissingMethod(String name, Object[] args) throws MissingMethodException;

    Object handleMissingAttribute(String attribute) throws MissingFieldException;

    Object handleMissingProperty(String name, Object value) throws MissingPropertyException;

    void handleMissingAttribute(String attribute, Object newValue);

    <T> void disposeCallback(Closure<T> closure);
}
