public class Main {
    public static void main(String[] args) {
        // Me inventé los numeros REVISAR
        int numOperarios = 5;
        int numProductos = 10;
        int capacidadBuzon = 5;
        int maxFallos = numProductos / 10;

        BuzonRevision buzonRevision = new BuzonRevision(capacidadBuzon);
        BuzonReproceso buzonReproceso = new BuzonReproceso();
        Deposito deposito = new Deposito();

        EquipoCalidad equipoCalidad = new EquipoCalidad(buzonRevision, buzonReproceso, deposito, maxFallos);
        equipoCalidad.start();

        for (int i = 0; i < numOperarios; i++) {
            new Productor(buzonRevision, buzonReproceso, numProductos, i).start();
        }
    }

}
