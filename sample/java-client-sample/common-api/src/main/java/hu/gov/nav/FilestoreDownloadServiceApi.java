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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2026-02-03T10:46:30.977683945Z[GMT]")
@Component("hu.gov.nav.FilestoreDownloadServiceApi")
public class FilestoreDownloadServiceApi {
    private CommonApiClient commonApiClient;

    public FilestoreDownloadServiceApi() {
        this(new CommonApiClient());
    }

    @Autowired
    public FilestoreDownloadServiceApi(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    public CommonApiClient getApiClient() {
        return commonApiClient;
    }

    public void setApiClient(CommonApiClient commonApiClient) {
        this.commonApiClient = commonApiClient;
    }

    /**
     * File.getFileStatus
     * Egy feltöltött fájl állapotának lekérdezése.  (Letrehozva a File.getFileStatus(fileId: char, correlationId: char, messageId: char, Authorization: char):GetFileStatusResponseType operation-bol.)
     * <p><b>200</b>
     * @param fileId A fájl egyedi azonosítója a fájltárolóban. Path parameterkent letrehozva! (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return GetFileStatusResponseType
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public GetFileStatusResponseType getFileStatus(String fileId, String messageId, String correlationId) throws RestClientException {
        return getFileStatusWithHttpInfo(fileId, messageId, correlationId).getBody();
    }

    /**
     * File.getFileStatus
     * Egy feltöltött fájl állapotának lekérdezése.  (Letrehozva a File.getFileStatus(fileId: char, correlationId: char, messageId: char, Authorization: char):GetFileStatusResponseType operation-bol.)
     * <p><b>200</b>
     * @param fileId A fájl egyedi azonosítója a fájltárolóban. Path parameterkent letrehozva! (required)
     * @param messageId Az üzenet egyedi azonosítója (required)
     * @param correlationId Üzenetek összekapcsolását biztosító egyedi azonosító. (optional)
     * @return ResponseEntity&lt;GetFileStatusResponseType&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<GetFileStatusResponseType> getFileStatusWithHttpInfo(String fileId, String messageId, String correlationId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'fileId' when calling getFileStatus");
        }
        // verify the required parameter 'messageId' is set
        if (messageId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'messageId' when calling getFileStatus");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("fileId", fileId);
        String path = UriComponentsBuilder.fromPath("/NavM2mCommon/filestoreDownloadService/File/{fileId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();
        if (correlationId != null)
            headerParams.add("correlationId", commonApiClient.parameterToString(correlationId));
        if (messageId != null)
            headerParams.add("messageId", commonApiClient.parameterToString(messageId));

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = commonApiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = commonApiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] { "bearerAuth" };

        ParameterizedTypeReference<GetFileStatusResponseType> returnType = new ParameterizedTypeReference<GetFileStatusResponseType>() {};
        return commonApiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
