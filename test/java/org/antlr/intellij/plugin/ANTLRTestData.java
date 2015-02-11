package org.antlr.intellij.plugin;

import java.io.File;

/**
 * Created by jason on 2/10/15.
 */
public class ANTLRTestData {
    public static final String PATH;
    static {
        File f = new File("test/testData/grammars");
        assert f.exists();
        PATH = f.getAbsolutePath()+'/';
    }
}
