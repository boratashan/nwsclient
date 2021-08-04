package nwscore;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class EvsFileToJsonFile {

    public static void main(String[] args) throws IOException {
        getFiles();
    }

    private static void getFiles() throws IOException {
        File dir = new File("c:/input/");

        File[] files = dir.listFiles();
        System.out.println(files);
        for (File f : files)
            convertFileToJson(f);

    }

    private static void convertFileToJson(File file) throws IOException {
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
            stream.forEach(s -> {
                if (!s.equalsIgnoreCase("temp")) {
                    JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
                    array.add(jsonObject);
                }
            });
        }
        gson.toJson(array, new JsonWriter(new BufferedWriter(new FileWriter(file.getPath()+".json"))));

    }
}
