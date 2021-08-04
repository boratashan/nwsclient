package nwscore.io;

import kong.unirest.HttpResponse;

import java.io.IOException;

public interface RestClient {
    HttpResponse<String> post(String path, String query) throws RestClientException, InvalidCredentialsException;
    HttpResponse<String> get();

}
