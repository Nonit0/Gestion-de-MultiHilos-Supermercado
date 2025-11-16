import java.util.Random;

public class CajeroConsumidor extends Thread {
    private final RecaudacionDiaria recurso;
    private final int idCajero;
    private double cajaCajero = 0;
    private final Random rd = new Random();

    public CajeroConsumidor(RecaudacionDiaria recurso, int idCajero){
        this.recurso = recurso;
        this.idCajero = idCajero;
    }

    @Override
    public void run() {
        while (true) {
            if (recurso.getClientesRestantes() <= 0) break;

            Cliente cliente = recurso.atenderCliente();
            if (cliente != null) {
                double totalCliente = cliente.totalCompraCliente();
                cajaCajero += totalCliente; // suma al cajero
                recurso.actualizarRecaudacionTotal(totalCliente); // suma al total global


            if (cliente.getId()<=9) {
                System.out.println(coloresTexto.B_RED + "Cajero " + idCajero + " atendió al cliente Nº00" +cliente.getId() + " | Total compra: " + totalCliente + "€"+coloresTexto.RESET );
            }else if(cliente.getId()<=99){
                System.out.println(coloresTexto.B_RED + "Cajero " + idCajero + " atendió al cliente Nº0" +cliente.getId() + " | Total compra: " + totalCliente + "€"+coloresTexto.RESET );
            }else{
                System.out.println(coloresTexto.B_RED + "Cajero " + idCajero + " atendió al cliente Nº" +cliente.getId() + " | Total compra: " + totalCliente + "€"+coloresTexto.RESET );
            }


                try {
                    Thread.sleep(rd.nextInt(501) + 500); // 500-1000 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        }    

        System.out.println(coloresTexto.W_BEIGE + "Cajero " + idCajero + " terminó su jornada. Caja propia: " + cajaCajero + "€"+coloresTexto.RESET);
    }

    public double getCajaPropia() {
        return cajaCajero;
    }

    public int getIdCajero() {
        return idCajero;
    }
}
