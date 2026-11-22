package semana17.web;

public class Socio {
    private final int id;
    private final String nombre;
    private final int saldo;

    public Socio(int id, String nombre, int saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSaldo() {
        return saldo;
    }

    public String toJson() {
        String nombreSeguro = nombre.replace("\\", "\\\\").replace("\"", "\\\"");
        return "{\"id\": " + id
                + ", \"nombre\": \"" + nombreSeguro
                + "\", \"saldo\": " + saldo + "}";
    }
}
