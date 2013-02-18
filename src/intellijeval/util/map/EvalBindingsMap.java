package intellijeval.util.map;

import groovy.lang.Binding;
import groovy.lang.Closure;
import intellijeval.util.RefType;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public
class EvalBindingsMap extends CachingObservableMap<String, Object> {

    private final Binding binding = new Binding(this);

    public
    EvalBindingsMap(Map<String, Object> delegate) {
        super(delegate);
    }

    public
    EvalBindingsMap(Map<String, Object> map, RefType refType) {
        super(map, refType);
    }

    public
    Binding getBinding() {
        return binding;
    }

    @Override
    public
    Object put(String key, Object value) {
        return value instanceof Closure ? super.put(key, handleClosure(value)) : super.put(key, value);
    }

    private
    Object handleClosure(Object closure) {
        Object result = null;
        try {
            result = ((Closure) closure).call();
            if (result instanceof Closure) {
                Closure c = (Closure) result;
                c = c.dehydrate();
                c.setResolveStrategy(Closure.DELEGATE_ONLY);
                c.setDelegate(binding);
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
