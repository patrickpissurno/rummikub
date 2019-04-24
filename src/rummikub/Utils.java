package rummikub;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static int randomRange(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static URL getResource(String name){
        try
        {
            return Utils.class.getResource("/" + name) == null ? Paths.get(name).toUri().toURL() : Utils.class.getResource("/" + name);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static InputStream getResourceAsStream(String name){
        try
        {
            return Utils.class.getResource("/" + name) == null ? new FileInputStream(name) : Utils.class.getResourceAsStream("/" + name);
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
