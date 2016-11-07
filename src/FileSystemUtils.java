import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by user on 07/11/16.
 */
public class FileSystemUtils {
    public static byte[] getBytesFromPath(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
    public static String getFileNameFromPath(String filePath) {
        Path path = Paths.get(filePath);
        String name = path.getFileName().toString();
        System.out.println("file&ext: " + name);
        return name;
    }
}
