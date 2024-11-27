package com.github.gun2.managerapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>http response정보를 가지는 타입</p>
 */
@Getter
@Setter
@ToString
public class HttpResponseDto {
    private String status;  //상태
    private String method;  //method
    private String url; //url
    private LocalDateTime recordTime = LocalDateTime.now();
    private long time;  //소요시간
    private Map<String, String> requestHeaderMap = new HashMap<>();
    private Map<String, String> responseHeaderMap = new HashMap<>();
    private String requestBody;
    private String responseBody;

    public void setRequest(HttpEntity<String> requestEntity, String url, String method){
        this.requestBody = requestEntity.getBody();
        this.requestHeaderMap = requestEntity.getHeaders().toSingleValueMap();
        this.url = url;
        this.method = method;
    }

    public void setResponse(ResponseEntity<String> response){
        this.responseBody = response.getBody();
        this.status = String.valueOf(response.getStatusCodeValue());
        this.responseHeaderMap = response.getHeaders().toSingleValueMap();
    }

    public void setResponse(HttpStatusCodeException httpStatusCodeException){
        this.responseBody = httpStatusCodeException.getResponseBodyAsString();
        this.status = String.valueOf(httpStatusCodeException.getRawStatusCode());
        this.responseHeaderMap = httpStatusCodeException.getResponseHeaders().toSingleValueMap();
    }

    public void setResponse(RestClientException restClientException){
        this.responseBody = restClientException.getMostSpecificCause().getMessage();
        this.status = restClientException.getMostSpecificCause().getClass().getSimpleName();
    }

    public void setResponse(Exception e){
        this.responseBody = e.getMessage();
        this.status = e.getClass().getSimpleName();
    }

    public <T extends Exception> void setErrorResponse(T e){
        if (e instanceof HttpStatusCodeException httpStatusCodeException){
            setResponse(httpStatusCodeException);
        }else if(e instanceof RestClientException restClientException){
            setResponse(restClientException);
        }else{
            setResponse(e);
        }
    }

}
