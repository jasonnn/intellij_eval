package intellijeval.util.map;

import com.google.common.collect.ForwardingMap;
import intellijeval.util.RefType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public
class ObservableMap<K, V> extends ForwardingMap<K, V> {
    private Set<Listener<K, V>> listeners;
    private VetoableListener<K,V> veto;
   // private Set<VetoableListener<K, V>> vetoableListeners;
    private Map<K, V> delegate;

    public
    ObservableMap(Map<K, V> delegate) {
        this(delegate, RefType.STRONG);

    }


    public
    ObservableMap(Map<K, V> delegate, RefType howToKeepListeners) {
        this.delegate = delegate;
        switch (howToKeepListeners) {
            case SOFT:
            case WEAK:
                listeners = Collections.newSetFromMap(new WeakHashMap<Listener<K, V>, Boolean>(2));
                //vetoableListeners = Collections.newSetFromMap(new WeakHashMap<VetoableListener<K, V>, Boolean>(2));

                break;
            case STRONG:
            default:
                listeners = new LinkedHashSet<Listener<K, V>>();
                //vetoableListeners = new LinkedHashSet<VetoableListener<K, V>>(2);
                break;

        }
    }

    public
    void addListener(Listener<K, V> listener) {
        listeners.add(listener);
        if (listener instanceof VetoableListener) {
            veto= (VetoableListener<K, V>) listener;
            //vetoableListeners.add((VetoableListener<K, V>) listener);
        }
    }

    public
    void removeListener(Listener<K, V> listener) {
        listeners.remove(listener);
        if (listener instanceof VetoableListener && veto==listener) {
            veto=null;
            //vetoableListeners.remove(listener);
        }
    }

    private
    boolean shouldPut(K key, V value) {
        if (veto != null) {
            return veto.allowAdd(key,value);
        }
//        for (VetoableListener<K, V> listener : vetoableListeners) {
//            if (!listener.allowAdd(key, value)) return false;
//        }
//
        return true;
    }

    private
    void notifyPut(K key, V value) {
        for (Listener<K, V> listener : listeners) {
            listener.entryAdded(key, value);
        }
    }

    @Override
    public
    V put(K key, V value) {
        V result;
        if (shouldPut(key, value)) {
            result = super.put(key, value);
            notifyPut(key, value);
        }
        else {
            result = super.get(key);
        }
        return result;
    }

    private
    boolean shouldRemove(Object key) {
        if (veto != null) {
            return veto.allowRemove((K) key);
        }
//        for (VetoableListener<K, V> listener : vetoableListeners) {
//            if (!listener.allowRemove((K) key)) return false;
//        }
        return true;
    }

    private
    void notifyRemove(Object key) {
        for (Listener<K, V> listener : listeners) {
            listener.entryRemoved((K) key);
        }
    }

    @Override
    protected
    Map<K, V> delegate() {
        return delegate;
    }

    public
    Map<K, V> getDelegate() {
        return delegate;
    }

    public
    void setDelegate(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public
    V remove(Object key) {
        V result;

        if (shouldRemove(key)) {
            result = super.remove(key);
            notifyRemove(key);
        }
        else {
            result = super.get(key);
        }


        return result;
    }

    @Override
    public
    void putAll(Map<? extends K, ? extends V> m) {
        standardPutAll(m);
    }

    @Override
    public
    void clear() {
        //standardClear();
        clearWithoutVeto();
    }

    private
    void clearWithoutVeto() {
        Iterator<K> iterator = delegate.keySet().iterator();
        while (iterator.hasNext()) {
            K key = iterator.next();
            iterator.remove();
            // super.remove(key);
            notifyRemove(key);
        }
    }

    @NotNull
    @Override
    public
    Set<K> keySet() {
        return new StandardKeySet();
    }

    @NotNull
    @Override
    public
    Collection<V> values() {
        return new StandardValues();
    }

    @NotNull
    @Override
    public
    Set<Entry<K, V>> entrySet() {
        return new StandardEntrySet() {
            @NotNull
            @Override
            public
            Iterator<Entry<K, V>> iterator() {
                return delegate.entrySet().iterator();
            }
        };
    }

    public static
    interface Listener<K, V> {

        void entryAdded(K key, V value);

        void entryRemoved(K key);

    }

    public static
    interface VetoableListener<K, V> extends Listener<K, V> {
        boolean allowAdd(K key, V value);

        boolean allowRemove(K key);

    }

    public static
    class ListenerAdapter<K, V> implements VetoableListener<K, V> {

        @Override
        public
        boolean allowAdd(K key, V value) {
            return true;
        }

        @Override
        public
        boolean allowRemove(K key) {
            return true;
        }

        @Override
        public
        void entryAdded(K key, V value) {
        }

        @Override
        public
        void entryRemoved(K key) {
        }
    }
}
