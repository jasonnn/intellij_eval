package intellijeval.project.script;

import intellijeval.project.script.ctx.EvalContextFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/16/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalScriptFactory {
   private EvalContextFactory contextFactory;

    public EvalScriptFactory(EvalContextFactory contextFactory) {
        this.contextFactory = contextFactory;
    }

    public EvalScriptBase instantiate(Class scriptClass){
        assert EvalScriptBase.class.isAssignableFrom(scriptClass);

        try {
            Constructor ctor = scriptClass.getDeclaredConstructor(EvalContext.class);
            ctor.setAccessible(true);
           return (EvalScriptBase) ctor.newInstance(contextFactory.createContext());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
