package models;

import play.Logger;

import java.util.HashMap;
import java.util.Map;

public class Salaries {

    public static Map<String, String> list() {
        Logger.debug("pouet");
        return new HashMap<String, String>() {{
            put("0", "30K - 40K");
            put("1", "41K - 50K");
            put("2", "51K+");
        }};
    }

}