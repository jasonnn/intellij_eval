package misc

import org.junit.Before
import org.junit.Test

import java.util.concurrent.Callable

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 10:11 PM
 * To change this template use File | Settings | File Templates.
 */
class ClosureIsCallableTest {
     def wasCalled = false



    @Test
    public void 'closure instanceof Callable'(){
      assert {->} instanceof Callable
    }

    @Test
    public void 'test that the closure gets called'(){
        def callable = {wasCalled=true}  as Callable

       def result= callable.call()

        assert result
        assert wasCalled
    }

    @Before
    public void setUp() throws Exception {
        wasCalled=false

    }
}
