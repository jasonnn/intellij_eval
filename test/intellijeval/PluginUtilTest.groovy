package intellijeval

import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/19/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
class PluginUtilTest {

    /*
    [ 376180]  ERROR - com.intellij.ide.IdeEventQueue - Error during dispatching of java.awt.event.InvocationEvent[INVOCATION_DEFAULT,runnable=intellijeval.PluginUtil$_show_closure1@4d08e941,notifier=null,catchExceptions=false,when=1361324034972] on apple.awt.CToolkit@253ab896
groovy.lang.MissingMethodException: No signature of method: java.lang.Integer.isArray() is applicable for argument types: () values: []
Possible solutions: every()
	at org.codehaus.groovy.runtime.ScriptBytecodeAdapter.unwrap(ScriptBytecodeAdapter.java:55)
	at org.codehaus.groovy.runtime.callsite.PojoMetaClassSite.call(PojoMetaClassSite.java:46)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:45)
	at java_lang_Class$isArray.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callSafe(AbstractCallSite.java:75)
	at intellijeval.PluginUtil.asString(PluginUtil.groovy:580)
	at intellijeval.PluginUtil$asString.callStatic(Unknown Source)
	....
     */

    @Test
    void 'asString() shouldnt throw a MissingMethodException'() {

        asString(1)
        asString([1] as Integer[])
        asString([] as Integer[])
        asString(null)



       asString([:])
       asString([:].withDefault {0})

    }

    @Test
    void 'does asString handle null correctly?'(){
        assert 'null'.equals(asString(null))
    }

    static String asString(message) {
        message?.getClass()?.isArray() ? Arrays.toString(message) : String.valueOf(message)
    }
}
