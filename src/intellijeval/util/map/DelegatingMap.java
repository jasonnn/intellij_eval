package intellijeval.util.map;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatingMap<K,V> implements Map<K,V> {

    protected Map<K,V> delegate;

    public DelegatingMap(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    public Map<K, V> getDelegate() {
        return delegate;
    }

    public void setDelegate(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return delegate.get(key);
    }

    public V put(K key, V value) {
        return delegate.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return delegate.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
    }

     void standardPutAll(Map<? extends K, ? extends V> m){
        for(Map.Entry<? extends K, ? extends V> entry: entrySet()) {
          put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public void clear() {
        delegate.clear();
    }

     void standardClear(){
      for(K key: keySet()){
          remove(key);
      }
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        return delegate.keySet();
    }

    @NotNull
    @Override
    public Collection<V> values() {
        return delegate.values();
    }

    @NotNull
    @Override
    public Set<Entry<K,V>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && (o == this || delegate.equals(o));
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }
}
