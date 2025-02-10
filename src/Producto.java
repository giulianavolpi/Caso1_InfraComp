
enum TipoProducto {
    NORMAL, FIN;
}

public class Producto {

    private TipoProducto tipo;

    public Producto(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

}
