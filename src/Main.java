public class Main {
    public static void main(String[] args) {
        // Me inventé los numeros REVISAR
        int numOperarios = 5;
        int numProductos = 20;//
        int capacidadBuzon = 5;
        int maxFallos = numProductos / 10;

        BuzonRevision buzonRevision = new BuzonRevision(capacidadBuzon);
        BuzonReproceso buzonReproceso = new BuzonReproceso();
        Deposito deposito = new Deposito();

        Thread[] productores=new Thread[numOperarios];
        EquipoCalidad equipoCalidad = new EquipoCalidad(buzonRevision, buzonReproceso, deposito, maxFallos);
        equipoCalidad.start();

        for (int i = 0; i < numOperarios; i++) {
            productores[i]=new Productor(buzonRevision, buzonReproceso, numProductos, i);
            productores[i].start();
        }
        // Esperar a que todos los productores terminen antes de enviar el producto FIN
        for (Thread productor : productores) {
            try {
                productor.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Después de que todos los productores terminaron, enviar el producto FIN/Veneno
        buzonReproceso.agregarProducto(new Producto(TipoProducto.FIN));

        //FALTA TRATAR CON EQUIPOS DE CALIDAD
    }

}
