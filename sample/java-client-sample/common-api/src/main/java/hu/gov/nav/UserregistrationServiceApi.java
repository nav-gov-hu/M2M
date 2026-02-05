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
@Component("hu.gov.nav.UserregistrationServiceApi")
public class UserregistrationServiceApi {
    private CommonApiClient commonApiClient;

    public UserregistrationServiceApi() {
        this(new CommonApiClient());
    }

    @Autowired
    public UserregistrationServiceApi(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    public CommonApiClient getApiClient() {
        return commonApiClient;
    }

    public void setApiClient(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    /**
     * Activation.activateUserRegistration
     *  (Letrehozva a Activation.activateUserRegistration(requestData: ActivateUserRegistrationRequestType, messageId: char, correlationId: char, Authorization: char):ActivateUserRegistrationResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ActivateUserRegistrationResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ActivateUserRegistrationResponseType activateUserRegistration(ActivateUserRegistrationRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        return activateUserRegistrationWithHttpInfo(body, messageId, correlationId).getBody();
    }

    /**
     * Activation.activateUserRegistration
     *  (Letrehozva a Activation.activateUserRegistration(requestData: ActivateUserRegistrationRequestType, messageId: char, correlationId: char, Authorization: char):ActivateUserRegistrationResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ResponseEntity&lt;ActivateUserRegistrationResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<ActivateUserRegistrationResponseType> activateUserRegistrationWithHttpInfo(ActivateUserRegistrationRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling activateUserRegistration");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling activateUserRegistration");
        }
        String path = UriComponentsBuilder.fromPath("/NavM2mCommon/userregistrationService/Activation").build().toUriString();
        
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

        ParameterizedTypeReference<ActivateUserRegistrationResponseType> returnType = new ParameterizedTypeReference<ActivateUserRegistrationResponseType>() {};
        return commonApiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Nonce.redeemNonce
     * Nonce beváltását biztosító művelet (Letrehozva a Nonce.redeemNonce(requestData: RedeemNonceRequestType, messageId: char, correlationId: char, Authorization: char):RedeemNonceResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return RedeemNonceResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public RedeemNonceResponseType redeemNonce(RedeemNonceRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        return redeemNonceWithHttpInfo(body, messageId, correlationId).getBody();
    }

    /**
     * Nonce.redeemNonce
     * Nonce beváltását biztosító művelet (Letrehozva a Nonce.redeemNonce(requestData: RedeemNonceRequestType, messageId: char, correlationId: char, Authorization: char):RedeemNonceResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ResponseEntity&lt;RedeemNonceResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<RedeemNonceResponseType> redeemNonceWithHttpInfo(RedeemNonceRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling redeemNonce");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling redeemNonce");
        }
        String path = UriComponentsBuilder.fromPath("/NavM2mCommon/userregistrationService/Nonce").build().toUriString();
        
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

        ParameterizedTypeReference<RedeemNonceResponseType> returnType = new ParameterizedTypeReference<RedeemNonceResponseType>() {};
        return commonApiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
