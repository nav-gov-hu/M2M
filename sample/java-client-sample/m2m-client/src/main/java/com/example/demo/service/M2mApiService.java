package com.example.demo.service;

import com.example.demo.NAV.*;
import com.example.demo.util.GeneratePMT25;
import hu.gov.nav.*;
import hu.gov.nav.auth.OAuth;
import net.sf.saxon.s9api.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@PropertySource("user.properties")
public class M2mApiService {

    @Autowired
    TokenServiceApi tokenServiceApi;
    @Autowired
    UserregistrationServiceApi userregistrationServiceApi;
    @Autowired
    FilestoreUploadServiceApi filestoreUploadServiceApi;
    @Autowired
    BizonylatServiceApi bizonylatServiceApi;
    @Autowired
    PooledPBEStringEncryptor encryptor;

    @Value("${user.username}")
    String username;
    @Value("${user.password}")
    String password;
    @Value("${user.clientId}")
    String clientId;
    @Value("${user.clientSecret}")
    String clientSecret;
    @Value("${signature.nonce}")
    String nonce;
    @Value("${signature.first}")
    String signatureFirstPart;

    String token;
    String signatureSecondPart;
    ClassLoader classLoader;

    private static final Logger logger = LoggerFactory.getLogger(M2mApiService.class);

    public void start() throws Exception {
        classLoader = getClass().getClassLoader();
        String formName = "NAV_PMT25";
        String formVersion = "0.17";
        String form = formName+"_"+formVersion;

        login();
        userActivation();
        generateXml(); //ELMDIJ
        generateXmlFromJava(form, uploadAttachment()); //PMT25
        isValid(form);
        validateXml(form);
        createBizonylat(formName, formVersion);
    }

    private void login() {
        CreateTokenRequestBodyType request = new CreateTokenRequestBodyType();
        CreateTokenRequestType requestData = new CreateTokenRequestType();
        requestData.setUsername(username);
        requestData.setPassword(password);
        requestData.setClientId(clientId);
        requestData.setClientSecret(clientSecret);
        request.setRequestData(requestData);

        CreateTokenResponseType result = tokenServiceApi.createToken(request, UUID.randomUUID().toString(), null);
        logger.info("createTokenRequestBodyType:{}, createTokenResponseType:{}", request, result);
        token = result.getAccessToken();
    }

    private void userActivation() {
        CommonApiClient commonApiClient = new CommonApiClient();
        OAuth apiAuth = (OAuth) commonApiClient.getAuthentication("bearerAuth");
        apiAuth.setAccessToken(token);
        userregistrationServiceApi.setApiClient(commonApiClient);

        RedeemNonceRequestBodyType request = new RedeemNonceRequestBodyType();
        RedeemNonceRequestType requestData = new RedeemNonceRequestType();
        requestData.setNonce(nonce);
        request.setRequestData(requestData);

        RedeemNonceResponseType response = userregistrationServiceApi.redeemNonce(request, UUID.randomUUID().toString(), null);
        logger.info("RedeemNonceRequestBodyType:{}, RedeemNonceResponseType:{}", request, response);
        signatureSecondPart = encryptor.encrypt(response.getSignatureKeySecondPart());

        String messageId = UUID.randomUUID().toString();
        ActivateUserRegistrationRequestBodyType requestActivate = new ActivateUserRegistrationRequestBodyType();
        ActivateUserRegistrationRequestType requestDataActivate = new ActivateUserRegistrationRequestType();
        requestDataActivate.setSignature(generateSignature(messageId, ""));
        requestActivate.setRequestData(requestDataActivate);

        ActivateUserRegistrationResponseType responseActivate = userregistrationServiceApi.activateUserRegistration(requestActivate, messageId, null);
        logger.info("ActivateUserRegistrationRequestBodyType:{}, ActivateUserRegistrationResponseType:{}", requestActivate, responseActivate);
    }

    private void generateXml() throws TransformerException, IOException {
        Source xmlSource = new StreamSource(Objects.requireNonNull(classLoader.getResourceAsStream("ELMDIJ_source.xml")));
        Source xsltSource = new StreamSource(Objects.requireNonNull(classLoader.getResourceAsStream("ELMDIJ_source_generator.xslt")));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,true);
        Transformer transformer = transformerFactory.newTransformer(xsltSource);

