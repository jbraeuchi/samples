package jsonpath;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tests {
    @Test
    public void testAdd(){
        DocumentContext dcPerson = JsonPath.parse("{}");
        dcPerson.put("$", "name","Tester");
        dcPerson.put("$", "datum", LocalDate.now().toString());
        dcPerson.put("$", "adressen", new ArrayList());

        DocumentContext dcAdresse = JsonPath.parse("{}");
        dcAdresse.put("$", "ort","Bern");
        dcAdresse.put("$", "strasse", "Monbijoustrasse 68");

        dcPerson.add("$.adressen", dcAdresse.json());

        System.out.println(dcPerson.jsonString());
    }

    @Test
    public void testRead() {
        String json = "{\n" +
                "  \"name\": \"Tester\",\n" +
                "  \"datum\": \"2018-01-15\",\n" +
                "  \"adressen\": [\n" +
                "    {\n" +
                "      \"ort\": \"Bern\",\n" +
                "      \"strasse\": \"Monbijoustrasse 68\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ort\": \"Bern\",\n" +
                "      \"strasse\": \"Bundesgasse 35\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"ort\": \"Solothurn\",\n" +
                "      \"strasse\": \"Bahnweg 1\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        DocumentContext dc = JsonPath.parse(json);

        List<Map<String, String>> result1 = dc.read("$.adressen.[?(@.strasse contains 'gasse')]", List.class);
        System.out.println(result1.get(0).get("strasse"));

        Filter filter1 = Filter.filter(Criteria.where("strasse").contains("gasse"));
        List<Map<String, String>> result2 = dc.read("$.adressen.[?]", filter1);
        System.out.println(result2.get(0).get("strasse"));

    }
}
