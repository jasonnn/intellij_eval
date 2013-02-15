package intellijeval.util.map;

import com.google.common.cache.Cache;
import com.google.common.collect.ForwardingMap;
import com.google.common.util.concurrent.Callables;
import groovy.lang.Closure;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 7:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheMapAdapterThingForBinding extends ForwardingMap<String, Object> {

    private final Cache<String, Object> parent;
    private final Map<String, Object> local;

    public CacheMapAdapterThingForBinding(Cache<String, Object> parent, Map<String, Object> localContributions) {
        this.parent = parent;
        this.local = localContributions;
        if(!local.isEmpty()){
            putAll(localContributions);
        }
    }

    public CacheMapAdapterThingForBinding(Cache<String, Object> parent) {
        this.parent = parent;
        this.local=new LinkedHashMap<String, Object>();
    }

    @Override
    public boolean containsKey(Object key) {
        boolean result = (parent.getIfPresent(key)) != null;
        if (!result) assert !local.containsKey(key);

        return result;
    }

    @Override
    public Object put(String key, final Object value) {

        Object result = null;
        try {
            result = parent.get(key, new MyCallable(value));
        } catch (ExecutionException ignore) {
        }

        if (!value.equals(result)) {
            local.put(key, value);
        }

        return result;
    }


   //TODO handle removal of local objects when parent objects have been removed directly
    @Override
    public Object remove(Object object) {
        parent.invalidate(object);
        return local.remove(object);
    }

    @Override
    public void clear() {
        parent.invalidateAll();
        local.clear();
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        standardPutAll(map);
    }

    @Override
    public Object get(Object key) {
        return parent.getIfPresent(key);
    }

    @Override
    protected Map<String, Object> delegate() {
        return parent.asMap(); //TODO cache?
    }

    static class MyCallable implements Callable<Object> {

        private final Object toReturn;

        MyCallable(Object toReturn) {
            this.toReturn = toReturn;
            assert toReturn != null;
        }

        @Override
        public Object call() throws Exception {
           if(toReturn instanceof Closure){
               return ((Closure) toReturn).call();
           } else{
               return toReturn;
           }
        }
    }
}
