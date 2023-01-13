package im.haugsdal.webservice.rest;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.rs.security.jose.jaxrs.JweClientResponseFilter;
import org.apache.cxf.rs.security.jose.jaxrs.JweWriterInterceptor;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import types.M1;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class JweTest {

    @org.testng.annotations.Test
    public void M1TestUtenSignering() throws ParserConfigurationException, IOException, SAXException {

        //new RekvirentWebServiceRestHttpPortImpl();

        M1 m1 = new M1();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newDefaultInstance();
        dbf.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        var doc = documentBuilder.parse("src/test/resources/data/_1_2009-02-20-eksempel1-basis.xml");

        //m1.setDokument("<dok>Hello world!</dok>".getBytes(StandardCharsets.UTF_8));
        m1.setDokument(toString(doc).getBytes(StandardCharsets.UTF_8));
        JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
        bean.setAddress("http://localhost:8890/rest/m1");

        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        bean.getFeatures().add(loggingFeature);

        JweWriterInterceptor jweWriterInterceptor = new JweWriterInterceptor();
        jweWriterInterceptor.setUseJweOutputStream(true);

        JweClientResponseFilter jweClientResponseFilter = new JweClientResponseFilter();

        List<Object> providers = new LinkedList<>();
        providers.add(jweWriterInterceptor);
        providers.add(jweClientResponseFilter);

        bean.setProviders(providers);

        bean.getProperties(true).put("rs.security.encryption.out.properties",
                "jwe/client/client.properties");
        /*bean.getProperties(true).put("rs.security.signature.out.properties",
            "jwe/client/client-sign.properties");*/
        bean.getProperties(true).put("rs.security.encryption.in.properties",
                "jwe/client/client-in.properties");
        /*bean.getProperties(true).put("rs.security.signature.in.properties",
            "jwe/client/client-in-sign.properties");*/

        bean.getProperties(true).put("jose.debug", true);

        WebClient client = bean.createWebClient()
                .type("application/xml; charset=UTF-8")
                .encoding("UTF-8");

        client.encoding("UTF-8");

        var response = client.post(m1);
        var entity = response.readEntity(String.class);
        System.out.println(entity);
        //Assert.assertTrue(entity.contains("INVALID_COMPACT_JWS")); //Server returnerer at JWS ikke stemmer

        //var jsoup = Jsoup.parse(entity);
        //var body = jsoup.body();
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
