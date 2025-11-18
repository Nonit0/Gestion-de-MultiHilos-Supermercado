// 1.- Clase Principal
public class Supermercado {
    // 1.1.- Variables
    public final static int TOTAL_CLIENTES = 100; // Define los clientes a generar
    final static int TOTAL_CAJEROS = 5; // Define los cajeros a generar
    
    // 1.2.- Main
    public static void main(String[] args) {
        // 1.2.1.- Creamos el recurso compartido
        RecaudacionDiaria recursoCompartido = new RecaudacionDiaria(TOTAL_CLIENTES);

        // 1.2.2.- Creamos el Productor, con el total de clientes a generar y el recurso compartido
        ClienteGenerador generador = new ClienteGenerador(TOTAL_CLIENTES, recursoCompartido);
        generador.start(); // Iniciamos el hilo

        // 1.2.3.- Creamos un array de Consumidor (para después guardar el dinero), con el total de cajeros a generar
        CajeroConsumidor[] cajeros = new CajeroConsumidor[TOTAL_CAJEROS];
        // 1.2.4.- Bucle para recorrer los cajeros que se hayan creado, asignándoles ID, y llamando al recurso compartido
        for (int i = 0; i < TOTAL_CAJEROS; i++) {
            cajeros[i] = new CajeroConsumidor(recursoCompartido, i + 1); // Se crea el cajero
            cajeros[i].start(); // Iniciamos el hilo
        }

        // 1.2.5.- Aseguramos que los cajeros no acaben antes de tiempo
        try {
            generador.join(); // Hasta que no acabe el hilo generador (clientes), no avanza.
            for (CajeroConsumidor c : cajeros) // Bucle para recorrer los cajeros
            c.join(); // Main espera a que cada consumidor (cajeros) terminen de manera individual cada uno
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 1.2.6.- Imprimimos los resultado finales
        System.out.println(coloresTexto.B_MAGENTA+"\n=== RESULTADOS FINALES ==="+coloresTexto.RESET);
        double total = 0; // Variable para guardar el total ganado en los cajeros
        for (CajeroConsumidor c : cajeros) { // Bucle para recorrer los cajeros y sacar de manera individual el dinero que han ganado
            System.out.println(coloresTexto.B_MAGENTA+"Cajero " + c.getIdCajero() + " recaudó: " +c.getCajaPropia() + "€"+coloresTexto.RESET);
            total += c.getCajaPropia();
        }
        System.out.println(coloresTexto.B_GREEN+"\nRecaudación total del supermercado: " +recursoCompartido.getRecaudacionTotal() + "€"+coloresTexto.RESET); // Imprimimos el total ganado por los cajeros
    }
}
