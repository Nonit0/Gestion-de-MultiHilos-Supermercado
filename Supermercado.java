public class Supermercado {
    final static int TOTAL_CLIENTES = 100;
    final static int TOTAL_CAJEROS = 5;
    
    public static void main(String[] args) {
        RecaudacionDiaria recursoCompartido = new RecaudacionDiaria(TOTAL_CLIENTES);

        // Productor
        ClienteGenerador generador = new ClienteGenerador(TOTAL_CLIENTES, recursoCompartido);
        generador.start();

        // Consumidores
        CajeroConsumidor[] cajeros = new CajeroConsumidor[TOTAL_CAJEROS];
        for (int i = 0; i < TOTAL_CAJEROS; i++) {
            cajeros[i] = new CajeroConsumidor(recursoCompartido, i + 1);
            cajeros[i].start();
        }

        try {
            generador.join();
            for (CajeroConsumidor c : cajeros) c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(coloresTexto.B_MAGENTA+"\n=== RESULTADOS FINALES ==="+coloresTexto.RESET);
        double total = 0;
        for (CajeroConsumidor c : cajeros) {
            System.out.println(coloresTexto.B_MAGENTA+"Cajero " + c.getIdCajero() + " recaudó: " +c.getCajaPropia() + "€"+coloresTexto.RESET);
            total += c.getCajaPropia();
        }
        System.out.println(coloresTexto.B_GREEN+"\nRecaudación total del supermercado: " +recursoCompartido.getRecaudacionTotal() + "€"+coloresTexto.RESET);
    }
}
