package dlc.tpi.dataAccess;

import dlc.tpi.entity.Index;
import dlc.tpi.util.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBIndex {
    public static void saveIndex(Index toSave, DBManager db) {
        try {
            String SQL_INSERT = "INSERT INTO dlc.indexLog (minutos, segundos, milisegundos, cantDoc, cantPalabrasNuevas, cantPalabrasActualizadas, fecha) VALUES (?,?,?,?,?,?,?)";
            db.prepareUpdate(SQL_INSERT);
            db.setInt(1, toSave.getMinutos());
            db.setInt(2, toSave.getSegundos());
            db.setLong(3, toSave.getMiliSegundos());
            db.setLong(4, toSave.getCantidadDocumentos());
            db.setLong(5, toSave.getCantidadPalabrasNuevas());
            db.setLong(6, toSave.getCantidadPalabrasActualizadas());
            db.setString(7, toSave.getFecha());
            db.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Index> getAllIndex(DBManager db) {
        List<Index> result = new ArrayList<Index>();
        try {
            String SQL_QUERY = "SELECT * FROM indexLog";
            db.prepareQuery(SQL_QUERY);
            ResultSet rs = db.executeQuery();
            while (rs.next()) {
                result.add(buildIndex(rs));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static Index buildIndex(ResultSet rs) throws SQLException {
        int id = rs.getInt("indexId");
        int minutos = rs.getInt("minutos");
        int segundos = rs.getInt("segundos");
        long milisegundos = rs.getLong("milisegundos");
        long cantidadDocumentos = rs.getLong("cantDoc");
        long cantidadPalabrasNuevas = rs.getLong("cantPalabrasNuevas");
        long cantidadPalabrasActualizadas = rs.getLong("cantPalabrasActualizadas");
        String fecha = rs.getString("fecha");

        return new Index(id, minutos, segundos, milisegundos, cantidadDocumentos, cantidadPalabrasNuevas,
                cantidadPalabrasActualizadas, fecha);
    }

}
