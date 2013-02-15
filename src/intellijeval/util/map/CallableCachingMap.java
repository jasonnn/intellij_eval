package intellijeval.util.map;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallableCachingMap<K,V> extends CachingMap<K,V> {

    public CallableCachingMap(Map<K, V> delegate) {
        super(delegate);
    }

    @Override
    public V put(K key, V value) {
        return super.containsKey(key)
                ? super.get(key) : value instanceof Callable
                ? super.put(key, handleCallable(value)) : super.put(key, value);
    }
    private V handleCallable(Object o){
        V result = null;
        try {
            result= (V) ((Callable)o).call();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
    }

//    @Override
//    public void putAll(Map<? extends K, ? extends V> m) {
//        standardPutAll(m);
//    }
}
