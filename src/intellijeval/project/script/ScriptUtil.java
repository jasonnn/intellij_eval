package intellijeval.project.script;

import com.intellij.ide.passwordSafe.impl.providers.masterKey.windows.W32API;
import com.intellij.openapi.application.PathManager;
import groovy.transform.ThreadInterrupt;
import intellijeval.PluginUtil;
import intellijeval.Util;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/13/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScriptUtil {
    private static class EvalPath{
        public static final URI uri = URI.create("file://"+PathManager.getPluginsPath()+"/intellij-eval-plugins");
    }

    private static class DirectoryFilter{
        public static final FileFilter INSTANCE=new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };


    }

    private static final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            return !pathname.isDirectory();
        }
    };

    //TODO: cache?
    public static CompilerConfiguration createDefaultCompilerConfiguration(){
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(EvalScriptBase.class.getName());
        cc.setRecompileGroovySource(true); //TODO what does this mean?
        cc.setMinimumRecompilationInterval(0);

        ImportCustomizer imports = new ImportCustomizer();
        imports.addStaticStars(Util.class.getName(), PluginUtil.class.getName());
        ASTTransformationCustomizer interrupt = new ASTTransformationCustomizer(ThreadInterrupt.class);

        cc.addCompilationCustomizers(imports,interrupt);

        return cc;
    }

    public static URI getEvalBasePath(){
        return  EvalPath.uri;
    }

    public static Collection<File>getDefaultPluginDirs(){
        return getPluginDirs(new File(getEvalBasePath()));
    }

    public static Collection<File> getPluginDirs(File base){
        Set<File> ret = new HashSet<File>();
       File[] dirs= base.listFiles(DirectoryFilter.INSTANCE) ;

        if(dirs==null || dirs.length==0) return Collections.emptySet();

        for(File dir: dirs){
         File[] files = dir.listFiles(FILE_FILTER);
         if(files==null || files.length==0) return Collections.emptySet();
            for(File f:files){
                if(f.getName().equals("plugin.groovy")){
                    ret.add(dir);
                    break;
                }
            }
       }


        return ret;
    }
}
