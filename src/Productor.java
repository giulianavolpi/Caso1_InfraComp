
class Productor extends Thread {
    private BuzonRevision buzonRevision;
    private BuzonReproceso buzonReproceso;
    private int productosAProducir;

    public Productor(BuzonRevision buzonRevision, BuzonReproceso buzonReproceso, int productosAProducir, int id) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.productosAProducir = productosAProducir;

    }

    // @Override
    // public void run() {
    //     try {
    //         for (int i = 0; i < productosAProducir; i++) {
    //             Producto reproceso = buzonReproceso.retirarProducto();
    //             if (reproceso != null) {
    //                 buzonRevision.agregarProducto(reproceso);
    //             } else {
    //                 buzonRevision.agregarProducto(new Producto(TipoProducto.NORMAL));
    //             }
    //         }
    //         buzonRevision.agregarProducto(new Producto(TipoProducto.FIN));
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    @Override
    public void run() {
        try {
            int producidos=0;
            while(producidos<productosAProducir) {
    
                Producto productoReprocesado = null;

                // Verificar si hay productos para reprocesar antes de intentar retirarlos(prioridad a los reprocesos)
                
                synchronized(buzonReproceso){
                    if(buzonReproceso.hayProductos()){
                        productoReprocesado=buzonReproceso.retirarProducto();
                        System.out.println("[Productor] Retirado producto reprocesado.");
                    }
                }

                if (productoReprocesado != null) {
                    if(productoReprocesado.getTipo()==TipoProducto.FIN){
                        System.out.println("[Productor] Recibido FIN. Terminando producción.");
                        buzonReproceso.agregarProducto(productoReprocesado);//Reeviar para los otros producoters
                        break;
                    }
                    buzonRevision.agregarProducto(productoReprocesado); //reproceso
                    System.out.println("[Productor] Producto reprocesado enviado a revisión.");
                } else {
                    synchronized(buzonRevision){// Esperea Activa: sigue verificando sin pausar el hilo
                    while(!buzonRevision.hayEspacio()){
                        buzonRevision.wait();
                    }
                    buzonRevision.agregarProducto(new Producto(TipoProducto.NORMAL));//productos nuevos
                    System.out.println("[Productor] Producto nuevo generado y enviado a revisión.");
                    producidos++;
                    producidos++;
                }
                    
                }
            }
            
            buzonRevision.agregarProducto(new Producto(TipoProducto.FIN));
            System.out.println("[Productor] Producto FIN enviado.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}