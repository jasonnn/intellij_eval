package intellijeval.project.script;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EvalPluginFactory {

     EvalPlugin createPlugin(URI base);
}
