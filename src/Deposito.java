import java.util.LinkedList;

public class Deposito {

    private LinkedList<Producto> productos = new LinkedList<>();

    public synchronized void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("[Deposito] Producto aprobado y almacenado. Tamaño actual: " + productos.size());
    }   

}
