
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
        System.out.println("[BuzonRevision] Producto ID " + producto.getId() + " agregado. Tamaño actual: " + productos.size());
        notifyAll();
    }

    public synchronized Producto retirarProducto() throws InterruptedException {
        while (productos.isEmpty()) {
            wait();
        }
        Producto producto = productos.removeFirst();
        System.out.println("[BuzonRevision] Producto ID " + producto.getId() + " retirado. Tamaño actual: " + productos.size());
        notifyAll();
        return producto;
    }
    public synchronized boolean hayEspacio() {
        return productos.size() < capacidad; // Retorna true si hay espacio disponible en el buzón
    }
    //diferentes ////////////////////////////////////////////////////////////////////////////////////////
    public synchronized boolean estaVacio() {
        return productos.isEmpty(); // Retorna true si el buzón está vacío
    }
}
