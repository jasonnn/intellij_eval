package intellijeval.util.map.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.ForwardingCache;
import com.google.common.util.concurrent.Callables;
import com.intellij.openapi.util.Ref;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 8:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObservableCache<K,V> extends ForwardingCache<K,V> {

        static interface Listener<K,V>{
            void entryAdded(Cache<K,V> source, K key,V value);
            void entryRemoved(Cache<K,V> source, K key,V value);
        }



    private final Cache<K,V> delegate;
    private final Set<Listener<K,V>> listeners;

    public ObservableCache(Cache<K, V> delegate) {
        this.delegate = delegate;
        this.listeners=Collections.newSetFromMap(new WeakHashMap<Listener<K, V>, Boolean>());
    }

    public ObservableCache(Cache<K,V> delegate,Set<Listener<K,V>>listenerList){
        this.delegate=delegate;
        this.listeners=listenerList;
    }

    public ObservableCache(){
        this.delegate= CacheBuilder.newBuilder().weakValues().build();
        this.listeners = Collections.newSetFromMap(new WeakHashMap<Listener<K, V>, Boolean>());
    }


    @Override
    public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
        WasItCalled<V> question = new WasItCalled<V>(valueLoader);
        V result = super.get(key, question);
        if (question.wasItCalled()) notifyAdd(key, result);
        return result;

    }

    private void notifyAdd(K key,V value){
        for(Listener<K,V> listener : listeners){
            listener.entryAdded(delegate,key,value);
        }

    }


    /**
     * delegates to {@link #get(Object, java.util.concurrent.Callable)}
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        try {
            get(key, Callables.returning(value));
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * delegates to {@link #put(Object, Object)}
     * @param m
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
       for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()){
           put(entry.getKey(),entry.getValue());
       }
    }

    @Override
    public void invalidate(Object key) {
        super.invalidate(key);
        notifyRemoved((K) key);
    }

    private void notifyRemoved(K key){
      for(Listener<K,V> listener: listeners){
          listener.entryRemoved(delegate,key,null);
      }
    }


    /**
     * delegates to {@link #invalidate(Object)}
     * @param keys
     */
    @Override
    public void invalidateAll(Iterable<?> keys) {
       for(Object key:keys){
           invalidate(key);
       }
    }


    /**
     * delegates to {@link #invalidateAll(Iterable)}
     */
    @Override
    public void invalidateAll() {
        Iterable<?> keys = delegate.asMap().keySet();
      invalidateAll(keys);
    }

    @Override
    protected Cache<K, V> delegate() {
        return delegate;
    }


  static class WasItCalled<V> implements Callable<V>{

      private final Callable<V> delegate;
      private final Ref<Boolean> called = Ref.create(Boolean.FALSE);

      WasItCalled(V value){
          this.delegate=Callables.returning(value);
      }

      public WasItCalled(Callable<? extends V> delegate) {
          this.delegate= (Callable<V>) delegate;
      }

      @Override
      public V call() throws Exception {
          called.set(Boolean.TRUE);
        return delegate.call();
      }

      public boolean wasItCalled(){
          return called.get();
      }
  }


}
