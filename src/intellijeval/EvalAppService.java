package intellijeval;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.extensions.PluginId;
import groovy.lang.GroovyClassLoader;

import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/12/13
 * Time: 7:42 PM
 * To change this template use File | Settings | File Templates.
 */
public
class EvalAppService {

    public static final String pluginId = "IntelliJEval";
    private final
    ListeningExecutorService pluginRunner;
    private GroovyClassLoader gcl;

    public
    EvalAppService() {
        gcl = new GroovyClassLoader(getPluginClassLoader());
        pluginRunner = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
    }

    public static
    EvalAppService getInstance() {
        return ServiceManager.getService(EvalAppService.class);
    }

    public static
    PluginId getPluginId() {
        return PluginId.getId(pluginId);
    }

    public static
    IdeaPluginDescriptor getPluginDescriptor() {
        return PluginManager.getPlugin(getPluginId());
    }

    public static
    ClassLoader getPluginClassLoader() {
        return getPluginDescriptor().getPluginClassLoader();
    }

    public
    ListeningExecutorService getPluginRunner() {
        return pluginRunner;
    }

    public
    GroovyClassLoader getAppClassLoader() {
        return gcl;
    }
}
