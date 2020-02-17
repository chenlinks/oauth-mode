package com.oauth.mode.authentication.social.wechat.handler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * @author chenling
 * @date 2020/2/14 12:49
 * @since V1.0.0
 */
public class WechatErrorHandler  extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (HttpStatus.Series.CLIENT_ERROR.equals(response.getStatusCode().series())) {
            Map<String, Object> errorDetails = extractErrorDetailsFromResponse(response);
            throw new UncategorizedApiException("wechat",
                    errorDetails.containsKey("errmsg") ? (String) errorDetails.get("errmsg") : "Unknown error", null);
        }
        handleUncategorizedError(response);
    }

    private void handleUncategorizedError(ClientHttpResponse response) {
        try {
            super.handleError(response);
        } catch (Exception e) {
            throw new UncategorizedApiException("wechat", "Error consuming wechat REST api", e);
        }
    }

    private Map<String, Object> extractErrorDetailsFromResponse(ClientHttpResponse response) throws IOException {
        try {
            return new ObjectMapper(new JsonFactory()).<Map<String, Object>>readValue(response.getBody(),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (JsonParseException e) {
            return Collections.emptyMap();
        }
    }
}