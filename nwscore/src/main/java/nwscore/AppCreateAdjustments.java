/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package nwscore;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import kong.unirest.HttpResponse;
import nwscore.graphql.DefaultGQLOrderService;
import nwscore.graphql.GQLOrderService;
import nwscore.graphql.GraphqlClient;
import nwscore.io.DefaultRestClient;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClient;

import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AppCreateAdjustments {

    public String getGreeting() {
        return "Hello world.";
    }




    public static void main(String[] args) throws Exception, InvalidCredentialsException {
//        main02(args);


        NwsContext context = NwsContext.Builder
                .start()
                .withCredentials(new Credential("btashan@newstore.com", "bORA@911!s"))
                .withTenant("vince")
                .withEnvironment(NwsEnvironment.STAGING)
                .withNwsUrl("newstore.net")
                .build();



        RestClient client = new DefaultRestClient(context);
        String path = "/v0/i/inventory/adjustment_reasons";



        for (int i = 1; i< table.length; i++) {
            String query = "{\n" +
                    "\"type\": \"%s\",\n" +
                    "\"subtype\": \"%s\",\n" +
                    "\"origin_stock_location\": \"%s\",\n" +
                    "\"destination_stock_location\": \"%s\"\n" +
                    "}\n";

            query = String.format(query, table[i][0], table[i][1], table[i][2], table[i][3]);

            System.out.println(query);
            HttpResponse<String> response = client.post(path, query);
            if (!response.isSuccess()) {
                System.out.println(response);
                throw new Exception(response.toString());
            } else {
                System.out.println(response);
            }


        }




    }


    private static String[][] table = {
            {"RETQUAL","sl_1st_qual -> sl_2nd_qual","sl_1st_qual","sl_2nd_qual"},
            {"RETQUAL","sl_1st_qual -> sl_qi","sl_1st_qual","sl_qi"},
            {"RETQUAL","sl_1st_qual -> sl_qh","sl_1st_qual","sl_qh"},
            {"RETQUAL","sl_2nd_qual -> sl_1st_qual","sl_2nd_qual","sl_1st_qual"},
            {"RETQUAL","sl_2nd_qual -> sl_qi", "sl_2nd_qual","sl_qi"},
            {"RETQUAL","sl_2nd_qual -> sl_qh","sl_2nd_qual","sl_qh"},
            {"RETQUAL","sl_qi -> sl_1st_qual","sl_qi", "sl_1st_qual"},
            {"RETQUAL", "sl_qi -> sl_2nd_qual","sl_qi","sl_2nd_qual"},
            {"RETQUAL","sl_qi -> sl_qh","sl_qi","sl_qh"},
            {"RETQUAL","sl_qh -> sl_1st_qual","sl_qh","sl_1st_qual"},
            {"RETQUAL", "sl_qh -> sl_2nd_qual","sl_qh","sl_2nd_qual"},
            {"RETQUAL","sl_qh -> sl_qi","sl_qh","sl_qi"},


            {"RETCUST","sl_1st_qual -> NULL","sl_1st_qual",""},
            {"RETCUST","sl_qi -> NULL","sl_qi",""},
            {"RETCUST","sl_qh -> NULL","sl_qh",""},

            {"RETDAM","sl_2nd_qual -> NULL","sl_2nd_qual",""},


            {"RETEMP","sl_1st_qual -> NULL", "sl_1st_qual",""},
            {"RETEMP","sl_qi -> NULL","sl_qi",""},
            {"RETEMP","sl_qh -> NULL","sl_qh",""},

            {"RETEXC","NULL -> sl_1st_qual","","sl_1st_qual"},
            {"RETLOSS","sl_1st_qual -> NULL","sl_1st_qual",""},
            {"RETLOSS","sl_qi -> NULL","sl_qi",""},
            {"RETLOSS","sl_qh -> NULL","sl_qh",""},

            {"RETMARK","sl_1st_qual -> NULL","sl_1st_qual",""},
            {"RETMARK","sl_qi -> NULL","sl_qi",""},
            {"RETMARK","sl_qh -> NULL","sl_qh",""},
            {"RETPR","sl_1st_qual -> NULL","sl_1st_qual",""},
            {"RETPR","sl_qi -> NULL","sl_qi",""},
            {"RETPR","sl_qh -> NULL","sl_qh",""}

    };

    private static String query = "{\"query\":\"query MyQuery {   orders(first: 10, orderBy: CREATED_AT_DESC) {     edges {       node {         id         associateEmail         customerEmail         createdAt         channel \\t\\t\\t\\tchannelType         customerId         demandLocationId         isExchange         isHistorical         currency \\t\\t\\t\\tpriceMethod         subtotal         discountTotal         taxTotal         taxStrategy         shippingTax         shippingTotal         taxExempt \\t\\t\\t  grandTotal                 status       }     }   } } \",\"variables\":{}}";
}
