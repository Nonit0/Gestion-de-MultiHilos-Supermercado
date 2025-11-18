import java.util.Random;

// 1.- Clase cliente, la usamos para generar Clientes
public class Cliente {
    // 1.1.- Variables
    private static int contador = 0; // Contador para aumentar el ID
    private final int idCliente; // ID único por cliente
    private final double[] carrito; // Array double para guardar los productos con un precio en decimal

    // 1.2.- Constructor
    public Cliente() {
        Random rd = new Random(); // random para los precios
        this.idCliente = ++contador; // incrementa el contador para cada cliente y hacer id único (también ayuda a saber cuantos clientes habrá)

        int numeroProductos = rd.nextInt(10) + 1; // cantidad de productos en el carrito (empieza en 0 -> 0-9), le sumamos uno
        carrito = new double[numeroProductos]; // declaramos el carrito con el numero de productos aleatorio

        for (int i = 0; i < numeroProductos; i++) { // bucle para recorrer el array
            double precio = 0.1 + rd.nextDouble() * (50 - 0.1); // añade precios a los productos: 0.1 (para empezar en 0.1) + (numero entre 0.0 - 1.0)* (49.9)
            carrito[i] = precio; // Redondearemos con stringFormat en los sisos correspondientes
        }
    }

    // 1.3.- Getter
    public int getId() {
        return idCliente;
    }

    // 1.4.- Método que calcula el valor final del carrito del cliente
    public double totalCompraCliente() {
        double total = 0;
        for (double precio : carrito) total += precio;
        return total;
    }
}
