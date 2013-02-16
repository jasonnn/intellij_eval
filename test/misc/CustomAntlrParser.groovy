package misc

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/14/13
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */



import org.codehaus.groovy.control.*
import org.codehaus.groovy.antlr.*
import org.codehaus.groovy.syntax.*

/**
 * @see <a href="http://groovyconsole.appspot.com/script/3"> http://groovyconsole.appspot.com/script/3 </a>
 */

/*
Technique useful to avoid overriding all possible means to read/parse sources (File, String, CodeSource, InputStream...)
A CompilerConfiguration definining a custom ParserPlugin / ParserPluginFactory can be passed to all: GroovyShell, GCL, etc.
Single place for modifying the input "text", as long as we pass this CompilerConfiguration.
*/

class SourceModifierParserPlugin extends AntlrParserPlugin {
    Reduction parseCST(SourceUnit sourceUnit, Reader reader) throws CompilationFailedException {
        def text = modifyTextSource(reader.text)
        StringReader stringReader = new StringReader(text)
        super.parseCST(sourceUnit, stringReader)
    }

    String modifyTextSource(text) {
        def lines = text.tokenize('\n')*.trim()
        def modified = lines.collect { line ->
            def words = line.trim().tokenize()
            def modified = words.join('(') + ')' * (words.size() - 1)
        }.join('\n')
        println modified
        modified
    }
}

class SourcePreProcessor extends ParserPluginFactory {
    ParserPlugin createParserPlugin() {
        new SourceModifierParserPlugin()
    }
}

def parserPluginFactory = new SourcePreProcessor()
def conf = new CompilerConfiguration(pluginFactory: parserPluginFactory)

def binding = new Binding([
        tell: { println it },
        me: { it + ' you' },
        them: { it + ' everybody' },
        hello: "Hello",
        goodbye: "Goodbye"
])
def shell = new GroovyShell(binding, conf)
def result = shell.evaluate("""
tell me hello
tell me goodbye
tell them goodbye
""")