package nwscore.graphql;

import kong.unirest.HttpResponse;
import nwscore.NwsContext;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClientException;

public class DefaultGQLOrderService implements GQLOrderService {

    private GraphqlClient graphqlClient;
    private NwsContext context;

    public DefaultGQLOrderService(GraphqlClient graphqlClient, NwsContext context) {
        this.graphqlClient = graphqlClient;
        this.context = context;
    }

    @Override
    public HttpResponse<String> fetchOrders(int first, int offset) throws InvalidCredentialsException, RestClientException {

        String q = String.format(queryLastOrderHeaders,  first, offset);
        q = String.format("{\"query\":\"%s\",\"variables\":{}}", q);
        return  graphqlClient.query(q);
    }





    //private String  query02 = "{\"query\":\"query MyQuery {\\r\  orders(first: 10, orderBy: CREATED_AT_DESC) {\\r\    edges {\\r\      node {\\r\        id\\r\        associateEmail\\r\        customerEmail\\r\      }\\r\    }\\r\  }\\r\}\\r\\",\"variables\":{}}";
    private String queryLastOrderItems = "query MyQuery {" +
            "  orders(first: 100, offset: 0, orderBy: CREATED_AT_DESC) {" +
            "    edges {" +
            "      node {" +
            "        items {" +
            "          edges {" +
            "            node {" +
            "              tenant" +
            "              taxClass" +
            "              tax" +
            "              status" +
            "              shippingServiceLevel" +
            "              quantity" +
            "              productId" +
            "              pricebookPrice" +
            "              pricebookId" +
            "              orderId" +
            "              orderDiscounts" +
            "              onHoldReason" +

            "              listPrice" +
            "              itemType" +
            "              id" +
            "              fulfillmentLocationId" +
            "              itemDiscounts" +
            "              cancellationReason" +
            "            }" +
            "          }" +
            "        }" +
            "      }" +
            "    }" +
            "    totalCount" +
            "    pageInfo {" +
            "      hasNextPage" +
            "    }" +
            "  }" +
            "}";


    private String queryLastOrderHeaders = "query MyQuery {" +
            "  orders(first: 100, offset: 0, orderBy: CREATED_AT_DESC) {" +
            "    edges {" +
            "      node {" +
            "        associateEmail" +
            "        associateId" +
            "        captureType" +
            "        channel" +
            "        channelType" +
            "        createdAt" +
            "        currency" +
            "        customerEmail" +
            "        customerId" +
            "        demandLocationId" +
            "        discountTotal" +
            "        grandTotal" +
            "        externalCustomerId" +
            "        externalId" +
            "        id" +
            "        isExchange" +
            "        isHistorical" +
            "        nodeId" +
            "        priceMethod" +
            "        placedAt" +
            "        tenant" +
            "        taxTotal" +
            "        taxStrategy" +
            "        taxExempt" +
            "        subtotal" +
            "        status" +
            "        shippingTotal" +
            "        shippingTax" +
            "      }" +
            "    }" +
            "    totalCount" +
            "    pageInfo {" +
            "      hasNextPage" +
            "    }" +
            "  }" +
            "}";

    private String queryLast01 = "query MyQuery {" +
            "  orders(first: 100, offset: 0, orderBy: CREATED_AT_DESC) {" +
            "    edges {" +
            "      node {" +
            "        associateEmail" +
            "        associateId" +
            "        captureType" +
            "        channel" +
            "        channelType" +
            "        createdAt" +
            "        currency" +
            "        customerEmail" +
            "        customerId" +
            "        demandLocationId" +
            "        discountTotal" +
            "        grandTotal" +
            "        externalCustomerId" +
            "        externalId" +
            "        id" +
            "        isExchange" +
            "        isHistorical" +
            "        nodeId" +
            "        priceMethod" +
            "        placedAt" +
            "        tenant" +
            "        taxTotal" +
            "        taxStrategy" +
            "        taxExempt" +
            "        subtotal" +
            "        status" +
            "        shippingTotal" +
            "        shippingTax" +
            "        items {" +
            "          edges {" +
            "            node {" +
            "              tenant" +
            "              taxClass" +
            "              tax" +
            "              status" +
            "              shippingServiceLevel" +
            "              pricebookPrice" +
            "              pricebookId" +
            "              orderId" +
            "              orderDiscounts" +
            "              quantity" +
            "              productId" +
            "              onHoldReason" +
            "              listPrice" +
            "              itemType" +
            "              itemDiscounts" +
            "              id" +
            "              cancellationReason" +
            "            }" +
            "          }" +
            "        }" +
            "      }" +
            "    }" +
            "    totalCount" +
            "    pageInfo {" +
            "      hasNextPage" +
            "    }" +
            "  }" +
            "}";

