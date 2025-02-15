import java.util.Random;

public class EquipoCalidad extends Thread {
    private BuzonRevision buzonRevision;
    private BuzonReproceso buzonReproceso;
    private Deposito deposito;
    private int maxFallos;
    private int fallos = 0;
    private Random random = new Random();
    private static int productosAprobados = 0;
    
    public EquipoCalidad(BuzonRevision buzonRevision, BuzonReproceso buzonReproceso, Deposito deposito, int maxFallos) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.maxFallos = maxFallos;
    }

    @Override
    public void run() {
        System.out.println("[EquipoCalidad] Iniciando revisión...");

        try {
            while (true) {
                //Producto producto = buzonRevision.retirarProducto();bloquea si esta vacio
                Producto producto=null;
                //Espera semi-activa
                while(producto==null){
                    synchronized(buzonRevision){
                        if(!buzonRevision.estaVacio()){
                            System.out.println("[EquipoCalidad] Intentando retirar un producto...");
                            producto=buzonRevision.retirarProducto();
                            System.out.println("[EquipoCalidad] Producto retirado y en revisión.");
                        }
                    }
                    if(producto==null){
                        Thread.yield();
                    }
                }
                
                if (producto.getTipo() == TipoProducto.FIN) {
                    buzonReproceso.agregarProducto(producto);
                    break;
                }

                if (fallos < maxFallos && random.nextDouble() < 0.1) {
                    fallos++;
                    buzonReproceso.agregarProducto(producto);
                    System.out.println("[EquipoCalidad] Producto rechazado. Enviado a reproceso.");
                } else {
                    deposito.agregarProducto(producto);
                    productosAprobados++;
                    if (productosAprobados >= maxFallos * 10) {
                        buzonReproceso.agregarProducto(new Producto(TipoProducto.FIN));
                        System.out.println("[EquipoCalidad] Se alcanzó la meta. Enviando FIN.");
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
