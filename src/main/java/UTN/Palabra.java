package UTN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Palabra {
    private ArrayList<String> alfabeto;
    private ArrayList<String> palabra;
    private String copia;
    private String ganador;

    public Palabra(String palabra) {
        this.alfabeto = new ArrayList<>((Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")));
        this.palabra = new ArrayList<>(Arrays.asList(palabra.split("")));
        copia = palabra;
    }

    public boolean isEmpty() {
        return palabra.isEmpty();
    }

    public String getPalabra() {
        return copia;
    }

    public String getGanador() {
        return ganador;
    }

    public synchronized void jugar(Jugador jugador) {
        while (palabra.isEmpty()) {
            return;
        }
        if (!alfabeto.isEmpty()) {
            Random r = new Random();
            int i = r.nextInt(alfabeto.size());
            System.out.println(jugador.getNombre() + ", letra: " + alfabeto.get(i));
            for (int x = 0; x <= palabra.size() - 1; ) {
                if (palabra.get(x).equals(alfabeto.get(i))) {
                    palabra.remove(x);
                }
                x++;
            }
            if (palabra.isEmpty()) {
                ganador = jugador.getNombre();
                System.out.println("Ganó " + ganador + ", palabra: " + copia);
            }
            alfabeto.remove(i);
        }
        notifyAll();
    }
}