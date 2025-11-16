import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class RecaudacionDiaria {
    private final Queue<Cliente> cola = new LinkedList<>();
    private int clientesRestantes;
    private double recaudacionTotal = 0;

    private final Semaphore semaforoClientes = new Semaphore(0); 
    private final Semaphore mutexCola = new Semaphore(1); 
    private final Semaphore mutexCaja = new Semaphore(1); 

    public RecaudacionDiaria(int numeroClientes) {
        this.clientesRestantes = numeroClientes;
    }

    public void encolarCLiente(Cliente cliente){
        try {
            mutexCola.acquire();
            cola.add(cliente);
            mutexCola.release();
            semaforoClientes.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Cliente atenderCliente(){
        Cliente cliente = null;
        try {
            semaforoClientes.acquire(); // espera a que haya un cliente
            mutexCola.acquire();
            cliente = cola.poll();
            if (cliente != null) clientesRestantes--;
            mutexCola.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public void actualizarRecaudacionTotal(double totalCliente) {
        try {
            mutexCaja.acquire();
            recaudacionTotal += totalCliente;
            mutexCaja.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getClientesRestantes() {
        return clientesRestantes;
    }

    public double getRecaudacionTotal() {
        return recaudacionTotal;
    }
}
