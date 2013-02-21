package script

import groovy.io.FileType
import groovy.io.FileVisitResult
import intellijeval.project.script.ScriptUtil
import org.codehaus.groovy.control.messages.WarningMessage
import util.TestUtil

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/15/13
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
class ScriptingTests {

    ScriptingTests() {

        //test1()
        test2()
    }

    def test1() {
        def cc = ScriptUtil.createDefaultCompilerConfiguration()
        File out = new File('./compile')
        out.mkdirs()
        cc.targetDirectory = out
        cc.debug = true
        cc.verbose = true
        cc.warningLevel = WarningMessage.PARANOIA
        cc.output = new PrintWriter(System.out)
        // cc.setScriptBaseClass('groovy.lang.Script')
        // cc.classpathList=getRoots().collect {it.path}  as List


        def gcl = new GroovyClassLoader(ScriptingTests.class.classLoader, cc, false)

        def gse = new GroovyScriptEngine(getRoots(), gcl)
        gse.config = cc

        gse.run('MyScript.groovy', new Binding())
    }

    def test2() {
        def cc = ScriptUtil.createDefaultCompilerConfiguration()

        def gcl = new GroovyClassLoader(ScriptingTests.class.classLoader, cc, false)

        def gse = new GroovyScriptEngine(getRoots(), gcl)
        gse.config = cc

//        def cls = gse.loadScriptByName('MyScript2.groovy')
//        assert cls != null

        def bindings = [ctx: new TestCtx()]
        def scrpt = gse.createScript('MyScript2.groovy', new Binding(bindings))
        scrpt.run()

        println bindings


    }




    static URL[] getRoots() {
        def roots = []
        TestUtil.testDir.traverse(
                type: FileType.DIRECTORIES,
                preDir: { if (it.name.startsWith('.')) return FileVisitResult.SKIP_SUBTREE },
                visitRoot: true,
                preRoot: true,
                //   sort: { a, b-> a.canonicalPath <=> b.canonicalPath},
                visit: { roots << it.toURI().toURL() }

        )
        // roots.each{println it}

        return roots as URL[]
    }



    public static void main(args) {
        new ScriptingTests()
    }
}