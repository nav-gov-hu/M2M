package hu.gov.nav;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-02-17T13:30:43.992819144Z[GMT]")
@Component("hu.gov.nav.BizonylatServiceApi")
public class BizonylatServiceApi {
    private BizonylatApiClient bizonylatApiClient;

    public BizonylatServiceApi() {
        this(new BizonylatApiClient());
    }

    @Autowired
    public BizonylatServiceApi(BizonylatApiClient bizonylatApiClient) {
        this.bizonylatApiClient = bizonylatApiClient;
    }

    public BizonylatApiClient getApiClient() {
        return bizonylatApiClient;
    }

    public void setApiClient(BizonylatApiClient bizonylatApiClient) {
        this.bizonylatApiClient = bizonylatApiClient;
    }

    /**
     * Bizonylat.createBizonylat
     * A bizonylat létrehozását és validálását biztosító művelet. Visszaadja a bizonylat státuszát és a validációs hibákat. (Letrehozva a Bizonylat.createBizonylat(messageId: char, correlationId: char, requestData: CreateBizonylatRequestType):CreateBizonylatResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return CreateBizonylatResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public CreateBizonylatResponseType createBizonylat(CreateBizonylatRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        return createBizonylatWithHttpInfo(body, messageId, correlationId).getBody();
    }

    /**
     * Bizonylat.createBizonylat
     * A bizonylat létrehozását és validálását biztosító művelet. Visszaadja a bizonylat státuszát és a validációs hibákat. (Letrehozva a Bizonylat.createBizonylat(messageId: char, correlationId: char, requestData: CreateBizonylatRequestType):CreateBizonylatResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ResponseEntity&lt;CreateBizonylatResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<CreateBizonylatResponseType> createBizonylatWithHttpInfo(CreateBizonylatRequestBodyType body, String messageId, String correlationId) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createBizonylat");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling createBizonylat");
        }
        String path = UriComponentsBuilder.fromPath("/NavM2mBizonylat/bizonylatService/Bizonylat").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (messageId != null)
            headerParams.add("messageId", bizonylatApiClient.parameterToString(messageId));
        if (correlationId != null)
            headerParams.add("correlationId", bizonylatApiClient.parameterToString(correlationId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = bizonylatApiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType contentType = bizonylatApiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "bearerAuth" };

        ParameterizedTypeReference<CreateBizonylatResponseType> returnType = new ParameterizedTypeReference<CreateBizonylatResponseType>() {};
        return bizonylatApiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
    /**
     * Bizonylat.getBizonylat
     *  (Letrehozva a Bizonylat.getBizonylat(messageId: char, correlationId: char, ugyAzonosito: char):GetBizonylatResponseType operation-bol.)
     * <p><b>200</b>
     * @param ugyAzonosito Az ügy(bizonylatbeküldés) egyedi azonosítója, amnek a státusza le lesz kérdezbe Path parameterkent letrehozva! (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return GetBizonylatResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GetBizonylatResponseType getBizonylat(String ugyAzonosito, String messageId, String correlationId) throws RestClientException {
        return getBizonylatWithHttpInfo(ugyAzonosito, messageId, correlationId).getBody();
    }

    /**
     * Bizonylat.getBizonylat
     *  (Letrehozva a Bizonylat.getBizonylat(messageId: char, correlationId: char, ugyAzonosito: char):GetBizonylatResponseType operation-bol.)
     * <p><b>200</b>
     * @param ugyAzonosito Az ügy(bizonylatbeküldés) egyedi azonosítója, amnek a státusza le lesz kérdezbe Path parameterkent letrehozva! (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ResponseEntity&lt;GetBizonylatResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetBizonylatResponseType> getBizonylatWithHttpInfo(String ugyAzonosito, String messageId, String correlationId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'ugyAzonosito' is set
        if (ugyAzonosito == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'ugyAzonosito' when calling getBizonylat");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling getBizonylat");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("ugyAzonosito", ugyAzonosito);
        String path = UriComponentsBuilder.fromPath("/NavM2mBizonylat/bizonylatService/Bizonylat/{ugyAzonosito}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (messageId != null)
            headerParams.add("messageId", bizonylatApiClient.parameterToString(messageId));
        if (correlationId != null)
            headerParams.add("correlationId", bizonylatApiClient.parameterToString(correlationId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = bizonylatApiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = bizonylatApiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "bearerAuth" };

        ParameterizedTypeReference<GetBizonylatResponseType> returnType = new ParameterizedTypeReference<GetBizonylatResponseType>() {};
        return bizonylatApiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
