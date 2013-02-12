package intellijeval.project.script;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class EvalPlugin {
    private final String id;
    private final URI pluginBase;

    public EvalPlugin(String id, URI pluginBase) {
        this.id = id;
        this.pluginBase = pluginBase;
    }
    public EvalPlugin(String id,String basePath){
        this(id,URI.create(basePath));
    }

    public String getId() {
        return id;
    }

    public URI getPluginBase() {
        return pluginBase;
    }
}
