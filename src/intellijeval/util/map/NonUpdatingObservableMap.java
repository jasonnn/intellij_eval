package intellijeval.util.map;

import intellijeval.util.RefType;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class NonUpdatingObservableMap<K,V> extends ObservableMap<K,V> {

    public NonUpdatingObservableMap(Map<K,V> delegate) {
        super(delegate);
    }

    public NonUpdatingObservableMap(Map<K, V> map, RefType refType) {
        super(map,refType);
    }

    @Override
    public V put(K key, V value) {
        return super.containsKey(key) ? super.get(key) :super.put(key,value);
    }
}
