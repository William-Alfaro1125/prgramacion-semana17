package semana17.ejercicios;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import semana17.web.RepositorioSocios;
import semana17.web.ServidorCooperativa;
import java.util.concurrent.Executors;

public class ServidorTotal {
    public static void main(String[] args) throws IOException {
        RepositorioSocios repo = new RepositorioSocios();
        HttpServer servidor = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        servidor.createContext("/total", ex -> {
            try {
                String json = "{\"socios\": " + repo.cantidadSocios()
                        + ", \"total\": " + repo.saldoTotal() + "}";
                ServidorCooperativa.responder(ex, 200, json);
            } finally {
                ex.close();
            }
        });

        servidor.setExecutor(Executors.newFixedThreadPool(4));
        servidor.start();
        System.out.println("Servidor en http://localhost:8080/total");
    }
}
