package dlc.tpi.endPoint;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dlc.tpi.util.DBManager;
import dlc.tpi.util.VendorConfiguration;
import dlc.tpi.entity.VocabularyEntry;

@Path("search")
public class SearchEndPoint {
    @EJB
    VendorConfiguration initConfig;
    @Inject
    private DBManager db;

    @GET
    @Path("word/{w}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWord(@PathParam("w") String word) {
        Response r;
        VocabularyEntry entry = initConfig.getVocabulary().get(word);
        if (entry != null) {
            r = Response.ok(entry).build();
        } else {
            String res = "\"Resultado\": \"No Entry\"";
            r = Response.ok(res).build();
        }
        return r;
    }

}
