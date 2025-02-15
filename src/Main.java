import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try (
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is)
        ) {
            System.out.print("Ingrese el número de operarios en producción y calidad: ");
            int numOperarios = Integer.parseInt(br.readLine());
            System.out.print("Ingrese el número de productos a generar: ");
            int numProductos = Integer.parseInt(br.readLine());
            System.out.print("Ingrese la capacidad del buzón de revisión: ");
            int capacidadBuzon = Integer.parseInt(br.readLine());

            int maxFallos = numProductos / 10;

            System.out.println("[Main] Iniciando simulación...");

            //BUZONES && DEPOSITO--------------------------------------------------------------------------------------
            BuzonRevision buzonRevision = new BuzonRevision(capacidadBuzon);
            BuzonReproceso buzonReproceso = new BuzonReproceso();
            Deposito deposito = new Deposito();

            //PRODUCTORES----------------------------------------------------------------------------------------------------
            Thread[] productores = new Thread[numOperarios];
            for (int i = 0; i < numOperarios; i++) {
                System.out.println("[Main] Creando Productor #" + i);
                productores[i] = new Productor(buzonRevision, buzonReproceso, numProductos, i);
                productores[i].start();
                System.out.println("[Main] Iniciado Productor #" + i);
            }

            //FALTA TRATAR CON EQUIPOS DE CALIDAD-------------------------------------------------------------------
            System.out.println("[Main] Creando hilos de EquipoCalidad...");
            Thread[] equiposCalidad = new Thread[numOperarios];
            for (int i = 0; i < numOperarios; i++) {
                System.out.println("[Main] Creando EquipoCalidad #" + i);
                equiposCalidad[i] = new EquipoCalidad(buzonRevision, buzonReproceso, deposito, maxFallos);
                equiposCalidad[i].start();
                System.out.println("[Main] Iniciado EquipoCalidad #" + i);
            }

            // Esperar a que todos los productores terminen antes de enviar el producto FIN/venenos
            System.out.println("[Main] Esperando a que los productores terminen...");
            for (Thread productor : productores) {
                try {
                    productor.join();
                    System.out.println("[Main] Productor terminado.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("[Main] Todos los productores han finalizado.");

            // Después de que todos los productores terminaron, enviar el producto FIN/Veneno
            System.out.println("[Main] Enviando señales FIN a los equipos de calidad...");
            for (int i = 0; i < numOperarios; i++) {
                buzonRevision.agregarProducto(new Producto(TipoProducto.FIN));
            }

             // JOIN() ACABAR TODOS
            System.out.println("[Main] Esperando a que los equipos de calidad terminen...");
            for (Thread equipo : equiposCalidad) {
                try {
                    equipo.join();
                    System.out.println("[Main] Un equipo de calidad ha finalizado.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("[Main] Todos los equipos de calidad han finalizado. Simulación terminada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
