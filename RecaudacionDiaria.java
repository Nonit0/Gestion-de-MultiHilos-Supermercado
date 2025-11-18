import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

// 1.- RECURSO COMPARTIDO - SECCIÓN CRÍTICA
public class RecaudacionDiaria {
    // 1.1.- Variables
    private final Queue<Cliente> cola = new LinkedList<>(); // Queue para encolar a los clientes, y usar el .add() y .poll()
    private int clientesRestantes; // Número de clientes restantes
    private double recaudacionTotal = 0; // Dinero total ganado

    // 1.2.- Semáforos para zona crítica
    private final Semaphore semaforoClientes = new Semaphore(0); // Controla cuándo pueden crearse los clientes (inicia bloqueado == 0)
    private final Semaphore mutexCola = new Semaphore(1); // Mutex para la cola: solo un hilo puede entrar a la vez == 1
    private final Semaphore mutexCaja = new Semaphore(1); // Mutex para la caja: solo un hilo puede atender/pagar a la vez == 1

    // 1.3.- Constructor
    public RecaudacionDiaria(int numeroClientes) { 
        this.clientesRestantes = numeroClientes;
    }

    // 1.4.- Método para Encolar cliente
    public void encolarCLiente(Cliente cliente){
        try {
            mutexCola.acquire(); // Entra en zona crítica de cola == SemaforoCola a 0, bloquea el acceso a la zona crítica
            cola.add(cliente); // Añade el cliente a la cola
            mutexCola.release(); // Sale de la zona crítica de cola == SemaforoCola a 1, desbloquea el acceso a la zona crítica
            semaforoClientes.release(); // Señaliza que hay un cliente disponible == SemáforoClientes +1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1.5.- Método para atender al siguiente cliente
public Cliente atenderCliente(){
    Cliente cliente = null; 
    try {
        semaforoClientes.acquire(); // Entra en zona crítica de cola == SemaforoCola -1, bloquea el acceso a la zona crítica
            mutexCola.acquire(); // Entra en zona crítica de cola == SemáforoCola a 0, bloquea el acceso a la zona crítica
        cliente = cola.poll();      // Extrae el primer cliente de la cola
        if (cliente != null) clientesRestantes--; // Disminuye el contador de clientes restantes
            mutexCola.release(); // Sale de la zona crítica de cola == SemáforoCola a 1, desbloquea el acceso a la zona crítica
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return cliente; // Devuelve el cliente atendido
}

// 1.6.- Método para actualizar la recaudación total
public void actualizarRecaudacionTotal(double totalCliente) {
    try {
        mutexCaja.acquire();               // Entra en zona crítica de la caja == SemaforoCaja a 0)
        recaudacionTotal += totalCliente;  // Suma el total del cliente a la recaudación
        mutexCaja.release();               // Sale de la zona crítica de la caja == SemaforoCaja a 1)
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}

// 1.7.- Getter sincronizado de clientes restantes
public synchronized int getClientesRestantes() {
    return clientesRestantes; 
}

// 1.8.- Getter de la recaudación total
public double getRecaudacionTotal() {
    return recaudacionTotal; 
}

}
