package script

import groovy.io.FileType
import groovy.io.FileVisitResult
import intellijeval.project.script.ScriptUtil
import intellijeval.project.script.ctx.AbstractEvalContext
import intellijeval.project.script.ctx.out.PrintStreamHandler
import org.codehaus.groovy.control.messages.WarningMessage
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

        def bindings = [ctx: new TestContext()]
        def scrpt = gse.createScript('MyScript2.groovy', new Binding(bindings))
        scrpt.run()

        println bindings


    }

    class TestContext extends AbstractEvalContext {

        protected TestContext() {
            super(new PrintStreamHandler())
        }

        @Override
        def <T> T runOnce(String id, Closure<T> closure) {
            println "ScriptingTests\$TestContext.runOnce"
        }

        @Override
        Object handlePropertyMissing(String name) throws MissingPropertyException {
            println "ScriptingTests\$TestContext.handlePropertyMissing"
           //throw new MissingPropertyException(name)
        }

        @Override
        Object handlePropertyMissing(String name, Object value) throws MissingPropertyException {
            println "ScriptingTests\$TestContext.handlePropertyMissing"
        }

        @Override
        Object handleMethodMissing(String name, Object[] args) throws MissingMethodException {
            println "ScriptingTests\$TestContext.handleMethodMissing"

        }
    }


    static URL[] getRoots() {
        def roots = []
        Util.testDir.traverse(
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
