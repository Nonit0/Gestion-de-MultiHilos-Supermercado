import java.util.Random;

// 1.- Clase productora, extiende de la clase Thread
public class ClienteGenerador extends Thread {
    // 1.1.- Variable
    private final int totalClientes; // Total de Clientes que generamos
    private final RecaudacionDiaria recurso; // Referencia al recurso compartido

    // 1.2.- Constructor, le pasamos el total de clientes que vayamos a crear, Recurso compartido (sección crítica)
    public ClienteGenerador(int totalClientes, RecaudacionDiaria recurso){ // Pasamos el numero de clientes a generar, y el recurso
        this.totalClientes = totalClientes;
        this.recurso = recurso;
    }

    // 1.3.- Método Run para ejecutar el Productor
    @Override
    public void run() {
        // 1.3.1.- Random para simular el tiempo de llegada a la cola
        Random rd = new Random(); 

        // 1.3.2.- Bucle para mandar los clientes a la cola de la caja
        for (int i = 0; i < totalClientes; i++) { 
            Cliente cliente = new Cliente(); // Creamos un cliente
            recurso.encolarCLiente(cliente); // Mandamos a la cola el cliente (acceso a zona compartida)

            // 1.3.2.3.- Esto es simplemente para cambiar el siso y que se vea mas bonito y ver como van llegando por terminal
            if (cliente.getId()<=9) { 
                System.out.println(coloresTexto.B_CYAN + "El cliente Nº 00" + cliente.getId() + " llega a la cola" + coloresTexto.RESET);
            }else if(cliente.getId()<=99){
                System.out.println(coloresTexto.B_CYAN + "El cliente Nº 0" + cliente.getId() + " llega a la cola" + coloresTexto.RESET);
            }else{
                System.out.println(coloresTexto.B_CYAN + "El cliente Nº " + cliente.getId() + " llega a la cola" + coloresTexto.RESET);
            }


            try { 
                Thread.sleep(rd.nextInt(11) + 20); // Simulamos el tiempo de llegada de un cliente a la cola de las cajas del supermercado
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 1.3.3.- Avisamos cuando hayan llegado todos los clientes
        System.out.println(coloresTexto.B_YELLOW + "\nTodos los clientes han sido generados\n"+coloresTexto.RESET); 
    }
}
