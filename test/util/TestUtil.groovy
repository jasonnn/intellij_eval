package util

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/15/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
class TestUtil {


    static final def filter = new FileFilter() {
        @Override
        boolean accept(File pathname) {
            return (pathname.isDirectory() && pathname.name.endsWith('.idea'))
            //TODO iml,ipr?
        }
    }

    static File getProjectDir() {
        def url = TestUtil.class.protectionDomain.codeSource.getLocation()

        return getProjectDir(new File(url.toURI()))
    }

    static File getTestDir() {
        return new File(getProjectDir(), "/test")
    }


    static boolean isIdeaProjectBase(File f) {
        if (!f.directory) return false
        def files = f.listFiles(filter)
        if (files == null) return false
        if (files.size() != 1) return false
        return true


    }

    static File getProjectDir(File dir) {
        File myDir = dir.canonicalFile
        while (myDir != null) {
            if (isIdeaProjectBase(myDir)) return myDir
            myDir = myDir.parentFile
        }
        return null

    }
}
