import java.util.LinkedList;

public class Deposito {

    private LinkedList<Producto> productos = new LinkedList<>();

    public synchronized void agregarProducto(Producto producto) {
        productos.add(producto);
    }

}
