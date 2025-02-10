import java.util.LinkedList;

public class BuzonReproceso {

    private LinkedList<Producto> productos = new LinkedList<>();

    public synchronized void agregarProducto(Producto producto) {
        productos.add(producto);
        notifyAll();
    }

    public synchronized Producto retirarProducto() {
        return productos.isEmpty() ? null : productos.removeFirst();
    }

}
