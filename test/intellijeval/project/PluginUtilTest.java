package intellijeval.project;

import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/17/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public
class PluginUtilTest {
    @Before
    public
    void setUp() throws Exception {

    }

    @Test
    public
    void testGetRoots() throws Exception {
        File testRoot = TestUtil.getTestDir();

        URL[] urls = PluginUtil.getSubDirs(testRoot);
        assert urls.length > 1;
        assert urls.length < 100;

        List<URL> urlsList = Arrays.asList(urls);
        assert urlsList.contains(testRoot.toURI().toURL());

    }
}
