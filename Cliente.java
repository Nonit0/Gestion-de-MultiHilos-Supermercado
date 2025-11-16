import java.util.Random;

public class Cliente {
    private static int contador = 0; 
    private final int idCliente; // ID único por cliente
    private final double[] carrito;

    public Cliente() {
        Random rd = new Random();
        this.idCliente = ++contador; // incrementa el contador para cada cliente

        int numeroProductos = rd.nextInt(10) + 1;
        carrito = new double[numeroProductos];

        for (int i = 0; i < numeroProductos; i++) {
            double precio = 0.1 + rd.nextDouble() * (50 - 0.1); // 0.1 a 50 €
            carrito[i] = Math.round(precio*100.0)/100;
        }
    }

    public int getId() {
        return idCliente;
    }

    public double totalCompraCliente() {
        double total = 0;
        for (double precio : carrito) total += precio;
        return total;
    }
}
