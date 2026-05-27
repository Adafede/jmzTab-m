package org.lifstools.mztab2.api;

import org.lifstools.mztab2.ApiClient;
import org.lifstools.mztab2.BaseApi;

import org.lifstools.mztab2.model.Error;
import org.lifstools.mztab2.model.ValidationMessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-04-29T15:29:26.131341+02:00[Europe/Berlin]", comments = "Generator version: 7.17.0")
public class ValidatePlainApi extends BaseApi {

    public ValidatePlainApi() {
        super(new ApiClient());
    }

    public ValidatePlainApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * 
     * Validates an mzTab file in plain text representation / tab-separated format and reports syntactic, structural, and semantic errors. 
     * <p><b>200</b> - Validation Okay
     * <p><b>415</b> - Unsupported content type
     * <p><b>422</b> - Invalid input
     * <p><b>0</b> - Unexpected error
     * @param body mzTab file that should be validated. (required)
     * @param level The level of errors that should be reported, one of ERROR, WARN, INFO. (optional, default to info)
     * @param maxErrors The maximum number of errors to return. (optional, default to 100)
     * @param semanticValidation Whether a semantic validation against the default rule set should be performed. (optional, default to false)
     * @return List&lt;ValidationMessage&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<ValidationMessage> validatePlainMzTabFile(String body, String level, Integer maxErrors, Boolean semanticValidation) throws RestClientException {
        return validatePlainMzTabFileWithHttpInfo(body, level, maxErrors, semanticValidation).getBody();
    }

    /**
     * 
     * Validates an mzTab file in plain text representation / tab-separated format and reports syntactic, structural, and semantic errors. 
     * <p><b>200</b> - Validation Okay
     * <p><b>415</b> - Unsupported content type
     * <p><b>422</b> - Invalid input
     * <p><b>0</b> - Unexpected error
     * @param body mzTab file that should be validated. (required)
     * @param level The level of errors that should be reported, one of ERROR, WARN, INFO. (optional, default to info)
     * @param maxErrors The maximum number of errors to return. (optional, default to 100)
     * @param semanticValidation Whether a semantic validation against the default rule set should be performed. (optional, default to false)
     * @return ResponseEntity&lt;List&lt;ValidationMessage&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<ValidationMessage>> validatePlainMzTabFileWithHttpInfo(String body, String level, Integer maxErrors, Boolean semanticValidation) throws RestClientException {
        Object localVarPostBody = body;
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling validatePlainMzTabFile");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "level", level));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "maxErrors", maxErrors));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "semanticValidation", semanticValidation));
        

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "text/tab-separated-values", "text/plain"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<List<ValidationMessage>> localReturnType = new ParameterizedTypeReference<List<ValidationMessage>>() {};
        return apiClient.invokeAPI("/validatePlain", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }

    @Override
    public <T> ResponseEntity<T> invokeAPI(String url, HttpMethod method, Object request, ParameterizedTypeReference<T> returnType) throws RestClientException {
        String localVarPath = url.replace(apiClient.getBasePath(), "");
        Object localVarPostBody = request;

        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "text/tab-separated-values", "text/plain"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        return apiClient.invokeAPI(localVarPath, method, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, returnType);
    }
}
