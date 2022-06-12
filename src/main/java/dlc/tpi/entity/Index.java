package dlc.tpi.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Index {
    private int id;
    private long inicio;
    private long fin;
    private int minutos;
    private int segundos;
    private long miliSegundos;
    private long cantidadDocumentos;
    private long cantidadPalabrasNuevas;
    private long cantidadPalabrasActualizadas;
    private String fecha;

    public Index(int id, int minutos, int segundos, long miliSegundos, long cantidadDocumentos,
            long cantidadPalabrasNuevas,
            long cantidadPalabrasActualizadas, String fecha) {
        this.id = id;
        this.minutos = minutos;
        this.segundos = segundos;
        this.miliSegundos = miliSegundos;
        this.cantidadDocumentos = cantidadDocumentos;
        this.cantidadPalabrasNuevas = cantidadPalabrasNuevas;
        this.cantidadPalabrasActualizadas = cantidadPalabrasActualizadas;
        this.fecha = fecha;
    }

    public Index() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.fecha = LocalDateTime.now().format(formatter);
        this.init();
    }

    private void init() {
        this.inicio = System.currentTimeMillis();
    }

    public void finish() {
        this.fin = System.currentTimeMillis();
        this.miliSegundos = this.fin - this.inicio;
        this.segundos = (int) (this.miliSegundos / 1000);
        this.minutos = this.segundos / 60;
    }

    public void setData(Long[] data) {
        this.cantidadDocumentos = data[0];
        this.cantidadPalabrasNuevas = data[1];
        this.cantidadPalabrasActualizadas = data[2];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public long getMiliSegundos() {
        return miliSegundos;
    }

    public void setMiliSegundos(long miliSegundos) {
        this.miliSegundos = miliSegundos;
    }

    public long getCantidadDocumentos() {
        return cantidadDocumentos;
    }

    public void setCantidadDocumentos(long cantidadDocumentos) {
        this.cantidadDocumentos = cantidadDocumentos;
    }

    public long getCantidadPalabrasNuevas() {
        return cantidadPalabrasNuevas;
    }

    public void setCantidadPalabrasNuevas(long cantidadPalabrasNuevas) {
        this.cantidadPalabrasNuevas = cantidadPalabrasNuevas;
    }

    public long getCantidadPalabrasActualizadas() {
        return cantidadPalabrasActualizadas;
    }

    public void setCantidadPalabrasActualizadas(long cantidadPalabrasActualizadas) {
        this.cantidadPalabrasActualizadas = cantidadPalabrasActualizadas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
