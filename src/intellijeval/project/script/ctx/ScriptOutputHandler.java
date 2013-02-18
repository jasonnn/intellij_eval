package intellijeval.project.script.ctx;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ScriptOutputHandler {
    void println(Object o);

    void print(Object value);

    void printf(String format, Object value);

    void printf(String format,Object[] values);
}
