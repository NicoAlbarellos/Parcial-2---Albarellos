public class Ejercicio2 {

    static int[] pesos = {14, 29, 5, 22, 10, 2, 26, 12, 7, 15};
    static int capacidad = 65;
    static int k = 4;                    // cantidad de paquetes del lote
    static int n = pesos.length;

    static int[] elegidos = new int[k];  // pesos elegidos en la combinacion actual
    static int soluciones = 0;

    static int nodos = 0;                // cuantas veces se llama al backtracking
    static boolean usarPodasB = false;

    // i = desde que paquete puedo elegir, cant = cuantos llevo, suma = peso acumulado
    static void backtracking(int i, int cant, int suma) {
        nodos++;

        if (cant == k) { // ya tengo 4 paquetes
            if (suma == capacidad) {
                soluciones++;
                imprimir();
            }
            return;
        }
        // b) Poda 1: si el peso ya supera la capacidad, corto.
        // Todos los pesos son positivos: agregar paquetes solo aumenta el peso,
        // asi que ya no hay forma de volver a 65.
        if (usarPodasB && suma > capacidad) return;

        // b) Poda 2: si no quedan suficientes paquetes para completar 4, corto.
        // Quedan (n - i) paquetes disponibles y me faltan (k - cant); si quedan menos
        // de los que faltan, es imposible armar el lote por esta rama.
        if (usarPodasB && n - i < k - cant) return;

        // j + 1: solo sigo con los paquetes que estan DESPUES del elegido,
        // asi no repito paquetes ni la misma combinacion en otro orden
        for (int j = i; j < n; j++) {
            elegidos[cant] = pesos[j];                       // elegir
            backtracking(j + 1, cant + 1, suma + pesos[j]);  // bajar
            elegidos[cant] = 0;                              // deshacer
        }
    }

    static void imprimir() {
        String texto = "";
        for (int t = 0; t < k; t++) {
            texto += elegidos[t];
            if (t < k - 1) texto += " + ";
        }
        System.out.println(texto + " = " + capacidad);
    }

    static void resolver(boolean podasB, String titulo) {
        usarPodasB = podasB;
        soluciones = 0;
        nodos = 0;
        System.out.println("=== " + titulo + " ===");
        backtracking(0, 0, 0);
        System.out.println("Combinaciones: " + soluciones + " - Nodos explorados: " + nodos);
    }

    public static void main(String[] args) {
        resolver(false, "a) Sin podas");
        resolver(true, "b) Con podas por factibilidad");
    }
}