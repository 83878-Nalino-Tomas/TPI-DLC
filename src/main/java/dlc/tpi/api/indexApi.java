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
import java.util.HashSet;

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
    @Produces(MediaType.TEXT_PLAIN)
    public Response indexar() {
        Long init = System.currentTimeMillis();
        String pathDocs = System.getProperty("user.home");

        HashMap<String, Post> docPost = new HashMap<>();
        HashSet<String> doclList = initConfig.getDocList();


        try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(Paths.get(pathDocs + "\\DocumentosTP1"))) {
            for (java.nio.file.Path file : stream) {
                Document newDoc = new Document(file.getFileName().toString());
                if(doclList.contains(newDoc.getDocName())) continue;
                doclList.add(newDoc.getDocName());
                DBDocument.insertDoc(newDoc, db);
                IndexService.indexOneByOne(newDoc, initConfig.getVocabulary(), docPost, db);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        DBVocabulary.insertVocabulary(initConfig.getVocabulary(), db);
        long res = (System.currentTimeMillis() - init) / 1000 / 60;
        String response = res + " Minutos";
        return Response.ok(response).build();
    }
}
