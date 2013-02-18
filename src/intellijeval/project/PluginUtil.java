package intellijeval.project;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/17/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public
class PluginUtil {

    private static final URI defaultBase = URI.create("file://" + PathManager.getPluginsPath() + "/intellij-eval-plugins");
    private static Comparator<URL> urlComparator = new Comparator<URL>() {

        @Override
        public
        int compare(URL o1, URL o2) {
            return o1.getPath().compareTo(o2.getPath());
        }
    };

    public static
    URI getDefaultBasePath() {
        return defaultBase;
    }

    public static
    Collection<File> getDefaultPluginDirs() {
        return getPluginDirs(new File(getDefaultBasePath()));
    }

    public static
    Collection<File> getPluginDirs(File base) {
        Set<File> ret = new HashSet<File>();
        File[] dirs = base.listFiles(Filters.DIRECTORIES);

        if (dirs == null || dirs.length == 0) return Collections.emptySet();

        for (File dir : dirs) {
            File[] files = dir.listFiles(Filters.FILES);
            if (files == null || files.length == 0) return Collections.emptySet();
            for (File f : files) {
                if (f.getName().equals("plugin.groovy")) {
                    ret.add(dir);
                    break;
                }
            }
        }


        return ret;
    }

    public static
    URL[] getSubDirs(File base) {
        Set<URL> urls = new LinkedHashSet<URL>();
        Queue<File> search = new ArrayDeque<File>();
        try {
            urls.add(base.toURI().toURL());
            search.add(base);

            while (!search.isEmpty()) {
                File dir = search.poll();
                urls.add(dir.toURI().toURL());
                Collections.addAll(search, dir.listFiles(Filters.DIRECTORIES));
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return urls.toArray(new URL[urls.size()]);
    }

    public static
    String dir2Id(File file) {
        try {
            return file.getCanonicalFile().getName();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static
    String virtualFile2Id(VirtualFile file) {
        return file.getName();
    }

    public static
    String uri2Id(URI uri) {
        return dir2Id(new File(uri));
    }

    public static
    String url2Id(URL url) {
        try {
            return dir2Id(new File(url.toURI()));
        }
        catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;

    }

    private static
    enum Filters implements FileFilter {

        FILES(false),
        DIRECTORIES(true);
        private final boolean dir;

        Filters(boolean b) {
            this.dir = b;
        }

        @Override
        public
        boolean accept(File file) {
            return dir && file.isDirectory();
        }

    }


}
