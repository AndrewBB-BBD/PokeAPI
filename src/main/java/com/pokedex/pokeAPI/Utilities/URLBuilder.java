package com.pokedex.pokeAPI.Utilities;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class URLBuilder {
    private String baseUrl;
    private String client_id;
    private String code_challenge;
    private String response_type;
    private String scope;
    private String state;
    private String method;
    private String redirect_uri;

    public URLBuilder() {}

    public URLBuilder(String baseUrl) {
        if (baseUrl.endsWith("?"))
            this.baseUrl = baseUrl;
        else
            this.baseUrl = baseUrl + "?";
    }

    public URLBuilder(String baseUrl, String endpoint) {
        if (!baseUrl.endsWith("/"))
           this.baseUrl = baseUrl + "/";
        if (endpoint.endsWith("?"))
            this.baseUrl += endpoint;
        else
            this.baseUrl += endpoint + "?";
    }

    public URLBuilder baseUrl(String baseUrl) {
        if (baseUrl.endsWith("?"))
            this.baseUrl = baseUrl;
        else
            this.baseUrl = baseUrl + "?";
        return this;
    }

    public URLBuilder baseUrl(String baseUrl, String endpoint) {
        if (!baseUrl.startsWith("http://"))
            baseUrl = "http://" + baseUrl;
        if (!baseUrl.endsWith("/"))
            this.baseUrl = baseUrl + "/";
        if (endpoint.endsWith("?"))
            this.baseUrl += endpoint;
        else
            this.baseUrl += endpoint + "?";
        return this;
    }

    public URLBuilder clientId(String clientId) {
        this.client_id = clientId;
        return this;
    }

    public URLBuilder code_challenge_method(String method) {
        this.method = method;
        return this;
    }

    public URLBuilder responseType(String responseType) {
        this.response_type = responseType;
        return this;
    }

    public URLBuilder scope(String scope) {
        this.scope = scope;
        return this;
    }

    public URLBuilder state(String state) {
        this.state = state;
        return this;
    }

    public URLBuilder code_challenge(String challenge) {
        this.code_challenge = challenge;
        return this;
    }

    public URLBuilder redirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
        return this;
    }

    public URLBuilder queryParams(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            this.baseUrl += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return this;
    }

    public String build() throws NoSuchFieldException, IllegalAccessException {
        StringBuilder outputURL = new StringBuilder();
        outputURL.append(baseUrl);
        for (Field field : this.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            if (!fieldName.equals("baseUrl")) {
                String fieldValue = (String) field.get(this);
                if (fieldValue != null) {
                    outputURL.append(fieldName).append("=").append(fieldValue).append("&");
                }
            }
        }
        return outputURL.toString();
    }
}
