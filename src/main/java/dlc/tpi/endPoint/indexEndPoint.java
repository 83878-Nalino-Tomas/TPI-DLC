package dlc.tpi.endPoint;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dlc.tpi.entity.*;
import dlc.tpi.service.IndexService;
import dlc.tpi.util.DBManager;
import dlc.tpi.util.VendorConfiguration;

@Path("index")
public class indexEndPoint {

    @EJB
    VendorConfiguration initConfig;
    @Inject
    private DBManager db;

    @POST
    @Path("indexar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response indexar() {
        Index index = IndexService.indexar(initConfig.getDocList(), initConfig.getVocabulary(), db);
        return Response.ok(index).build();
    }

    @GET
    @Path("log")
    @Produces(MediaType.APPLICATION_JSON)
    public Response log() {
        List<Index> indexLog = IndexService.getIndexHistory(db);
        return Response.ok(indexLog).build();
    }

    
}
