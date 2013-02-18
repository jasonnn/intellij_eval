package intellijeval.util.map;

import groovy.lang.Closure;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/17/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public
class EvalBindingsMapTest {

    EvalBindingsMap map;
    Map delegate;

    @Before
    public
    void setUp() throws Exception {
        delegate = new HashMap<String, Object>();
        map = new EvalBindingsMap(delegate);

    }

    @Test
    public
    void testAddClosure() {

        map.put("closure", new Closure<String>(null) {
            @Override
            public
            String call() {
                return "called";
            }
        });

        Object o = map.get("closure");
        assert o instanceof String;
        assert o.equals("called");
        assert o.equals(delegate.get("closure"));

    }

    @Test
    public
    void testMapDoesntUpdate() throws Exception {
        map.put("cached", "orig");
        map.put("cached", "changed");
        assert "orig".equals(map.get("cached"));
        assert map.get("cached").equals(delegate.get("cached"));

    }
}
