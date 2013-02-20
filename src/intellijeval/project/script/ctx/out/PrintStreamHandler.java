package intellijeval.project.script.ctx.out;

import intellijeval.project.script.ctx.ScriptOutputFactory;
import intellijeval.project.script.ctx.ScriptOutputHandler;

import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/13/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public
class PrintStreamHandler implements ScriptOutputHandler {

    private final PrintStream out;

    public
    PrintStreamHandler(PrintStream out) {
        this.out = out;
    }

    public
    PrintStreamHandler() {
        this(System.out);
    }

    @Override
    public
    void println(Object o) {
        out.println(o);
    }

    @Override
    public
    void print(Object value) {
        out.print(value);
    }

    @Override
    public
    void printf(String format, Object value) {
        out.printf(format, value);
    }

    @Override
    public
    void printf(String format, Object[] values) {
        out.printf(format, values);
    }

    public static
    class Factory implements ScriptOutputFactory {
        private PrintStream printStream;

        public
        Factory(PrintStream printStream) {
            this.printStream = printStream;
        }

        public
        Factory() {
        }

        @Override
        public
        ScriptOutputHandler newHandler() {
            if (printStream == null) return new PrintStreamHandler();
            else return new PrintStreamHandler(printStream);
        }
    }
}
