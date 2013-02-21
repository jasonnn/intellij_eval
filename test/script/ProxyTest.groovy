package script

import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/20/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
class ProxyTest {
    @Test
    void 'test basic proxy usage'() {
        A a = new A()
        def p = new MyProxy().wrap(a)
        println "adaptee: $p.adaptee groovyObject? ${p.adaptee instanceof GroovyObject}"


        assert p.str.equals('a')

        p.adaptee = new B()

        assert p.doSomething().equals('b')

    }

   static class MyProxy extends groovy.util.Proxy {

//        Object getProperty(String name) {
//            println "ProxyTest\$MyProxy.getProperty"
//            return getAdaptee().getMetaClass().getProperty(getAdaptee(), name)
//        }
//         @Override
//         Object invokeMethod(String name, Object args) {
//             println "$name.($args)"
//             return super.invokeMethod(name, args)
//         }

//         def methodMissing(String name, def args) {
//            println "method missing: $name.($args)"
//             return null
//         }
//
//         def propertyMissing(String name) {
//             println "property missing: $name"
//             return name
//         }

//         def propertyMissing(String name,  arg) {
//            println "property missing: $name = $arg"
//             return null
//         }
    }

    static class A {
        def str = 'a'

        void doSomething() { str }
    }

    static class B {
        def str = 'b'

        def doSomething() { str }
    }
}
