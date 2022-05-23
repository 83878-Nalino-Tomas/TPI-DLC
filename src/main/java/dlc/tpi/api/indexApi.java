package dlc.tpi.api;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import dlc.tpi.DataAccess.DBDocument;
import dlc.tpi.DataAccess.DBVocabulary;
import dlc.tpi.Entity.*;
import dlc.tpi.Utils.DBManager;
import dlc.tpi.Utils.VendorConfiguration;
import dlc.tpi.service.IndexService;

@Path("index")
public class indexApi {

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
            r = Response.ok("NoEntry").build();
        }

        return r;
    }

    @GET
    @Path("indexar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response indexar() {
        HashMap<String, Post> docPost = new HashMap<>();
        String pathDocs = System.getProperty("user.home");
        Long init = System.currentTimeMillis();
        IndexService is = new IndexService();
        try (DirectoryStream<java.nio.file.Path> stream = Files
                .newDirectoryStream(Paths.get(pathDocs + "\\DocumentosTP1"))) {
            for (java.nio.file.Path file : stream) {
                Document newDoc = new Document(file.getFileName().toString());
                DBDocument.insertDoc(newDoc, db);
                is.indexOneByOne(newDoc, initConfig.getVocabulary(), docPost, db);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBVocabulary.insertVocabulary(initConfig.getVocabulary(), db);
        long res = (System.currentTimeMillis() - init) / 1000 / 60;
        String response = res + " Minutos";

        Response r = Response.ok(response).build();
        return r;
    }
}
