package intellijeval.util.map;

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
public class EvalBindingsMap extends NonUpdatingObservableMap<String,Object> {

    public EvalBindingsMap(Map<String, Object> delegate) {
        super(delegate);
    }

    public EvalBindingsMap(Map<String, Object> map, RefType refType) {
        super(map, refType);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Object put(String key, Object value) {
        return value instanceof Closure ? super.put(key,((Closure) value).call()) : super.put(key,value);
    }
}
