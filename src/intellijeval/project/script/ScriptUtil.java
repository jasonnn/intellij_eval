package intellijeval.project.script;

import groovy.transform.ThreadInterrupt;
import intellijeval.PluginUtil;
import intellijeval.Util;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/13/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public
class ScriptUtil {


    public static
    CompilerConfiguration createDefaultCompilerConfiguration() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(EvalScriptBase.class.getName());
        cc.setRecompileGroovySource(true); //TODO what does this mean?
        cc.setMinimumRecompilationInterval(0);

        ImportCustomizer imports = new ImportCustomizer();
        imports.addStaticStars(Util.class.getName(), PluginUtil.class.getName());
        ASTTransformationCustomizer interrupt = new ASTTransformationCustomizer(ThreadInterrupt.class);

        cc.addCompilationCustomizers(imports, interrupt);

        return cc;
    }




}
