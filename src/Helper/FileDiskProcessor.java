package Helper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public abstract class FileDiskProcessor {

     protected void writeToDisk(Path path, List<String> payload)  {
        try {
                Files.write(path, Collections.emptyList(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                Files.write(path, payload);
        } catch (Exception e) {
            System.err.println("A disk write error occurred " + e.getMessage());
        }
    }
    protected List<String> readFromDisk(Path path) {
        try {

           return Files.readAllLines(path);
        } catch (Exception e) {
            System.err.println("A disk read error occurred " + e.getMessage());
            return Collections.emptyList();
        }
    }
    protected void serializeToDisk (String path, Serializable object) {
         try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
             oos.writeObject(object);

         } catch (Exception e) {
             System.err.println("A disk write error occurred" + e.getCause());
         }
    }
    protected TreeMap<?,?> deserializeFromDisk (String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (TreeMap<?,?>) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return new TreeMap<>();
        }
    }
}
