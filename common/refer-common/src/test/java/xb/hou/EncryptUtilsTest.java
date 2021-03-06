package xb.hou;

import org.junit.Test;

import static xb.hou.utils.EncryptUtils.*;
import static xb.hou.utils.EncryptUtils.desDecrypt;
import static xb.hou.utils.EncryptUtils.desEncrypt;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class EncryptUtilsTest {

    /**
     * 对称加密
     */
    @Test
    public void testDesEncrypt() {
        try {
            assertEquals("7772841DC6099402", desEncrypt("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对称解密
     */
    @Test
    public void testDesDecrypt() {
        try {
            assertEquals("123456", desDecrypt("7772841DC6099402"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
