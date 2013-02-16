package script

import groovy.io.FileType
import groovy.io.FileVisitResult
import intellijeval.project.script.ScriptUtil
import util.Util

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/15/13
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
class ScriptingTests {

    ScriptingTests() {
        def cc = ScriptUtil.createDefaultCompilerConfiguration()

        cc.debug=true
        cc.verbose=true
       // cc.output= new PrintWriter(System.err)
        cc.setScriptBaseClass('groovy.lang.Script')

        def gcl = new GroovyClassLoader(ScriptingTests.class.classLoader,cc,false)

        def gse = new GroovyScriptEngine(getRoots(),gcl)

        gse.run('MyScript.groovy',new Binding())

    }


   static URL[] getRoots(){
       def roots=[]
       Util.testDir.traverse(
               type: FileType.DIRECTORIES ,
               preDir : {if (it.name.startsWith('.')) return FileVisitResult.SKIP_SUBTREE},
               visitRoot: true,
               preRoot: true,
            //   sort: { a, b-> a.canonicalPath <=> b.canonicalPath},
               visit:{ roots<<it.toURI().toURL()}

       )
      // roots.each{println it}

       return roots as URL[]
    }



    public static void main(args) {
      new ScriptingTests()
    }
}
