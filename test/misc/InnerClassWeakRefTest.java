package misc;

import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class InnerClassWeakRefTest {
    
    interface Inner{
        
    }
    
    private WeakReference<Inner> ref;

    @Before
    public void setUp() throws Exception {            

    }

    @Test
    public void testWeakRef() throws InterruptedException {
        ref = new WeakReference<Inner>(new Inner(){});
        assert ref.get() != null;

        System.gc();

       assert ref.get()==null;


    }
}
