package nwscore.io;


import com.google.common.io.CharSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kong.unirest.*;
import nwscore.NwsAccessToken;
import nwscore.NwsContext;
import nwscore.ffrectmodel.Response;
import nwscore.utils.IOUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.io.input.ReaderInputStream;
import sfccorderexp.app.utils.ConsoleUtils;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultRestClient implements RestClient {
    private final NwsContext context;
    private final Gson gson;
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
                        String s = response.getBody();
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
                            .body(body)
                            .asString();
                    break;
                case PATCH:
                    response = Unirest.patch(fullUrl)
                            .headers(headers)
                            .body(body)
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
    public HttpResponse<String> post(String path, Map<String, Object> bodyFields) throws RestClientException, InvalidCredentialsException {
        try {
            String fullUrl = IOUtils.joinPaths(context.getTargetUrl(), path);
            Map<String, String> headers = new HashMap<>();
            if (true) {
                Optional<String> token = this.getAuthorizationToken(true);
                headers.put("Authorization", token.get());
            }
            //headers.put("Content-Type", "multipart/form-data");

        InputStream targetStream = (InputStream) bodyFields.get("file");

            HttpResponse<String> response = Unirest.post(fullUrl)
                    .headers(headers)
                    .field("file", targetStream, ContentType.APPLICATION_OCTET_STREAM, "file.csv")
                    .field("count_type", bodyFields.get("count_type").toString())
                    .field("external_id", bodyFields.get("external_id").toString())
                    .asString();
            return response;
        } catch (UnirestException e) {
            throw new RestClientException(e);
        } /*catch (IOException e) {
            throw new RestClientException(e.toString());
        }*/
    }

    @Override
    public HttpResponse<String> put(String path, String body) throws RestClientException, InvalidCredentialsException {
        return this.doInternalRestCall(RequestType.PUT, path, null, body, true);
    }

    @Override
    public HttpResponse<String> patch(String path, String body) throws RestClientException, InvalidCredentialsException {
        return this.doInternalRestCall(RequestType.PATCH, path, null, body, true);
    }


    @Override
    public HttpResponse<String> put(String path, byte[] body) throws RestClientException, InvalidCredentialsException {
        try {
            String fullUrl = IOUtils.joinPaths(context.getTargetUrl(), path);
            Map<String, String> headers = new HashMap<>();
            if (false) {
                Optional<String> token = this.getAuthorizationToken(true);
                headers.put("Authorization", token.get());
            }
            headers.put("Content", "application/zip");
            //Content-Type: application/json
            HttpResponse<String> response = null;
            response = Unirest.put(path)
                    .headers(headers)
                    .body(body)
                    .asString();
            return response;
        }catch (UnirestException e) {
            throw new RestClientException(e);
        }
    }



    @Override
    public HttpResponse<String> get(String path, Map<String, Object> query, String body) throws InvalidCredentialsException, RestClientException {
        return this.doInternalRestCall(RequestType.GET, path, query, body, true);
    }
}
