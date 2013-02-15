package intellijeval.util.map;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CachingMap<K,V> extends DelegatingMap<K,V> {
    public CachingMap(Map<K, V> delegate) {
        super(delegate);
    }

    @Override
    public V put(K key, V value) {
        return super.containsKey(key) ? super.get(key) :super.put(key,value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        standardPutAll(m);
    }
}
