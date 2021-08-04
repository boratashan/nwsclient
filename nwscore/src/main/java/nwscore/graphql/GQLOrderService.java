package nwscore.graphql;

import kong.unirest.HttpResponse;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClientException;

public interface GQLOrderService {
    HttpResponse<String> fetchOrders(int first, int offset) throws InvalidCredentialsException, RestClientException;
}
