import counter.ICounter;
import counter.IPCounter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static String filePath;
    public static void main(String[] args) {
        readConfig("config");
        ICounter counter = new IPCounter(filePath);
        counter.scan();
    }

    private static void readConfig(String configPath) {
        try(FileReader fileReader = new FileReader(configPath)) {
            Properties properties = new Properties();
            properties.load(fileReader);

            filePath = properties.getProperty("ipFilePath");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
