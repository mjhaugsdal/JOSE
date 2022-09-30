package im.haugsdal.webservice;



import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


import types.AppRec;
import types.AppRecFault;
import types.M1;
import types.M24_1;
import types.M24_2;
import types.M25_1;
import types.M27_1;
import types.M27_2;
import types.M4_1;
import types.M4_2;
import types.M5;
import types.M95Kj;
import types.M96Kj;
import types.M9_11;
import types.M9_12;
import types.M9_21;
import types.M9_22;
import types.M9_5;
import types.M9_6;
import types.M9_7;
import types.M9_8;
import types.MV;


@WebService(name = "RekvirentSoapWebPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface RekvirentWebServiceSoapHttpPort {

    @WebMethod(operationName = "RekvirentWebService_m1", action = "M1")
    @WebResult(name = "AppRecElement", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    AppRec m1(@WebParam(name = "M1Element") M1 m1);

    @WebMethod(operationName = "RekvirentWebService_m5", action = "M5")
    @WebResult(name = "AppRecElement", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    AppRec m5(@WebParam(name = "M5Element") M5 m5);

    @WebMethod(operationName = "RekvirentWebService_m9_5", action = "M9_5")
    @WebResult(name = "M9_6Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M9_6 m9_5(@WebParam(name = "M9_5Element") M9_5 m9_5);

    @WebMethod(operationName = "RekvirentWebService_M95kj", action = "M9_5kj")
    @WebResult(name = "M96kjElement", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M96Kj M95kj(@WebParam(name = "M95kjElement") M95Kj m95Kj) throws AppRecFault;

    @WebMethod(operationName = "RekvirentWebService_m4_1", action = "M4_1")
    @WebResult(name = "M4_2Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M4_2 m4_1(@WebParam(name = "M4_1Element") M4_1 m4_1);

    @WebMethod(operationName = "RekvirentWebService_m24_1", action = "M24_1")
    @WebResult(name = "M24_2Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M24_2 m24_1(@WebParam(name = "M24_1Element") M24_1 m24_1);

    @WebMethod(operationName = "RekvirentWebService_m25_1", action = "M25_1")
    @WebResult(name = "AppRecElement", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    AppRec m25_1(@WebParam(name = "M25_1Element") M25_1 m25_1);

    @WebMethod(operationName = "RekvirentWebService_m27_1", action = "M27_1")
    @WebResult(name = "M27_2Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M27_2 m27_1(@WebParam(name = "M27_1Element") M27_1 m271) throws AppRecFault;

    @WebMethod(operationName = "RekvirentWebService_m9_11", action = "M9_11")
    @WebResult(name = "M9_12Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M9_12 m9_11(@WebParam(name = "M9_11Element") M9_11 m911) throws AppRecFault;

    @WebMethod(operationName = "RekvirentWebService_m9_7", action = "M9_7")
    @WebResult(name = "M9_8Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M9_8 m9_7(@WebParam(name = "M9_7Element") M9_7 m9_7) throws AppRecFault;

    @WebMethod(operationName = "RekvirentWebService_verify", action = "Verify")
    @WebResult(name = "AppRecElement", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    AppRec verify(@WebParam(name = "MVElement") MV mv);

    @WebMethod(operationName = "RekvirentWebService_m9_21", action = "M9_21")
    @WebResult(name = "M9_22Element", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    M9_22 m9_21(@WebParam(name = "M9_21Element") M9_21 m921) throws AppRecFault;
}
