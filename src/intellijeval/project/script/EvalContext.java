package intellijeval.project.script;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface  EvalContext  extends ScriptOutputHandler {




    public abstract <T> T runOnce(String id, Closure<T> closure);




    public abstract Object handlePropertyMissing(String name) throws MissingPropertyException;

    public abstract Object handlePropertyMissing(String name, Object value) throws MissingPropertyException;

    public abstract Object handleMethodMissing(String name, Object[] args) throws MissingMethodException;
}
