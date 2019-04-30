package UTN;

import static java.lang.Thread.sleep;

public class Jugador implements Runnable {
    private int id;
    private String nombre;
    private Palabra palabra;

    public Jugador(int id, String nombre, Palabra palabra) {
        this.id = id;
        this.nombre = nombre;
        this.palabra = palabra;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        while (!palabra.isEmpty()) {
            palabra.jugar(this);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}