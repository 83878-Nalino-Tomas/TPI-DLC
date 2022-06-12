package dlc.tpi.endPoint;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import dlc.tpi.util.DBManager;
import dlc.tpi.util.VendorConfiguration;
import dlc.tpi.entity.Document;
import dlc.tpi.entity.VocabularyEntry;
import dlc.tpi.service.SearchService;

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
            String res = "{" + "\"Resultado\": \"No Entry\"" + "}";
            r = Response.ok(res).build();
        }
        return r;
    }

    @GET
    @Path("{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSearch(@PathParam("query") String query) {
        Response r;
        List<Document> docList = initConfig.getDocList();
        Hashtable<String, VocabularyEntry> vocabulary = initConfig.getVocabulary();
        List<Document> result = SearchService.search(query, vocabulary, db, docList);
        r = Response.ok(result).build();
        return r;
    }

    @GET
    @Path("download/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDownload(@PathParam("id") int id) {
        Response r;
        List<Document> docList = initConfig.getDocList();

        File arch = SearchService.getFile(docList, id);

        if (arch == null) {
            r = Response.status(Response.Status.NOT_FOUND).build();
        } else {
            r = Response.ok(arch).build();
        }
        return r;
    }

}
