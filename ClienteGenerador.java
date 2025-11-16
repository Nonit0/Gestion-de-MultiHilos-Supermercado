import java.util.Random;

public class ClienteGenerador extends Thread {
    private final int totalClientes;
    private final RecaudacionDiaria recurso;

    public ClienteGenerador(int totalClientes, RecaudacionDiaria recurso){
        this.totalClientes = totalClientes;
        this.recurso = recurso;
    }

    @Override
    public void run() {
        Random rd = new Random();
        for (int i = 0; i < totalClientes; i++) {
            Cliente cliente = new Cliente();
            recurso.encolarCLiente(cliente);

            if (cliente.getId()<=9) {
                System.out.println(coloresTexto.B_CYAN + "El cliente Nº 00" + cliente.getId() + " llega a la cola" + coloresTexto.RESET);
            }else if(cliente.getId()<=99){
                System.out.println(coloresTexto.B_CYAN + "El cliente Nº 0" + cliente.getId() + " llega a la cola" + coloresTexto.RESET);
            }else{
                System.out.println(coloresTexto.B_CYAN + "El cliente Nº " + cliente.getId() + " llega a la cola" + coloresTexto.RESET);
            }


            try {
                Thread.sleep(rd.nextInt(11) + 20); // 20-30 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(coloresTexto.B_YELLOW + "\nTodos los clientes han sido generados\n"+coloresTexto.RESET);
    }
}
