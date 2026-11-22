package semana17.hilos;

public class Tiempos {
    private static void tarea(String nombre) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        // El nombre identifica la tarea; no se requiere imprimirlo.
    }

    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();

        tarea("A");
        tarea("B");
        tarea("C");

        long secuencial = System.currentTimeMillis() - inicio;
        System.out.println("Secuencial: " + secuencial + " ms aprox.");

        inicio = System.currentTimeMillis();
        Thread[] hilos = new Thread[3];

        for (int i = 0; i < hilos.length; i++) {
            String nombre = "T" + i;
            hilos[i] = new Thread(() -> tarea(nombre));
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        long paralelo = System.currentTimeMillis() - inicio;
        System.out.println("Con hilos: " + paralelo + " ms aprox.");
    }
}
