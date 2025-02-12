import java.util.LinkedList;

public class BuzonReproceso {

    private LinkedList<Producto> productos = new LinkedList<>();

    public synchronized void agregarProducto(Producto producto) {
        productos.add(producto);
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
        return productos.removeFirst();
        //return productos.isEmpty() ? null : productos.removeFirst();
    }
    public synchronized boolean hayProductos(){
        return !productos.isEmpty();
    }

}
