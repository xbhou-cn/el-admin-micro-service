package xb.hou;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static xb.hou.utils.FileUtil.*;
import static xb.hou.utils.FileUtil.getExtensionName;
import static xb.hou.utils.FileUtil.getFileNameNoEx;
import static xb.hou.utils.FileUtil.getSize;
import static xb.hou.utils.FileUtil.toFile;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FileUtilTest {

    @Test
    public void testToFile() {
        long retval = toFile(new MockMultipartFile("foo", (byte[]) null)).getTotalSpace();
        assertEquals(500695072768L, retval);
    }

    @Test
    public void testGetExtensionName() {
        assertEquals("foo", getExtensionName("foo"));
        assertEquals("exe", getExtensionName("bar.exe"));
    }

    @Test
    public void testGetFileNameNoEx() {
        assertEquals("foo", getFileNameNoEx("foo"));
        assertEquals("bar", getFileNameNoEx("bar.txt"));
    }

    @Test
    public void testGetSize() {
        assertEquals("1000B   ", getSize(1000));
        assertEquals("1.00KB   ", getSize(1024));
        assertEquals("1.00MB   ", getSize(1048576));
        assertEquals("1.00GB   ", getSize(1073741824));
    }
}
