import java.util.LinkedList;

public class Deposito {

    private LinkedList<Producto> productos = new LinkedList<>();

    public synchronized void agregarProducto(Producto producto) {
        productos.add(producto);
        System.out.println("Producto aprobado y almacenado en el depósito.");
    }   

}
