import java.util.LinkedList;

public class BuzonReproceso {

    private LinkedList<Producto> productos = new LinkedList<>();

    public synchronized void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("[BuzonReproceso] Producto ID " + producto.getId() + " agregado. Tamaño actual: " + productos.size());
        notifyAll();
    }

    public synchronized Producto retirarProducto() {
        while(productos.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Producto producto=productos.removeFirst();
        System.out.println("[BuzonReproceso] Producto ID " + producto.getId() + " retirado. Tamaño actual: " + productos.size());
        notifyAll();
        return producto;
        //return productos.isEmpty() ? null : productos.removeFirst();
    }
    public synchronized boolean hayProductos(){
        return !productos.isEmpty();
    }

}
