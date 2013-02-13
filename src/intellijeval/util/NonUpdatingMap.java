package intellijeval.util;

import com.google.common.collect.ForwardingMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class NonUpdatingMap<K,V> extends ForwardingMap<K,V> {

    private Map<K,V> delegate;

    public NonUpdatingMap(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    public NonUpdatingMap() {
        this(new HashMap<K, V>());
    }

    @Override
    protected Map delegate() {
        return delegate;
    }

    @Override
    public V put(K key, V value) {
        return  (delegate.containsKey(key) ? delegate.get(key) : delegate.put(key,value));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        super.putAll(map);   //TODO
    }
}
