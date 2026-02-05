package hu.gov.nav;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-02-03T10:46:30.977683945Z[GMT]")
@Component("hu.gov.nav.TokenServiceApi")
public class TokenServiceApi {
    private CommonApiClient commonApiClient;

    public TokenServiceApi() {
        this(new CommonApiClient());
    }

    @Autowired
    public TokenServiceApi(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    public CommonApiClient getApiClient() {
        return commonApiClient;
    }

    public void setApiClient(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    /**
     * Token.createToken
     * Új token kérését biztosító művelet, amely egy belépést és egy token frissítést lehetővé tevő tokent ad vissza. (Letrehozva a Token.createToken(requestData: CreateTokenRequestType, messageId: string, correlationId: string):CreateTokenResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return CreateTokenResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CreateTokenResponseType createToken(CreateTokenRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        return createTokenWithHttpInfo(body, messageId, correlationId).getBody();
    }

    /**
     * Token.createToken
     * Új token kérését biztosító művelet, amely egy belépést és egy token frissítést lehetővé tevő tokent ad vissza. (Letrehozva a Token.createToken(requestData: CreateTokenRequestType, messageId: string, correlationId: string):CreateTokenResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ResponseEntity&lt;CreateTokenResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CreateTokenResponseType> createTokenWithHttpInfo(CreateTokenRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createToken");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling createToken");
        }
        String path = UriComponentsBuilder.fromPath("/NavM2mCommon/tokenService/Token").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (messageId != null)
            headerParams.add("messageId", commonApiClient.parameterToString(messageId));
        if (correlationId != null)
            headerParams.add("correlationId", commonApiClient.parameterToString(correlationId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = commonApiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = commonApiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "bearerAuth" };

        ParameterizedTypeReference<CreateTokenResponseType> returnType = new ParameterizedTypeReference<CreateTokenResponseType>() {};
        return commonApiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
