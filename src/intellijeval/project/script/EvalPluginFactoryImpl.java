package intellijeval.project.script;

import intellijeval.EvalAppService;
import intellijeval.project.EvalProjectService;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalPluginFactoryImpl implements EvalPluginFactory{

    private EvalAppService appService;
    private EvalProjectService projectService;

    public EvalPluginFactoryImpl(EvalAppService appService, EvalProjectService evalProjectService) {
        this.appService = appService;
        this.projectService = evalProjectService;
    }

    @Override
    public EvalPlugin createPlugin(URI base) {
        EvalContext ctx=null;  //TODO!
        EvalPlugin plugin = new EvalPlugin("CHANGEME",base.getPath(),ctx,projectService,appService);


        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
