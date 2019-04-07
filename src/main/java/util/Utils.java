package util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Utils {

    private static final String LF = "\n";

    public static void puts(Object... os) {
        for (Object o : os) {
            System.out.print(o + LF);
        }
    }

    public static String readFile(String path) {
        try {
            return FileUtils.readFileToString(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties readProps(String path) {
        Properties props = new Properties();

        String propsSrc = readFile(path);

        try (StringReader sr = new StringReader(propsSrc)) {
            props.load(sr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return props;
    }

}
