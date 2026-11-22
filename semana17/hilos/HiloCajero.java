package semana17.hilos;

public class HiloCajero extends Thread {
    private final String caja;

    public HiloCajero(String caja) {
        this.caja = caja;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(caja + " atiende cliente " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(caja + " interrumpida");
                return;
            }
        }
    }

    public static void main(String[] args) {
        HiloCajero a = new HiloCajero("Caja A");
        HiloCajero b = new HiloCajero("Caja B");

        a.start();
        b.start();
        System.out.println("main sigue trabajando");
    }
}
