package intellijeval.util;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/11/13
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static void initDefault(){
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("MyUncaughtExceptionHandler.uncaughtException");
    }
}
