package im.haugsdal.webservice.rest;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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


@Path("/rest")
@WebService
public interface RekvirentWebServiceRestHttpPort {

    @Path("/m1")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    AppRec m1(M1 m1);

    @Path("/m5")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    AppRec m5(M5 m5);

    @Path("/m95")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M9_6 m9_5(M9_5 m9_5);

    @Path("/m95kj")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M96Kj M95kj(M95Kj m95Kj) throws AppRecFault;

    @Path("/m41")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M4_2 m4_1(M4_1 m4_1);

    @Path("/m241")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M24_2 m24_1(M24_1 m24_1);

    @Path("/m251")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    AppRec m25_1(M25_1 m25_1);

    @Path("/m271")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M27_2 m27_1(M27_1 m271) throws AppRecFault;

    @Path("/m911")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M9_12 m9_11(M9_11 m911) throws AppRecFault;

    @Path("/m97")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M9_8 m9_7(M9_7 m9_7) throws AppRecFault;

    @Path("/verify")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    AppRec verify(MV mv);

    @Path("/m921")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    M9_22 m9_21(M9_21 m921) throws AppRecFault;
}
