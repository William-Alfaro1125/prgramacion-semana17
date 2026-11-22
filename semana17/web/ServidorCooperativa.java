package semana17.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class ServidorCooperativa {
    private static final RepositorioSocios REPO = new RepositorioSocios();

    public static void responder(HttpExchange ex, int codigo, String json)
            throws IOException {
        byte[] datos = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set(
                "Content-Type", "application/json; charset=utf-8"
        );
        ex.sendResponseHeaders(codigo, datos.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(datos);
        }
    }

    private static String error(String mensaje) {
        String seguro = mensaje.replace("\\", "\\\\").replace("\"", "\\\"");
        return "{\"error\": \"" + seguro + "\"}";
    }

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        servidor.createContext("/socios", ex -> {
            try {
                if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
                    responder(ex, 405, error("Metodo no permitido"));
                    return;
                }
                responder(ex, 200, REPO.listarJson());
            } finally {
                ex.close();
            }
        });

        servidor.createContext("/socio", ex -> {
            try {
                if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
                    responder(ex, 405, error("Metodo no permitido"));
                    return;
                }

                String consulta = ex.getRequestURI().getRawQuery();
                String id = obtenerParametroId(consulta);

                try {
                    responder(ex, 200, REPO.buscar(id).toJson());
                } catch (SocioNoEncontradoException e) {
                    responder(ex, 404, error(e.getMessage()));
                } catch (DatoInvalidoException e) {
                    responder(ex, 400, error(e.getMessage()));
                }
            } finally {
                ex.close();
            }
        });

        servidor.createContext("/total", ex -> {
            try {
                String json = "{\"socios\": " + REPO.cantidadSocios()
                        + ", \"total\": " + REPO.saldoTotal() + "}";
                responder(ex, 200, json);
            } finally {
                ex.close();
            }
        });

        servidor.createContext("/hilo", ex -> {
            try {
                String hilo = Thread.currentThread().getName();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    responder(ex, 503, error("Peticion interrumpida"));
                    return;
                }
                responder(ex, 200, "{\"atendido_por\": \"" + hilo + "\"}");
            } finally {
                ex.close();
            }
        });

        servidor.setExecutor(Executors.newFixedThreadPool(4));
        servidor.start();
        System.out.println("Servidor en http://localhost:8080");
        System.out.println("Rutas: /socios, /socio?id=2, /total, /hilo");
    }

    private static String obtenerParametroId(String consulta) {
        if (consulta == null || consulta.isBlank()) {
            throw new DatoInvalidoException("Falta el parametro id");
        }

        for (String parametro : consulta.split("&")) {
            String[] partes = parametro.split("=", 2);
            if (partes.length == 2 && "id".equals(partes[0])) {
                return partes[1];
            }
        }

        throw new DatoInvalidoException("Falta el parametro id");
    }
}
