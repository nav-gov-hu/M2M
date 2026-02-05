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
@Component("hu.gov.nav.FilestoreUploadServiceApi")
public class FilestoreUploadServiceApi {
    private CommonApiClient commonApiClient;

    public FilestoreUploadServiceApi() {
        this(new CommonApiClient());
    }

    @Autowired
    public FilestoreUploadServiceApi(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    public CommonApiClient getApiClient() {
        return commonApiClient;
    }

    public void setApiClient(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    /**
     * File.addFile
     * Egy fájl feltöltésére szolgáló művelet. A visszatérési értékben szerepel a file azonosítója, amivel a feldolgozások során hivatkozni lehet rá. (Letrehozva a File.addFile(*_/_*: file, correlationId: char, messageId: char, Authorization: char, sha256hash: char, signature: char):AddFileResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @param sha256hash A fájltárolóba betöltendő fájlból képzett SHA256 hash. (optional)
     * @param signature Fájlfeltöltés aláírása. Aláírás képzése: SHA256(messageId+timestamp+sha256hash+signatureKey)  (optional)
     * @return AddFileResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public AddFileResponseType addFile(Object body, String messageId, String correlationId, String sha256hash, String signature) throws RestClientException {
        return addFileWithHttpInfo(body, messageId, correlationId, sha256hash, signature).getBody();
    }

    /**
     * File.addFile
     * Egy fájl feltöltésére szolgáló művelet. A visszatérési értékben szerepel a file azonosítója, amivel a feldolgozások során hivatkozni lehet rá. (Letrehozva a File.addFile(*_/_*: file, correlationId: char, messageId: char, Authorization: char, sha256hash: char, signature: char):AddFileResponseType operation-bol.)
     * <p><b>200</b>
     * @param body  (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @param sha256hash A fájltárolóba betöltendő fájlból képzett SHA256 hash. (optional)
     * @param signature Fájlfeltöltés aláírása. Aláírás képzése: SHA256(messageId+timestamp+sha256hash+signatureKey)  (optional)
     * @return ResponseEntity&lt;AddFileResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<AddFileResponseType> addFileWithHttpInfo(Object body, String messageId, String correlationId, String sha256hash, String signature) throws RestClientException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling addFile");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling addFile");
        }
        String path = UriComponentsBuilder.fromPath("/NavM2mCommon/filestoreUploadService/File").build().toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        queryParams.putAll(commonApiClient.parameterToMultiValueMap(null, "sha256hash", sha256hash));
        queryParams.putAll(commonApiClient.parameterToMultiValueMap(null, "signature", signature));
        if (correlationId != null)
            headerParams.add("correlationId", commonApiClient.parameterToString(correlationId));
        if (messageId != null)
            headerParams.add("messageId", commonApiClient.parameterToString(messageId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = commonApiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = { 
            "*/*"
         };
        final MediaType contentType = commonApiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "bearerAuth" };

        ParameterizedTypeReference<AddFileResponseType> returnType = new ParameterizedTypeReference<AddFileResponseType>() {};
        return commonApiClient.invokeAPI(path, HttpMethod.POST, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
