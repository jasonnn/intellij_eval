package intellijeval.project.script;

import com.intellij.openapi.project.Project;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.transform.ThreadInterrupt;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceConnector;
import intellijeval.EvalAppService;
import intellijeval.PluginUtil;
import intellijeval.Util;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.IOException;
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
    private final EvalContext context;

    private Project project;

    private GroovyClassLoader loader;
    private GroovyScriptEngine engine;

    public EvalPlugin(String id, URI pluginBase, EvalContext context) {
        this.id = id;
        this.pluginBase = pluginBase;
        this.context = context;
    }
    public EvalPlugin(String id, String basePath, EvalContext context){
        this(id,URI.create(basePath), context);
    }

    private void init(){
        loader = new GroovyClassLoader(EvalAppService.getInstance().getAppClassLoader());
        try {
            engine = new GroovyScriptEngine(pluginBase.getPath(),loader);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }



    public Binding createBinding(){


        return null;
    }

    public String getId() {
        return id;
    }

    public URI getPluginBase() {
        return pluginBase;
    }

    public EvalContext getContext(){
        return context;
    }
}
