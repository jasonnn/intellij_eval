package util

import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 2/15/13
 * Time: 1:06 PM
 * To change this template use File | Settings | File Templates.
 */
class UtilTest {

    @Test
    public void 'getProjectDir() should find this projects base dir'(){
      def base = Util.projectDir
        assert base != null
        assert base.exists()

       assert base.name.equals('intellij_eval')
       assert !base.path.contains('out')
    }

    @Test
    public void 'getTestDir() should work'(){
        def testDir = Util.testDir
        assert testDir != null
        assert testDir.exists()

        assert testDir.name.equals('test')
        assert !testDir.path.contains('out')
    }

}
