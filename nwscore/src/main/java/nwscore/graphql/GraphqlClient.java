package nwscore.graphql;

import kong.unirest.HttpResponse;
import nwscore.NwsContext;
import nwscore.io.DefaultRestClient;
import nwscore.io.InvalidCredentialsException;
import nwscore.io.RestClient;
import nwscore.io.RestClientException;


public class GraphqlClient  {

    private static final String path = "/api/v1/org/data/query";

    private final NwsContext context;
    private final RestClient restClient;


    public GraphqlClient(NwsContext context) {
        this.context = context;
        this.restClient = new DefaultRestClient(this.context);
    }

    public HttpResponse<String> query(String query) throws RestClientException, InvalidCredentialsException {
        return restClient.post(path, query);
    }
}
