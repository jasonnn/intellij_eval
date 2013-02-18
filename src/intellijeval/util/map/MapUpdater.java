package intellijeval.util.map;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapUpdater<K,V> implements ObservableMap.Listener<K,V> {

    private Map<K,V> target;

    public MapUpdater(Map<K, V> target) {
        this.target = target;
    }
    public MapUpdater(ObservableMap<K,V> source,Map<K,V> target){
        this.target=target;
        source.addListener(this);

    }

    @Override
    public void entryAdded(K key, V value) {
        target.put(key,value);
    }

    @Override
    public void entryRemoved(K key) {
        target.remove(key);
    }
}
