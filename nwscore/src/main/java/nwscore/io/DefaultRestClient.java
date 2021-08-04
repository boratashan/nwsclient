package nwscore.io;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kong.unirest.*;
import nwscore.NwsAccessToken;
import nwscore.NwsContext;
import nwscore.utils.IOUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultRestClient implements RestClient {
    private NwsContext context;
    private Gson gson;
    public DefaultRestClient(NwsContext context) {
        this.context = context;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    private Optional<String> getAuthorizationToken(boolean forceObtainingToken) throws RestClientException, InvalidCredentialsException {
        synchronized (context) {
            Optional<NwsAccessToken> current = context.getNwsAccessToken();
            if (current.isPresent()) {
                NwsAccessToken t = current.get();
                return Optional.of(String.format("%s %s", t.getToken_type(), t.getAccess_token()));
            } else {
                if (forceObtainingToken) {
                    String endPoint = IOUtils.joinPaths(context.getTargetUrl(), "/v0/token");
                    String body = String.format("username=%s&grant_type=password&password=%s",
                            context.getCredentials().getUserName(),
                            context.getCredentials().getPassword());
                    HttpResponse<String> response = Unirest.post(endPoint).body(body).asString();
                    if (response.isSuccess()) {
                        String s = response.getBody().toString();
                        NwsAccessToken token = gson.fromJson(s, NwsAccessToken.class);
                        context.setNwsAccessToken(token);
                        return Optional.of(String.format("%s %s", token.getToken_type(), token.getAccess_token()));
                    } else {
                        if (response.getStatus() == 403) {
                            throw new InvalidCredentialsException("Invalid credentials");
                        }
                        else {
                            throw new RestClientException("Something happened bad");
                        }
                    }
                } else {
                    return Optional.empty();
                }
            }
        }
    }


    private HttpResponse<String> doInternalRestCall(RequestType requestType, String path, Map<String, Object> queryStrings, String body, boolean withAuthorization) throws RestClientException, InvalidCredentialsException {
        try {
            String fullUrl = IOUtils.joinPaths(context.getTargetUrl(), path);
            Map<String, String> headers = new HashMap<>();
            if (withAuthorization) {
                Optional<String> token = this.getAuthorizationToken(true);
                headers.put("Authorization", token.get());
            }
            headers.put("Content-Type", "application/json");
            //Content-Type: application/json
            HttpResponse<String> response = null;
            switch (requestType) {
                case GET:
                    response = Unirest.get(fullUrl)
                            .headers(headers)
                            .queryString(queryStrings)
                            .asString();
                    break;
                case POST:
                    response = Unirest.post(fullUrl)
                            .headers(headers)
                            .queryString(queryStrings)
                            .body(body)
                            .asString();
                    break;
                case PUT:
                    response = Unirest.put(fullUrl)
                            .headers(headers)
                            .queryString(queryStrings)
                            .asString();
                    break;
                case DELETE:
                    response = Unirest.delete(fullUrl)
                            .headers(headers)
                            .queryString(queryStrings)
                            .asString();
                    break;
            }
            return response;
        }catch (UnirestException e) {
            throw new RestClientException(e);
        }
    }

    @Override
    public HttpResponse<String> post(String path, String query) throws RestClientException, InvalidCredentialsException {
        return this.doInternalRestCall(RequestType.POST, path, null, query, true);
    }

    @Override
    public HttpResponse<String> get() {
        return null;
    }
}