    private String query01 = "query MyQuery {" +
            "  orders(first: %d, offset: %d, orderBy: CREATED_AT_ASC) {" +
            "    edges {" +
            "      node {" +
            "        associateEmail" +
            "        associateId" +
            "        captureType" +
            "        channel" +
            "        channelType" +
            "        createdAt" +
            "        currency" +
            "        customerEmail" +
            "        customerId" +
            "        demandLocationId" +
            "        discountTotal" +
            "        grandTotal" +
            "        externalCustomerId" +
            "        externalId" +
            "        id" +
            "        isExchange" +
            "        isHistorical" +
            "        nodeId" +
            "        priceMethod" +
            "        placedAt" +
            "        tenant" +
            "        taxTotal" +
            "        taxStrategy" +
            "        taxExempt" +
            "        subtotal" +
            "        status" +
            "        shippingTotal" +
            "        shippingTax" +
            "        discounts {" +
            "          edges {" +
            "            node {" +
            "              trigger" +
            "              tenant" +
            "              orderId" +
            "              nodeId" +
            "              discountId" +
            "              couponCode" +
            "            }" +
            "          }" +
            "        }" +
            "        fulfillmentRequests(first: 100) {" +
            "          edges {" +
            "            node {" +
            "              tenant" +
            "              serviceLevel" +
            "              orderId" +
            "              nodeId" +
            "              logicalTimestamp" +
            "              id" +
            "              fulfillmentLocationId" +
            "              createdAt" +
            "              associateId" +
            "            }" +
            "          }" +
            "        }" +
            "        items {" +
            "          edges {" +
            "            node {" +
            "              tenant" +
            "              taxClass" +
            "              tax" +
            "              status" +
            "              shippingServiceLevel" +
            "              pricebookPrice" +
            "              pricebookId" +
            "              orderId" +
            "              orderDiscounts" +
            "              quantity" +
            "              productId" +
            "              onHoldReason" +
            "              nodeId" +
            "              listPrice" +
            "              itemType" +
            "              itemDiscounts" +
            "              id" +
            "              cancellationReason" +
            "              extendedAttributes {" +
            "                edges {" +
            "                  node {" +
            "                    value" +
            "                    tenant" +
            "                    orderItemId" +
            "                    nodeId" +
            "                    name" +
            "                  }" +
            "                }" +
            "              }" +
            "              externalIdentifiers {" +
            "                edges {" +
            "                  node {" +
            "                    value" +
            "                    tenant" +
            "                    orderItemId" +
            "                    nodeId" +
            "                    name" +
            "                  }" +
            "                }" +
            "              }" +
            "            }" +
            "          }" +
            "        }" +
            "        paymentAccountTransactions {" +
            "          edges {" +
            "            node {" +
            "              amount" +
            "              currency" +
            "              nodeId" +
            "              transactionType" +
            "              tenant" +
            "              paymentAccountId" +
            "              instrumentId" +
            "              id" +
            "              correlationId" +
            "            }" +
            "          }" +
            "        }" +
            "      }" +
            "    }" +
            "    totalCount" +
            "    pageInfo {" +
            "      hasNextPage" +
            "    }" +
            "  }" +
            "}";






}
