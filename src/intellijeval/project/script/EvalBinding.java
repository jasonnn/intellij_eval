package intellijeval.project.script;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;
import intellijeval.project.EvalProjectService;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/21/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
public
class EvalBinding extends Binding {


    public
    EvalBinding(Map variables) {
        super(variables);
    }


    /**
     * copied from {@link groovy.lang.Script#invokeMethod(String, Object)}
     * @param name
     * @param args
     * @return
     */
    @Override
    public
    Object invokeMethod(String name, Object args) {
        try{
            return super.invokeMethod(name,
                                      args);
        } catch (MissingMethodException mme) {
            try {
                if (name.equals(mme.getMethod())) {
                    Object boundClosure = getVariable(name);
                    if (boundClosure != null && boundClosure instanceof Closure) {
                        return ((Closure) boundClosure).call((Object[])args);
                    } else {
                        throw mme;
                    }
                } else {
                    throw mme;
                }
            } catch (MissingPropertyException mpe) {
                throw mme;
            }
        }
    }
}
