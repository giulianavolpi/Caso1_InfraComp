import java.util.Random;

public class EquipoCalidad extends Thread {
    private BuzonRevision buzonRevision;
    private BuzonReproceso buzonReproceso;
    private Deposito deposito;
    private int maxFallos;
    private int fallos = 0;
    private Random random = new Random();

    public EquipoCalidad(BuzonRevision buzonRevision, BuzonReproceso buzonReproceso, Deposito deposito, int maxFallos) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.maxFallos = maxFallos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Producto producto = buzonRevision.retirarProducto();bloquea si esta vacio
                Producto producto=null;
                //Espera semi-activa
                while(producto==null){
                    synchronized(buzonRevision){
                        if(!buzonRevision.estaVacio()){
                            producto=buzonRevision.retirarProducto();
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
                    System.out.println("Producto rechazado y enviado a reproceso.");
                } else {
                    deposito.agregarProducto(producto);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
