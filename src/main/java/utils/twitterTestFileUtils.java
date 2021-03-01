package utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class twitterTestFileUtils {
    public static final Properties prop = new Properties();
    public static List<String> getUsersFromFile() throws IOException {
        prop.load(new FileInputStream("src/main/resources/testProperties"));
        List<String> userNames = new ArrayList<>();
        try
        {
            userNames = Files.readAllLines(Paths.get(prop.getProperty("com.twitter.userlist.file")), StandardCharsets.UTF_8);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return userNames;
    }
    public static void createDirectories(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
    }
    public static void copyFiles(File file1, File file2) throws IOException {
        FileUtils.copyFile(file1,file2);
    }
}
