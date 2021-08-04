package nwscore;

import com.google.gson.*;

public class AppTest {

    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();

        JsonArray arr = new JsonArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : locations) {

            /*stringBuilder.append(String.format("%s:%s, \n", "\""+s+"\"","\""+"ca_62e8cdb75ae048f2b5d4d63ecff92051"+"\""  ));*/

            /*"V-FFL-4833-WASHINGTONST": "ca_62e8cdb75ae048f2b5d4d63ecff92051",
                    "V-FFL-4846-EASTOAKST": "ca_62e8cdb75ae048f2b5d4d63ecff92051"
              */

            /*JsonObject innerObject = new JsonObject();
            innerObject.addProperty("from_location_id", s);
            JsonArray a = new JsonArray();
            a.add("*");
            innerObject.add("to_location_ids", a);
            JsonArray b = new JsonArray();
            b.add("STANDART");
            b.add("EXPRESS");
            innerObject.add("service_level_ids", b);
            arr.add(innerObject);*/
            String sp = String.format(templateISP, s);
            stringBuilder.append(sp + ", \n");

        }

        System.out.println(arr.toString());
        System.out.println("");
        System.out.println(stringBuilder.toString());

    }



    private static String templateFF = "{\n" +
            "  \"head\": {\n" +
            "    \"store_mapping\": [\n" +
            "      {\n" +
            "        \"store_id\": \"%s\",\n" +
            "        \"fulfillment_node_id\": \"%s\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"mode\": \"atp\"\n" +
            "  },\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"product_id\": \"98765566868686862868636862683232\",\n" +
            "      \"quantity\": 1,\n" +
            "      \"fulfillment_node_id\": \"%s\"\n" +
            "\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    private static String templateISP = "    \"%s\": {\n" +
            "      \"delivery_time\": \"Available now\",\n" +
            "      \"delivery_time_if_not_available\": \"N/A\",\n" +
            "      \"cost\": {\n" +
            "        \"price\": 0,\n" +
            "        \"tax_code\": \"\",\n" +
            "        \"currency_code\": \"USD\"\n" +
            "      } \n"+
            "} ";



    private static String template = "|\"from_location_id\": \"%s\",\n" +
            "            \"to_location_ids\": [\"*\"],\n" +
            "            \"service_level_ids\": [\n" +
            "                \"STANDART\",\n" +
            "                \"EXPRESS\"\n" +
            "            ]";

    private static String [] locations = {
       // "D100-PORTLG0100",
            "V-FFL-4835-PRINCEST",
            "V-FFL-4836-GEARYST",
            "V-FFL-4837-HIGHLANDPARK",
            "V-FFL-4838-TOWNCENTER",
            "V-FFL-4840-THEWESTCHESTER",
            "V-FFL-4842-PHIPPSPLAZA",
            "V-FFL-4845-BELLEVUESQ",
            "V-FFL-4906-WESTPORT",
            "V-FFL-5912-SEATTLEOUTLET",
            "V-FFL-4917-CHESTNUTHILL",
            "V-FFL-5920-SANMARCOS",
            "V-FFL-5922-WRENTHAM",
            "V-FFL-4924-BROOKFIELD",
            "V-FFL-4925-RIVEROAKS",
            "V-FFL-5932-CHICAGOPREMIUM",
            "V-FFL-4934-FORUMSHOPS",
            "V-FFL-4935-TYSONSGALLERIA",
            "V-FFL-4936-KINGOFPRUSSIA",
            "V-FFL-4938-FASHIONVALLEY",
            "V-FFL-4940-SOMERSET",
            "V-FFL-4941-INTLMARKETPLACE",
            "V-FFL-4943-ELPASEOVILLAGE",
            "V-FFL-4945-SHORTHILLSMALL",
            "V-FFL-4946-WATERSIDESHOPS",
            "V-FFL-4947-PACIFICPALISADES",
            "V-FFL-4955-PALMBEACHGARDENS",
            "V-FFL-4956-MALLATMILLENIA",
            "V-FFL-4958-FIFTHAVE",
            "V-FFL-4959-SANTANAROW",
            "V-FFL-4961-RIVERSIDE",
            "V-FFL-5962-NATIONALHARBOR",
            "V-FFL-5963-ORLANDOVINE",
            "V-FFL-4971-EASTHAMPTON",
            "V-FFL-7630-DRAYCOTT",
            "V-FFL-4833-WASHINGTONST",
            "V-FFL-4844-STANFORDCENTER",
            "V-FFL-4846-EASTOAKST",
            "V-FFL-4847-FASHIONISLAND",
            "V-FFL-4901-MADISONAVE",
            "V-FFL-5904-CABAZON",
            "V-FFL-4907-GREENWICH",
            "V-FFL-4909-MERCER",
            "V-FFL-4910-COLUMBUSAVE",
            "V-FFL-5911-RIVERHEAD",
            "V-FFL-5913-LASVEGASOUTLET",
            "V-FFL-4915-NEWBURYST",
            "V-FFL-4918-PASADENA",
            "V-FFL-4919-WALNUTST",
            "V-FFL-5921-CARLSBAD",
            "V-FFL-5923-CAMARILLO",
            "V-FFL-5926-SFPREMIUMOUTLETS",
            "V-FFL-5927-SAWGRASSMILLS",
            "V-FFL-4928-CITYCENTERDC",
            "V-FFL-4929-MERRICKPARK",
            "V-FFL-4930-ABBOTKINNEY",
            "V-FFL-4931-SCOTTSDALEQRT",
            "V-FFL-5933-WOODBURYCOMMONS",
            "V-FFL-4939-THEGROVE",
            "V-FFL-4942-MELROSE",
            "V-FFL-4944-THEDOMAIN",
            "V-FFL-5948-FASHIONOUTLET",
            "V-FFL-4960-AVENTURA",
            "V-FFL-4965-ROOSEVELTFIED"


    };
}
