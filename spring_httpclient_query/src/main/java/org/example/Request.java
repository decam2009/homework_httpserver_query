package org.example;

import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import javax.naming.Name;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

public class Request {
    private String method;
    private String pathRequest;
    private String body;

    private final URIBuilder uriBuilder;

    public Request(String method, String path, String body) throws URISyntaxException {
        this.method = method;
        this.pathRequest = path;
        this.body = body;
        this.uriBuilder = new URIBuilder(path, Charset.defaultCharset());
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathRequest() {
        return uriBuilder.getPath();
    }

    public void setPathRequest(String pathRequest) {
        this.pathRequest = pathRequest;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NameValuePair getQueryParam (String name){
        return uriBuilder.getFirstQueryParam(name);
    }

    public List<NameValuePair> getQueryParams(){
        return uriBuilder.getQueryParams();
    }

    @Override
    public String toString() {
        return "Запрос: [ метод - " + method + ", путь запроса - " + pathRequest + ",тело запроса - " + body + "]";
    }
}
