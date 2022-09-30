package im.haugsdal.webservice.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.rs.security.jose.jaxrs.JweClientResponseFilter;
import org.apache.cxf.rs.security.jose.jaxrs.JweJsonWriterInterceptor;
import org.apache.cxf.rs.security.jose.jaxrs.JweWriterInterceptor;
import org.apache.cxf.rs.security.jose.jaxrs.JwsClientResponseFilter;
import org.apache.cxf.rs.security.jose.jaxrs.JwsWriterInterceptor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import types.AppRec;
import types.M1;

public class JweJwsTest {

    @org.testng.annotations.Test
    public void M1Test() throws ParserConfigurationException, IOException, SAXException {

        new RekvirentWebServiceRestHttpPortImpl();

        M1 m1 = new M1();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        var doc = documentBuilder.parse("src/test/resources/data/_1_2009-02-20-eksempel1-basis.xml");

        m1.setDokument(toString(doc).getBytes(StandardCharsets.UTF_8));
        JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
        bean.setAddress("http://localhost:8890/rest/m1");

        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        bean.getFeatures().add(loggingFeature);

        //JweWriterInterceptor jweWriterInterceptor = new JweWriterInterceptor();
        JweJsonWriterInterceptor jweWriterInterceptor = new JweJsonWriterInterceptor();
        jweWriterInterceptor.setUseJweOutputStream(true);

        JwsWriterInterceptor jwsWriterInterceptor = new JwsWriterInterceptor();
        jwsWriterInterceptor.setUseJwsOutputStream(true);

        JweClientResponseFilter jweClientResponseFilter = new JweClientResponseFilter();
        JwsClientResponseFilter jwsClientResponseFilter = new JwsClientResponseFilter();

        List<Object> providers = new LinkedList<>();
        providers.add(jwsWriterInterceptor);
        providers.add(jweWriterInterceptor);
        providers.add(jweClientResponseFilter);
        providers.add(jwsClientResponseFilter);

        bean.setProviders(providers);

        bean.getProperties(true).put("rs.security.encryption.out.properties",
            "jwe/client/client.properties");
        bean.getProperties(true).put("rs.security.signature.out.properties",
            "jwe/client/client-sign.properties");
        bean.getProperties(true).put("rs.security.encryption.in.properties",
            "jwe/client/client-in.properties");
        bean.getProperties(true).put("rs.security.signature.in.properties",
            "jwe/client/client-in-sign.properties");
        bean.getProperties(true).put("jose.debug", true);

        WebClient client = bean.createWebClient()
            .type("application/xml; charset=UTF-8")
            .encoding("UTF-8");

        client.encoding("UTF-8");
        var apprec = client.post(m1);
        var apprecDocument = apprec.readEntity(AppRec.class);
        var dok = (String) apprecDocument.getDokument();
        System.out.println(dok);
    }



    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }
}