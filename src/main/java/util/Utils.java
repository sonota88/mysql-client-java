package util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Utils {

    private static final String LF = "\n";
    private static final String BS = "\\";
    private static final String DQ = "\"";

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

    public static String escape(String s) {
        return s
                .replace(BS, BS + BS)
                .replace(DQ, BS + DQ)
                .replace("\b", BS + "b")
                .replace("\f", BS + "f")
                .replace("\n", BS + "n")
                .replace("\r", BS + "r")
                .replace("\t", BS + "t")
                ;
    }

}
