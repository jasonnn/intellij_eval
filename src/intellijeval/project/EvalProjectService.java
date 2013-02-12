package intellijeval.project;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class EvalProjectService {



    public static EvalProjectService getInstance(Project project){
        return ServiceManager.getService(project,EvalProjectService.class);
    }
}
