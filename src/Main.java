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
        
        
        

            BuzonRevision buzonRevision = new BuzonRevision(capacidadBuzon);
            BuzonReproceso buzonReproceso = new BuzonReproceso();
            Deposito deposito = new Deposito();

            Thread[] productores=new Thread[numOperarios];
            
            for (int i = 0; i < numOperarios; i++) {
                productores[i]=new Productor(buzonRevision, buzonReproceso, numProductos, i);
                productores[i].start();
            }
            // Esperar a que todos los productores terminen antes de enviar el producto FIN/venenos
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
            
            Thread[] equiposCalidad = new Thread[numOperarios];
            for (int i = 0; i < numOperarios; i++) {
                equiposCalidad[i] = new EquipoCalidad(buzonRevision, buzonReproceso, deposito, maxFallos);
                equiposCalidad[i].start();
            }

            // Esperar a que todos los equipos de calidad terminen
            for (Thread equipo : equiposCalidad) {
                try {
                    equipo.join();
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


