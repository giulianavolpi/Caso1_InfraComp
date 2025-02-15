
enum TipoProducto {
    NORMAL, FIN;
}

public class Producto {
    private static int idCounter = 1; 
    private int id;
    private TipoProducto tipo;

    public Producto(TipoProducto tipo) {
        this.id = idCounter++;
        this.tipo = tipo;
        System.out.println("[Producto] Creado un producto ID: " + id + " de tipo: " + tipo);
    }

    public int getId() {
        return id;
    }
    public TipoProducto getTipo() {
        return tipo;
    }
    @Override
    public String toString() {
        return "Producto[ID=" + id + ", Tipo=" + tipo + "]";
    }

}
