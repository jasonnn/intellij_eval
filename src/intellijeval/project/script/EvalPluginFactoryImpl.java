package intellijeval.project.script;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import intellijeval.EvalAppService;
import intellijeval.project.EvalPlugin;
import intellijeval.project.EvalProjectService;
import intellijeval.project.script.ctx.EvalContextFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalPluginFactoryImpl implements EvalPluginFactory {

    private EvalAppService appService;
    private EvalProjectService projectService;
    private Collection<File> dirsToSearch;
    private Disposable parent;
    private EvalContextFactory contextFactory;

    public EvalPluginFactoryImpl(Disposable parent, EvalAppService appService, EvalProjectService evalProjectService, Collection<File> dirsToSearch, EvalContextFactory contextFactory) {
        this.appService = appService;
        this.projectService = evalProjectService;
        this.dirsToSearch = dirsToSearch;// new HashSet<File>(dirsToSearch);
        this.parent = parent;
        this.contextFactory = contextFactory;
    }

    public EvalPluginFactoryImpl(EvalProjectService projectService) {
        this(projectService, EvalAppService.getInstance(), projectService, Collections.singleton(new File(ScriptUtil.getEvalBasePath())), null);
    }

    @Override
    public EvalPlugin createPlugin(String name) throws Exception {
        File dir = findPluginFolder(name);

        EvalPlugin plugin = new EvalPlugin(name, dir.toURI(), contextFactory.createContext(), projectService.getProject(), projectService, appService);
        Disposer.register(parent, plugin);
        return plugin;
    }

    private File findPluginFolder(String name) throws FileNotFoundException {
        for (File root : dirsToSearch) {
            for (File pluginDir : ScriptUtil.getPluginDirs(root)) {
                if (pluginDir.getName().equals(name)) return pluginDir;
            }
        }

        throw new FileNotFoundException("Couldnt find pluginDirectory!");
    }
}
