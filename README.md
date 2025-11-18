# SupermercadoPSP
Ejercicio de práctica con Threads y Semáforos

# Simulación de Clientes y Cajeros en un Supermercado

Este proyecto es una **simulación de un supermercado** en Java, donde los clientes son generados como hilos y los cajeros atienden a los clientes de manera concurrente usando **colas y semáforos** como recursos compartidos.  

---

## 1.- Descripción del proyecto

El objetivo es simular cómo varios clientes compran productos y son atendidos por cajeros en un supermercado, considerando:

- Hasta **100 clientes** como máximo.  
- Cada cliente tiene entre **1 y 10 productos** con precio entre **0.10 y 50 €**.  
- **5 cajeros** disponibles, cada uno con su caja individual.  
- Una **caja común** que suma el valor total de todas las compras.  
- Tiempos de espera:
  - Los clientes llegan a la cola entre **10 y 30 ms**.  
  - Los cajeros atienden a un cliente cada **500-1000 ms**.  
- Uso de **colas (`Queue`)** y **semáforos (`Semaphore`)** para sincronización y exclusión mutua.  

El programa ilustra conceptos de **concurrencia, sincronización y control de recursos compartidos** en Java.

---

## 2.- Estructura del proyecto

Hilos/  
└─ supermercado/  
  ├─ Cliente.java # Modela un cliente con carrito de productos  
  ├─ ClienteGenerador.java # Genera clientes y los encola  
  ├─ CajeroConsumidor.java # Hilo que atiende clientes y acumula su caja  
  ├─ RecaudacionDiaria.java # Recurso compartido: cola de clientes y caja común  
  └─ Supermercado.java # Clase principal que lanza la simulación  

### 2.1.- Descripción de clases

- **Cliente.java**  
  Representa a un cliente:
  - Atributos: id, carrito de productos con precios aleatorios.  
  - Método `totalCompraCliente()`: devuelve el valor total del carrito.  

- **ClienteGenerador.java**  
  Hilo generador de clientes:
  - Crea clientes de manera secuencial.  
  - Los agrega a la cola (`encolarCLiente`).  
  - Simula la llegada de clientes con tiempo aleatorio entre 10-30 ms.  

- **CajeroConsumidor.java**  
  Hilo que representa a un cajero:
  - Extrae clientes de la cola (`atenderCliente`).  
  - Calcula el total de la compra y lo acumula en su caja individual.  
  - Actualiza la caja común de manera segura usando semáforos.  
  - Simula el tiempo de atención de cada cliente (500-1000 ms).  

- **RecaudacionDiaria.java**  
  Recurso compartido:
  - Cola de clientes (`Queue<Cliente>`).  
  - Caja común (`recaudacionTotal`).  
  - Semáforos:
    - `semaforoClientes`: controla cuántos clientes hay disponibles en la cola.  
    - `semaforoCaja`: asegura que solo un cliente actualice la caja común a la vez.  
    - `mutexCola` y `mutexCaja`: garantizan exclusión mutua al modificar recursos compartidos.  
  - Métodos:
    - `encolarCLiente(Cliente)`: agrega un cliente a la cola y libera el semáforo de clientes.  
    - `atenderCliente(Cliente)`: extrae un cliente de la cola y devuelve el cliente atendido.  
    - `actualizarRecaudacionTotal(double)`: actualiza la caja común de manera segura.  

- **Supermercado.java**  
  Clase principal:
  - Inicializa el recurso compartido `RecaudacionDiaria`.  
  - Crea y lanza el hilo generador de clientes (`ClienteGenerador`).  
  - Crea y lanza 5 cajeros (`CajeroConsumidor`).  
  - Espera a que todos los hilos terminen (`join`).  
  - Muestra la recaudación de cada caja individual y el total de la caja común.  

---

## 3.- Requisitos

- Java 8 o superior.  
- IDE o compilador que soporte hilos (`Thread`) en Java.  
- Terminal para ejecutar el programa.  

---

## 4.- Cómo ejecutar

4.1. Clonar el repositorio:
```bash
git clone https://github.com/Nonit0/SupermercadoPSP.git
```

4.2. Compilar los archivos .java:
```bash
cd Gestion-de-MultiHilos-Supermercado/
javac *.java
```

4.3. Ejecutar la simulación:
```bash
java Supermercado
```
## 5.- Colores en consola

5.1.- Para diferenciar mensajes en la simulación se utiliza la clase coloresTexto:
```bash
// Reset del color
public static final String RESET = "\u001B[0m";

// Introducción
public static final String W_BEIGE = "\u001B[38;5;180m"; 

// Colores de texto brillantes
public static final String B_BLACK = "\u001B[90m";
public static final String B_RED = "\u001B[91m";       // Mensajes de cliente atendido
public static final String B_GREEN = "\u001B[92m";     // Mensajes de recaudación total
public static final String B_YELLOW = "\u001B[93m";
public static final String B_BLUE = "\u001B[94m";      // Mensajes de cliente llegando a la cola
public static final String B_MAGENTA = "\u001B[95m";   // Mensajes de resultados finales
public static final String B_CYAN = "\u001B[96m";
public static final String B_WHITE = "\u001B[97m";
```
5.2.- Ejemplo de uso
```bash
System.out.println(coloresTexto.B_BLUE + "El cliente llega a la cola" + coloresTexto.RESET);
System.out.println(coloresTexto.B_RED + "El cliente ha sido atendido" + coloresTexto.RESET);
System.out.println(coloresTexto.B_MAGENTA + "=== RESULTADOS FINALES ===" + coloresTexto.RESET);
System.out.println(coloresTexto.B_GREEN + "Recaudación total: 1234.56€" + coloresTexto.RESET);
```

## 6.- Ejemplo de ejecución
6.1.- Capturas de pantalla sobre la ejecución:
<img width="788" height="883" alt="1super" src="https://github.com/user-attachments/assets/fa3aecd0-ff5f-48aa-8290-b274f77a04ce" />
<img width="787" height="883" alt="super2" src="https://github.com/user-attachments/assets/069b8b34-604c-4791-aed1-e39807543938" />
<img width="787" height="884" alt="super3" src="https://github.com/user-attachments/assets/6be32a26-f0b1-41a3-a52e-4b0e24f5a3bf" />
<img width="786" height="885" alt="super4" src="https://github.com/user-attachments/assets/69547311-bc3d-4a4c-a7b4-3b3839d8bc01" />
<img width="776" height="188" alt="super5" src="https://github.com/user-attachments/assets/55504678-fa21-4379-9f69-194fe1f616d4" />


## 7.- Licencia
Este proyecto es educativo, para práctica de programación concurrente en Java.
Se puede copiar, modificar y redistribuir libremente para fines de aprendizaje.


