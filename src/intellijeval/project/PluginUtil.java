package intellijeval.project;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/17/13
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public
class PluginUtil {
    private static final Logger log = Logger.getLogger(PluginUtil.class.getName());
    private static final URI defaultBase = URI.create("file://" + PathManager.getPluginsPath() + "/intellij-eval-plugins");

    public static
    URI getDefaultBasePath() {
        return defaultBase;
    }

    public static
    VirtualFile getDefaultRoot() {
        VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(getDefaultBasePath().toString());
        assert file != null;
        return file;
    }

    public static
    Collection<File> getDefaultPluginDirs() {
        return findPluginDirs(new File(getDefaultBasePath()));
    }

    //TODO: recursive search?
    public static
    Collection<File> findPluginDirs(File base) {
        Set<File> ret = new HashSet<File>();
        File[] dirs = base.listFiles(Filters.DIRECTORIES);

        if (dirs == null) {
            log.log(Level.WARNING, "problem finding sub directories of {0}.", base);
            return Collections.emptySet();
        }
        if (dirs.length == 0) {
            log.log(Level.WARNING, "folder: {0} contains no subdirs.", base);
            return Collections.emptySet();
        }
        int searchCount = 0;  //TODO: remove after done debugging
        for (File dir : dirs) {
            if (isPluginFolder(dir)) ret.add(dir);
            searchCount++;
        }

        if (ret.isEmpty()) log.log(Level.WARNING,
                                   "No plugin directories found after searching {0} subfolders of {1}",
                                   new Object[]{searchCount, base});


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
    Collection<String> virtualFilesToIDs(Collection<VirtualFile> files) {
        Collection<String> ret = new ArrayList<String>(files.size());
        for (VirtualFile file : files) {
            ret.add(file.getName());
        }
        return ret;
    }

    public static
    Collection<URI> virtualFilesToURI(Collection<VirtualFile> virtualFiles) {
        Collection<URI> ret = new ArrayList<URI>(virtualFiles.size());
        for (VirtualFile file : virtualFiles) {
            ret.add(URI.create(file.getPath()));
        }

        return ret;
    }

    public static
    Collection<VirtualFile> findPluginRootsFor(VirtualFile[] files) {
        Set<VirtualFile> ret = new HashSet<VirtualFile>();
        for (VirtualFile file : files) {
            VirtualFile root = pluginFolderOf(file);
            if (root != null) ret.add(root);

        }

        return ret;
    }

    public static
    VirtualFile pluginFolderOf(VirtualFile file) {
        Deque<VirtualFile> toSearch = new ArrayDeque<VirtualFile>();
        toSearch.add(file);

        while (file != null) {
            if (isPluginFolder(file)) return file;
            else file = file.getParent();
        }
        log.warning("didnt find plugin folder of VirtualFile : "+file.getName()+ "\n returning null.");
        return null;
    }

    public static
    boolean isPluginFolder(VirtualFile file) {
        return file.isDirectory() && file.findChild("plugin.groovy") != null;
    }

    public static
    boolean isPluginFolder(File dir) {
        return dir.isDirectory() && dir.listFiles(Filters.EVAL_PLUGIN_FILE).length == 1;
    }

    public static
    String url2Id(URL url) {
        try {
            return dir2Id(new File(url.toURI()));
        }
        catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement u   File | Settings | File Templates.
        }
        return null;

    }

    static abstract
    class Filters {
        public static final FileFilter FILES = new FileFilter() {
            @Override
            public
            boolean accept(File pathname) {
                return pathname.isFile();
            }
        };
        public static final FileFilter DIRECTORIES = new FileFilter() {
            @Override
            public
            boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
        public static final FileFilter EVAL_PLUGIN_FILE = new FileFilter() {

            @Override
            public
            boolean accept(File f) {
                return f.isFile() && f.getName().equals("plugin.groovy");
            }
        };
        public static final FileFilter EVAL_PLUGIN_DIRECTORY = new FileFilter() {
            @Override
            public
            boolean accept(File pathname) {
                return pathname.isDirectory() && pathname.listFiles(EVAL_PLUGIN_FILE).length == 1;
            }
        };
    }


}
