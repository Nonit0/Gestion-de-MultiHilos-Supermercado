import java.util.Random;

// 1.- Clase Consumidora, extiende de la clase Thread
public class CajeroConsumidor extends Thread {
    // 1.1.- Variables
    private final RecaudacionDiaria recurso; // Referencia al recurso compartido
    private final int idCajero; // Id de los distintos cajeros, que ademas es el ID de estos
    private double cajaCajero = 0; // Double donde se guarda el dinero recolectado por cada caja de manera individual
    private final Random rd = new Random(); // Random para simular tiempos de espera

    // 1.2.- Constructor, le pasamos el total de cajeros que vayamos a crear (sirve como id) , Recurso compartido (sección crítica)
    public CajeroConsumidor(RecaudacionDiaria recurso, int idCajero){ // Constructor
        this.recurso = recurso;
        this.idCajero = idCajero;
    }

    // 1.3.- Método Run para ejecutar el Productor
    @Override
    public void run() {
        // 1.3.1.- While(true), se ejecuta siempre 
        while (true) {
            if (recurso.getClientesRestantes() <= 0) break; // Para salir del while, comprobar si siguen existiendo clientes, cuando no haya sale del bucle

            Cliente cliente = recurso.atenderCliente(); // Atiende un cliente cuando llega (zona crítica)
            // 1.3.1.1.- Bucle que comprueba que el cliente se haya generado correctamente, si es así:
            if (cliente != null) {
                double totalCliente = cliente.totalCompraCliente(); // Guarda el dinero gastado por el cliente
                cajaCajero += totalCliente; // Suma al cajero individual
                recurso.actualizarRecaudacionTotal(totalCliente); // Suma al cajero común el total gastado del cliente (zona crítica)

            // 1.3.1.2.- Esto es simplemente para cambiar el siso y que se vea mas bonito y ver como van entrando a las cajas por terminal
            if (cliente.getId()<=9) {
                System.out.println(coloresTexto.B_RED + "Cajero " + idCajero + " atendió al cliente Nº00" +cliente.getId() + " | Total compra: " + totalCliente + "€"+coloresTexto.RESET );
            }else if(cliente.getId()<=99){
                System.out.println(coloresTexto.B_RED + "Cajero " + idCajero + " atendió al cliente Nº0" +cliente.getId() + " | Total compra: " + totalCliente + "€"+coloresTexto.RESET );
            }else{
                System.out.println(coloresTexto.B_RED + "Cajero " + idCajero + " atendió al cliente Nº" +cliente.getId() + " | Total compra: " + totalCliente + "€"+coloresTexto.RESET );
            }


                try {
                    Thread.sleep(rd.nextInt(501) + 500); // // Simulamos el tiempo de llegada de un cliente a la caja del supermercado
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        }    
        // 1.2.- Avisamos cuando hayan pasados todos los clientes por caja
        System.out.println(coloresTexto.W_BEIGE + "Cajero " + idCajero + " terminó su jornada. Caja propia: " + cajaCajero + "€"+coloresTexto.RESET);
    }

    // Getter de la caja
    public double getCajaPropia() {
        return cajaCajero;
    }

    // Getter del ID
    public int getIdCajero() {
        return idCajero;
    }
}
