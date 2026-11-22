package semana17.hilos;

public class SinJoin {
    private static long total = 0;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            total = 500500;
        });

        t.start();
        // No se espera al hilo: puede imprimirse el valor anterior.
        System.out.println("Total sin esperar: " + total);
    }
}
