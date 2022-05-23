package dlc.tpi.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class DBManagerProducer {

    @Produces
    @RequestScoped
    public DBManager create() {
        DBManager db = null;
        try {
            db = new DBManager();
            db.setConnectionMode(DBManager.DBConnectionMode.SINGLE_CONNECTION_MODE);
            db.setDriverName(DBManager.MARIADB_DRIVER_NAME);
            db.setUrl("jdbc:mariadb://localhost:3306/dlc?rewriteBatchedStatements=true");
            db.setUserName("root");
            db.setPassword("dlc2022");

            db.connect();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return db;
    }

    public void destroy(@Disposes DBManager db) {
        if (db != null)
            db.close();
    }
}
