package im.haugsdal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import types.AppRec;
import types.M1;


@WebService(name = "M1Service")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface M1Service {

    @WebMethod(operationName = "RekvirentWebService_m1", action = "M1")
    @WebResult(name = "AppRecElement", targetNamespace = "http://rekvirent.webservices.reseptformidleren.ergo.no/", partName = "result")
    AppRec m1(@WebParam(name = "M1Element") M1 m1);

}
