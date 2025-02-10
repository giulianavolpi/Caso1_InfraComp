
class Productor extends Thread {
    private BuzonRevision buzonRevision;
    private BuzonReproceso buzonReproceso;
    private int productosAProducir;

    public Productor(BuzonRevision buzonRevision, BuzonReproceso buzonReproceso, int productosAProducir, int id) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.productosAProducir = productosAProducir;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < productosAProducir; i++) {
                Producto reproceso = buzonReproceso.retirarProducto();
                if (reproceso != null) {
                    buzonRevision.agregarProducto(reproceso);
                } else {
                    buzonRevision.agregarProducto(new Producto(TipoProducto.NORMAL));
                }
            }
            buzonRevision.agregarProducto(new Producto(TipoProducto.FIN));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}