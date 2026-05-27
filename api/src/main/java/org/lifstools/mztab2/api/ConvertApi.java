package org.lifstools.mztab2.api;

import org.lifstools.mztab2.ApiClient;
import org.lifstools.mztab2.BaseApi;

import org.lifstools.mztab2.model.MzTab;

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
public class ConvertApi extends BaseApi {

    public ConvertApi() {
        super(new ApiClient());
    }

    public ConvertApi(ApiClient apiClient) {
        super(apiClient);
    }

    /**
     * 
     * Converts an mzTab file in JSON or XML format to the tab-separated representation. If this method returns an error code 422, the provided file did not pass validation. 
     * <p><b>200</b> - Conversion Okay
     * <p><b>415</b> - Unsupported content type
     * <p><b>422</b> - Invalid input
     * <p><b>0</b> - Unexpected error
     * @param mzTab mzTab file that should be validated. (required)
     * @return String
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public String convertMzTabFile(MzTab mzTab) throws RestClientException {
        return convertMzTabFileWithHttpInfo(mzTab).getBody();
    }

    /**
     * 
     * Converts an mzTab file in JSON or XML format to the tab-separated representation. If this method returns an error code 422, the provided file did not pass validation. 
     * <p><b>200</b> - Conversion Okay
     * <p><b>415</b> - Unsupported content type
     * <p><b>422</b> - Invalid input
     * <p><b>0</b> - Unexpected error
     * @param mzTab mzTab file that should be validated. (required)
     * @return ResponseEntity&lt;String&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<String> convertMzTabFileWithHttpInfo(MzTab mzTab) throws RestClientException {
        Object localVarPostBody = mzTab;
        
        // verify the required parameter 'mzTab' is set
        if (mzTab == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'mzTab' when calling convertMzTabFile");
        }
        

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

        ParameterizedTypeReference<String> localReturnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI("/convert", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
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
