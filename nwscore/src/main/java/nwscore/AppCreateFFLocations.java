/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package nwscore;

import kong.unirest.HttpResponse;
import nwscore.io.DefaultRestClient;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClient;

public class AppCreateFFLocations {

    public String getGreeting() {
        return "Hello world.";
    }




    public static void main(String[] args) throws Exception, InvalidCredentialsException {
//        main02(args);


        NwsContext context = NwsContext.Builder
                .start()
                .withCredentials(new Credential("btashan@newstore.com", "bORA@911!s"))
                .withTenant("vince")
                .withEnvironment(NwsEnvironment.PRODUCTION)
                .withNwsUrl("newstore.net")
                .build();



        RestClient client = new DefaultRestClient(context);
        String path = "/v0/d/availabilities/groups";

        String template = "\"%s\":{\"value\":1}, \n";

        template = "        \"%s\": {\n" +
                "            \"cost\": {\n" +
                "                \"currency_code\": \"USD\",\n" +
                "                \"price\": 0,\n" +
                "                \"tax_code\": \"\"\n" +
                "            },\n" +
                "            \"delivery_time\": \"Available now\",\n" +
                "            \"delivery_time_if_not_available\": \"N/A\"\n" +
                "        }, \n";

        template = "        {\n" +
                "            \"from_location_id\": \"%s\",\n" +
                "            \"service_level_ids\": [\n" +
                "                \"STANDARD\",\n" +
                "                \"EXPRESS\",\n" +
                "                \"OVERNIGHT\"\n" +
                "            ],\n" +
                "            \"to_location_ids\": [\n" +
                "                \"*\"\n" +
                "            ]\n" +
                "        },";



        template = "{\n" +
                "  \"inventory_list_id\": \"invfg-%s\",\n" +
                "  \"config\": {\n" +
                "    \"catalog\":\"storefront-catalog-en\",\n" +
                "    \"locale\":\"en-us\",\n" +
                "    \"handling\":\"preorder\"\n" +
                "  }\n" +
                "}";

        template = "{\n" +
                "  \"inventory_list_id\": \"invfg-%s\",\n" +
                "  \"config\": {\n" +
                "    \"catalog\":\"storefront-catalog-en\",\n" +
                "    \"locale\":\"en-us\",\n" +
                "    \"external_id\":\"upc\",\n" +
                "    \"handling\":\"preorder\"\n" +
                "  }\n" +
                "}";
         path = "/sfcc-api/v1/mapping_config/availability";
         template = "      {\n" +
                 "        \"fulfillment_node_id\": \"%s\",\n" +
                 "        \"location_code\": \"%s\"\n" +
                 "      },";

         template = "\"%s\": {\n" +
                 "            \"cost\": {\n" +
                 "                \"currency_code\": \"USD\",\n" +
                 "                \"price\": 0,\n" +
                 "                \"tax_code\": \"\"\n" +
                 "            },\n" +
                 "            \"delivery_time\": \"Available now\",\n" +
                 "            \"delivery_time_if_not_available\": \"N/A\"\n" +
                 "        },";
      //  String path2 = "/v0/locations/%s/inventory_master/_enable";
        StringBuilder sb  = new StringBuilder();
        for (int i = 0; i< locations.length; i++) {
            String storeID = locations[i].substring(6);
            String fffId = locations[i];
            sb.append(String.format(template, fffId));

     //       String query = String.format(template, fffId);

         /*  HttpResponse<String> response = client.post(path, query);
            if (!response.isSuccess()) {
                System.out.println(response);
                throw new Exception(response.toString());
            } else {
                System.out.println(response);
            }
            Thread.sleep(1000);
*/

        }

        System.out.println(sb.toString());




    }


    private static String groupTemplate = " {\n" +
            "            \"fulfillment_nodes\": [\n" +
            "                \"%s\"\n" +
            "            ],\n" +
            "            \"group_name\": \"invfg-%s\"\n" +
            "        }";

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



