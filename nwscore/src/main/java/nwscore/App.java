/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package nwscore;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import nwscore.graphql.DefaultGQLOrderService;
import nwscore.graphql.GQLOrderService;
import nwscore.graphql.GraphqlClient;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClientException;

import java.io.*;
import java.time.LocalDateTime;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }


    public static void main02(String[] args) throws Exception {

        JsonReader reader = new JsonReader(new FileReader("c:/input/orders-Lines-50000Headers.json"));
        JsonElement object =  JsonParser.parseReader(reader);



        if (true)
            throw new Exception("Halt");
    }

    public static void main(String[] args) throws Exception, InvalidCredentialsException {
//        main02(args);


        NwsContext context = NwsContext.Builder
                .start()
                .withCredentials(new Credential("btashan@newstore.com", "bORA@911!s"))
                .withTenant("ganni")
                .withEnvironment(NwsEnvironment.PRODUCTION)
                .withNwsUrl("newstore.net")
                .build();

        GraphqlClient graphqlClient = new GraphqlClient(context);
        GQLOrderService service = new DefaultGQLOrderService(graphqlClient, context);


        int f = 100;
        int o = 0; //0;
        JsonArray arr  = new JsonArray();

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime fiveMinsLater = start.plusMinutes(5);
        int cntr = 0;
        int it = 0;
        boolean waitIdle = false;
        boolean timeToBreak = false;
        while (it < 1000*3) {
        //    while (o < 1) {

            /*if (waitIdle) {
                Thread.sleep(1000);
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(fiveMinsLater)) {
                    fiveMinsLater = now.plusMinutes(6);
                    cntr = 0;
                    waitIdle = false;
                }
                continue;
            }*/

            try {
                HttpResponse<String> response = service.fetchOrders(f, o);


                if (response.isSuccess()) {
                    JsonElement element = JsonParser.parseString(response.getBody());

                    if (true) {
                        JsonArray array = element.getAsJsonObject().get("data").getAsJsonObject().get("orders").getAsJsonObject().get("edges").getAsJsonArray();
                        arr.addAll(array);
                    }
                    else {
                        JsonArray items = element.getAsJsonObject().get("data").getAsJsonObject().get("orders").getAsJsonObject().get("edges").getAsJsonArray();

                        for (int i = 0; i < items.size(); i++) {

                            JsonArray i2 = items.get(i).getAsJsonObject().get("node").getAsJsonObject().get("items").getAsJsonObject().get("edges").getAsJsonArray();
                            arr.addAll(i2);
                        }

                    }
                    System.out.println(String.format("it : %d   cntr : %d", it, cntr));
                }

            }catch (Exception e) {
                System.out.println("Time to break");
                timeToBreak = true;
            }
            if (timeToBreak)
                break;
            cntr++; it++;
            o = o + f;

        }

        PrintWriter printWriter = new PrintWriter("c:/input/orders-new.json");
        for (int i = 0; i < arr.size(); i ++) {
            printWriter.println(arr.get(i).getAsJsonObject().toString());
        }
        printWriter.flush();

        //Gson gson = new Gson();
        //gson.toJson(arr, new JsonWriter(new FileWriter("c:/input/order-headers.json")));

        /*
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter("c:/input/items.json");
*/
        /*FileWriter fileWriter = new FileWriter("c:/input/items.json",false);
        fileWriter.write(arr.toString());*/
/*
        for (int i = 0; i < arr.size(); i ++) {

            printWriter.println(arr.get(i).getAsJsonObject().toString());
            printWriter.flush();*/
            //writer.write(arr.get(i).toString());
            //fileWriter.write(arr.get(i).toString());
            //Thread.sleep(10);
        //}



        System.out.println(arr.size());





    }


    private static String query = "{\"query\":\"query MyQuery {   orders(first: 10, orderBy: CREATED_AT_DESC) {     edges {       node {         id         associateEmail         customerEmail         createdAt         channel \\t\\t\\t\\tchannelType         customerId         demandLocationId         isExchange         isHistorical         currency \\t\\t\\t\\tpriceMethod         subtotal         discountTotal         taxTotal         taxStrategy         shippingTax         shippingTotal         taxExempt \\t\\t\\t  grandTotal                 status       }     }   } } \",\"variables\":{}}";
}
