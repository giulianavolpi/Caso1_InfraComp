
import java.util.LinkedList;

class BuzonRevision {
    private final int capacidad;
    private LinkedList<Producto> productos = new LinkedList<>();

    public BuzonRevision(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void agregarProducto(Producto producto) throws InterruptedException {
        while (productos.size() == capacidad) {
            wait();
        }
        productos.add(producto);
        notifyAll();
    }

    public synchronized Producto retirarProducto() throws InterruptedException {
        while (productos.isEmpty()) {
            wait();
        }
        Producto producto = productos.removeFirst();
        notifyAll();
        return producto;
    }
}
