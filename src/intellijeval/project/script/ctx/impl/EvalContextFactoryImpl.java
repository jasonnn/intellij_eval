package intellijeval.project.script.ctx.impl;

import intellijeval.project.script.ctx.EvalContext;
import intellijeval.project.script.ctx.EvalContextFactory;
import intellijeval.project.script.ctx.ScriptOutputFactory;
import intellijeval.project.script.ctx.out.PrintStreamHandler;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/19/13
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */

//TODO!
public
class EvalContextFactoryImpl implements EvalContextFactory {
    private
    ScriptOutputFactory outputFactory;

    public
    EvalContextFactoryImpl(ScriptOutputFactory outputFactory) {
        this.outputFactory = outputFactory;
    }

    public
    EvalContextFactoryImpl() {
        this.outputFactory = new PrintStreamHandler.Factory();
    }

    @Override
    public
    EvalContext createContext() {
        return new EvalContextImpl(outputFactory.newHandler());
    }
}
