package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class JsonInputParser {
    private static JSONObject data;

    public static JSONObject inputDataInit() {
        JSONParser parser =new JSONParser();
        try {
            data = (JSONObject) parser.parse(new  FileReader("src/test/resources/test_Data.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
