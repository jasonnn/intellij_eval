package intellijeval.util.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/17/13
 * Time: 6:14 PM
 * To change this template use File | Settings | File Templates.
 */
public
class ObservableMapTest {

    ObservableMap<String, Integer> om;
    Map<String, Integer> delegate;

    @Before
    public
    void setUp() throws Exception {

        delegate = new HashMap<String, Integer>();
        om = new ObservableMap<String, Integer>(delegate);

    }

    @Test
    public
    void testListening() throws Exception {
        CountingListener<String, Integer> myListener = new CountingListener<String, Integer>();
        om.addListener(myListener);

        om.put("one", 1);
        om.put("two", 2);
        om.remove("two");


        assert myListener.getAdds() == 2;
        assert myListener.getRemoves() == 1;

        om.remove("asdfasdfdafgd");

        assert myListener.getRemoves() == 2;


        assert delegate.size()==1;
        assert delegate.get("one").equals(om.get("one"));
        assert delegate.get("one").equals(1);


    }

    @Test
    public
    void testVeto() throws Exception {
        Veto veto = new Veto();
        om.addListener(veto);

        om.put("one", 1);
        om.put("two", 2);
        om.remove("two");

        om.put("dontAdd", 123);
        assert veto.getAdds() == 2;
        assert !delegate.containsKey("dontAdd");
        veto.reset();

        om.put("dontRemove", 1234);
        om.remove("dontRemove");
        assert veto.getRemoves() == 1;
        assert delegate.containsKey("dontRemove");


    }

    static
    class Veto extends CountingListener<String, Integer> implements ObservableMap.VetoableListener<String, Integer> {
        boolean allowAddCalled = false;
        boolean allowRemoveCalled = false;

        void reset() {
            allowAddCalled = false;
            allowRemoveCalled = false;
        }

        @Override
        public
        boolean allowAdd(String key, Integer value) {
            allowAddCalled = true;

            return !key.startsWith("dontAdd");
        }

        @Override
        public
        boolean allowRemove(String key) {
            allowRemoveCalled = true;

            return !key.startsWith("dontRemove");
        }

        @Override
        public
        void entryAdded(String key, Integer value) {
            assert allowAddCalled : "allowAdd should be called before entryAdded";
            allowAddCalled = false;
            super.entryAdded(key,
                             value);
        }

        @Override
        public
        void entryRemoved(String key) {
            assert allowRemoveCalled : "allowRemove Should be called before entryRemoved";
            allowRemoveCalled = false;
            super.entryRemoved(key);
        }
    }

    static
    class CountingListener<K, V> implements ObservableMap.Listener<K, V> {
        final AtomicInteger adds = new AtomicInteger(0);
        final AtomicInteger removes = new AtomicInteger(0);

        int getAdds() {
            return adds.get();
        }

        int getRemoves() {
            return removes.get();
        }

        @Override
        public
        void entryAdded(K key, V value) {
            adds.incrementAndGet();
        }

        @Override
        public
        void entryRemoved(K key) {
            removes.incrementAndGet();
        }
    }
}