        StreamResult result = new StreamResult("output/ELMDIJ.xml");
        transformer.transform(xmlSource, result);
        logger.info("Az ELMDIJ nyomtatvány xml előállítása XSL transzformációval: \n" + Files.readString(Paths.get("output/ELMDIJ.xml")));
    }

    private AttachmentType uploadAttachment() {
        CommonApiClient commonApiClient = new CommonApiClient();
        OAuth apiAuth = (OAuth) commonApiClient.getAuthentication("bearerAuth");
        apiAuth.setAccessToken(token);
        filestoreUploadServiceApi.setApiClient(commonApiClient);

        String messageId = UUID.randomUUID().toString();
        String toUpload = Objects.requireNonNull(classLoader.getResource("full_check.xsl")).getFile();
        String hash = DigestUtils.sha256Hex(toUpload);
        AddFileResponseType response = filestoreUploadServiceApi.addFile(toUpload.getBytes(StandardCharsets.UTF_8)
                , messageId, null, hash, generateSignature(messageId, hash));
        logger.info("addFileResponseType:{}", response);

        if (response.getResultCode().equals(FileUploadResult.UPLOAD_SUCCESS)) {
            AttachmentType attachment = new AttachmentType();
            attachment.setFileId(response.getFileId());
            attachment.setFileSize(BigInteger.valueOf(toUpload.length()));
            attachment.setFileName("teszt.xml");
            return attachment;
        } else {
            return null;
        }
    }

    private void generateXmlFromJava(String formName, AttachmentType attachment) throws JAXBException, IOException {
        DocNAVPMT25 pmt25 = new DocNAVPMT25();
        pmt25.setFormNAVPMT25(GeneratePMT25.getFormNAVPMT25());
        pmt25.getFormNAVPMT25().getAttachment1s().add(attachment);

        JAXBContext jaxbContext = JAXBContext.newInstance(DocNAVPMT25.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(pmt25, sw);
        String xmlString = sw.toString();
        logger.info("xmlString:{}",xmlString);

        FileOutputStream outputStream = new FileOutputStream("output/" + formName + ".xml");
        byte[] strToBytes = xmlString.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();

    }

    private void validateXml(String formName) throws SaxonApiException, JAXBException, IOException {
        Processor processor = new Processor(false);
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable stylesheet = compiler.compile(new StreamSource(Objects.requireNonNull(classLoader.getResourceAsStream("full_check.xsl"))));
        Serializer out = processor.newSerializer(new File("output/errorList.xml"));
        out.setOutputProperty(Serializer.Property.METHOD, "xml");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        Xslt30Transformer transformer30 = stylesheet.load30();
        Map<QName, XdmValue> parameters = new HashMap<>();
        parameters.put(new QName("form-name"), new XdmAtomicValue(formName));
        transformer30.setStylesheetParameters(parameters);
        transformer30.transform(new StreamSource(new File("output/" + formName+".xml")), out);

        logger.info(formName + " xml bizonylat validáiójának eredménye: \n" + Files.readString(Paths.get("output/errorList.xml")));

        Hibalista dto;
        InputStream inputStream = new ByteArrayInputStream(Files.readAllBytes(Paths.get("output/errorList.xml")));
        JAXBContext jc = JAXBContext.newInstance(Hibalista.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        dto = (Hibalista) unmarshaller.unmarshal(inputStream);

        if (dto.getBizonylatEllenorzes().getBizonylatreszs().getFirst().getHibak().getHibas().isEmpty()) {
            logger.info(formName + " xml NEM tartalmaz hibát.");
        } else {
            logger.error(formName + " xml tartalmaz hibát.");
        }
    }

    public boolean isValid(String formName) throws IOException, SAXException {
        Validator validator = initValidator(formName);
        try {
            validator.validate(new StreamSource(new File("output/" + formName + ".xml")));
            logger.info("Sikeres XSD validáció");
            return true;
        } catch (SAXException e) {
            logger.error("Sikertelen XSD validáció: {}", e.toString());
            return false;
        }
    }

    private Validator initValidator(String formName) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(Objects.requireNonNull(classLoader.getResourceAsStream(formName + ".xsd")));
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }

    private void createBizonylat(String formName, String formVersion) throws Exception {
        BizonylatApiClient bizonylatApiClient = new BizonylatApiClient();
        OAuth apiAuth = (OAuth) bizonylatApiClient.getAuthentication("bearerAuth");
        apiAuth.setAccessToken(token);
        bizonylatServiceApi.setApiClient(bizonylatApiClient);

        String messageId = UUID.randomUUID().toString();
        CreateBizonylatResponseType response = bizonylatServiceApi.createBizonylat(createBizonylatRequestBodyType(messageId, formName, formVersion), messageId, null);
        logger.info("createBizonylatResponseType:{}", response);
    }

    private CreateBizonylatRequestBodyType createBizonylatRequestBodyType(String messageId, String formName, String formVersion) throws IOException {
        byte[] xml = Files.readAllBytes(Paths.get("output/" + formName + "_" + formVersion + ".xml"));

        CreateBizonylatRequestBodyType bizonylatRequestBodyType = new CreateBizonylatRequestBodyType();
        CreateBizonylatRequestType requestData = new CreateBizonylatRequestType();
        requestData.setBizonylatTipus(formName);
        requestData.setBizonylatVerzio(formVersion);
        requestData.setBizonylatXml(Base64.getEncoder().encodeToString(xml));
        requestData.setSignature(generateSignature(messageId, DigestUtils.sha256Hex(xml)));
        bizonylatRequestBodyType.setRequestData(requestData);

        logger.info("createBizonylatRequestBodyType:{}", bizonylatRequestBodyType);
        return bizonylatRequestBodyType;
    }

    private String generateSignature(String messageId, String baseText) {
        String utcTime = LocalDateTime.now(Clock.systemUTC()).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String toSign = messageId + utcTime + baseText + signatureFirstPart + encryptor.decrypt(signatureSecondPart);
        logger.info("generateSignature:{}", toSign);

        byte[] hash = DigestUtils.sha256(toSign.getBytes());
        return Base64.getEncoder().encodeToString(hash).toUpperCase();
    }

}
