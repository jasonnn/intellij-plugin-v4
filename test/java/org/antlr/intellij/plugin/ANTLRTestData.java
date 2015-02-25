package org.antlr.intellij.plugin;

import java.io.File;

/**
 * Created by jason on 2/10/15.
 */
public class ANTLRTestData {
    public static final String PATH;
    public static String getTestDataPath(String subDir){
        File f = new File(PATH,subDir);
        assert f.exists();
        return f.getAbsolutePath()+'/';
    }

    static {
        File f = new File("test/resources");
        assert f.exists();
        PATH = f.getAbsolutePath()+'/';
    }
}
