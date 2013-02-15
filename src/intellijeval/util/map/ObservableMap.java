package intellijeval.util.map;

import intellijeval.util.RefType;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObservableMap<K,V> extends DelegatingMap<K,V> {

       public static interface Listener<K,V> {

              void entryAdded(K key,V value);

              void entryRemoved(K key,V value);

          }


     private Set<Listener<K,V>> listeners = new HashSet<Listener<K, V>>();

    public ObservableMap(Map<K, V> delegate) {
        super(delegate);

    }

    public ObservableMap(Map<K, V> delegate, Set<Listener<K, V>> listeners) {
        super(delegate);
        this.listeners = listeners;
    }

    public ObservableMap(Map<K,V> delegate,RefType howToKeepListeners){
        super(delegate);
        switch (howToKeepListeners){
            case SOFT:
            case WEAK: listeners= Collections.newSetFromMap(new WeakHashMap<Listener<K, V>, Boolean>());
                break;
            case STRONG:
            default: listeners = new LinkedHashSet<Listener<K, V>>();
                break;

        }
    }

    public void addListener(Listener<K,V> listener){
        listeners.add(listener);
    }

    public void removeListener(Listener<K,V> listener){
        listeners.remove(listener);
    }



    @Override
    public V put(K key, V value) {
        V result= super.put(key, value);

        for(Listener<K,V> listener: listeners){
            listener.entryAdded(key,value);
        }
        return result;
    }

    @Override
    public V remove(Object key) {
        V result= super.remove(key);
        for(Listener<K,V> listener: listeners){
            listener.entryRemoved((K) key, result);
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        standardPutAll(m);
    }

    @Override
    public void clear() {
       standardClear();
    }
}
