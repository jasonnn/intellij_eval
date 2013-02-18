package script

import intellijeval.project.script.ctx.AbstractEvalContext
import intellijeval.project.script.ctx.out.PrintStreamHandler

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/16/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
class TestCtx extends AbstractEvalContext {

    protected TestCtx() {
        super(new PrintStreamHandler())
    }

    @Override
    def <T> T runOnce(String id, Closure<T> closure) {
        println "TestCtx.runOnce"
    }

    @Override
    Object handleMissingProperty(String name) throws MissingPropertyException {
        println "TestCtx.handleMissingProperty"
        //throw new MissingPropertyException(name)
    }

    @Override
    Object handleMissingProperty(String name, Object value) throws MissingPropertyException {
        println "TestCtx.handleMissingProperty"
    }

    @Override
    void handleMissingAttribute(String attribute, Object newValue) {
        println "TestCtx.handleMissingAttribute"

    }
    @Override
    def <T> void disposeCallback(Closure<T> closure) {
        println "TestCtx.disposeCallback"
    }

    @Override
    Object handleMissingMethod(String name, Object[] args) throws MissingMethodException {
        println "TestCtx.handleMissingMethod"
    }

    @Override
    Object handleMissingAttribute(String attribute) throws MissingFieldException {
        println "TestCtx.handleMissingAttribute"
    }
}