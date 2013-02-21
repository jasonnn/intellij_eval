package intellijeval.project.script;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/21/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public
enum DefaultBindingKeys {
//    public static final String kApp = "app";
//    public static final String kProject = "project";
//    public static final String kContext = "ctx";

    APP("app"),
    PROJECT("project"),
    CONTEXT("ctx");
    final String key;

    DefaultBindingKeys(String key) {
        this.key = key;
    }

    public
    boolean is(String test) {
        return this.key.equals(test);
    }
}