    private static String[] fflocations = {
            "V-FFL-4847-FASHIONISLAND",
            "V-FFL-4909-MERCER",
            "V-FFL-5911-RIVERHEAD",
            "V-FFL-4915-NEWBURYST",
            "V-FFL-4919-WALNUTST",
            "V-FFL-5923-CAMARILLO",
            "V-FFL-5927-SAWGRASSMILLS",
            "V-FFL-4929-MERRICKPARK",
            "V-FFL-4931-SCOTTSDALEQRT",
            "V-FFL-4939-THEGROVE",
            "V-FFL-4833-WASHINGTONST",
            "V-FFL-4846-EASTOAKST",
            "V-FFL-4901-MADISONAVE",
            "V-FFL-4907-GREENWICH",
            "V-FFL-4910-COLUMBUSAVE",
            "V-FFL-5913-LASVEGASOUTLET",
            "V-FFL-5921-CARLSBAD",
            "V-FFL-5926-SFPREMIUMOUTLETS",
            "V-FFL-4928-CITYCENTERDC",
            "V-FFL-4930-ABBOTKINNEY",
            "V-FFL-4942-MELROSE",
            "V-FFL-5948-FASHIONOUTLET",
            "V-FFL-4944-THEDOMAIN",
            "V-FFL-4960-AVENTURA",
            "V-FFL-5933-WOODBURYCOMMONS",
            "V-FFL-5904-CABAZON"
    };

    private static String [] locations = {

            "V-FFL-4844-STANFORDCENTER",
            "V-FFL-4846-EASTOAKST",
            "V-FFL-4847-FASHIONISLAND",
            "V-FFL-4901-MADISONAVE",
            "V-FFL-4907-GREENWICH",
            "V-FFL-4909-MERCER",
            "V-FFL-4910-COLUMBUSAVE",
            "V-FFL-4915-NEWBURYST",
            "V-FFL-4919-WALNUTST",
            "V-FFL-4928-CITYCENTERDC",
            "V-FFL-4929-MERRICKPARK",
            "V-FFL-4930-ABBOTKINNEY",
            "V-FFL-4931-SCOTTSDALEQRT",
            "V-FFL-4939-THEGROVE",
            "V-FFL-4942-MELROSE",
            "V-FFL-4944-THEDOMAIN",
            "V-FFL-4960-AVENTURA",
            "V-FFL-4834-MALIBUCNTRYMART",
            "V-FFL-4835-PRINCEST",
            "V-FFL-4836-GEARYST",
            "V-FFL-4838-TOWNCENTER",
            "V-FFL-4840-THEWESTCHESTER",
            "V-FFL-4842-PHIPPSPLAZA",
            "V-FFL-4845-BELLEVUESQ",
            "V-FFL-4906-WESTPORT",
            "V-FFL-4917-CHESTNUTHILL",
            "V-FFL-4924-BROOKFIELD",
            "V-FFL-4925-RIVEROAKS",
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
            "V-FFL-4959-SANTANAROW",
            "V-FFL-4961-RIVERSIDE",
            "V-FFL-4971-EASTHAMPTON",
            "V-FFL-4965-ROOSEVELTFIED",
            "V-FFL-4972-KNOXST",
            "V-FFL-4964-SOUTHPARK",
            "V-FFL-4970-CHERRYCREEK"

            /*



            "D100-PORTLG0100",
                "V-FFL-5912-SEATTLEOUTLET",


            "V-FFL-5922-WRENTHAM",



            "V-FFL-4940-SOMERSET",



            "V-FFL-4958-FIFTHAVE",

            "V-FFL-5963-ORLANDOVINE",
            "V-FFL-7630-DRAYCOTT",
            "V-FFL-5911-RIVERHEAD",
            "V-FFL-5923-CAMARILLO",
            "V-FFL-5927-SAWGRASSMILLS",
            "V-FFL-4845-BELLEVUESQ",










            "V-FFL-5962-NATIONALHARBOR",

            "V-FFL-4833-WASHINGTONST",




        "V-FFL-5913-LASVEGASOUTLET",
        "V-FFL-4918-PASADENA",
        "V-FFL-5921-CARLSBAD",
        "V-FFL-5926-SFPREMIUMOUTLETS",



        "V-FFL-5948-FASHIONOUTLET",

        "V-FFL-5920-SANMARCOS",
        "V-FFL-4837-HIGHLANDPARK",

        "V-FFL-5932-CHICAGOPREMIUM",




        "V-FFL-5967-CLARKSBURGPREM",
        "V-FFL-5968-HOUSTONPREM",
        "V-FFL-5969-LEESBURGPREM",
        "V-FFL-4966-PENTAGONCITY",
        "V-FFL-5933-WOODBURYCOMMONS",
        "V-FFL-5904-CABAZON"
*/


    };
    private static String query = "{\"query\":\"query MyQuery {   orders(first: 10, orderBy: CREATED_AT_DESC) {     edges {       node {         id         associateEmail         customerEmail         createdAt         channel \\t\\t\\t\\tchannelType         customerId         demandLocationId         isExchange         isHistorical         currency \\t\\t\\t\\tpriceMethod         subtotal         discountTotal         taxTotal         taxStrategy         shippingTax         shippingTotal         taxExempt \\t\\t\\t  grandTotal                 status       }     }   } } \",\"variables\":{}}";
}
