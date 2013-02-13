package intellijeval.project.script;

import groovy.lang.Closure;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
//TODO: is this the right name?
public interface EvalContext extends ScriptOutputHandler{
    <T> T runOnce(String id, Closure<T> closure);
}
