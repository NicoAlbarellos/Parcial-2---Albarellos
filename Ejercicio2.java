public class Ejercicio2 {

    static int[] pesos = {14, 29, 5, 22, 10, 2, 26, 12, 7, 15};
    static int capacidad = 65;
    static int k = 4;                    // cantidad de paquetes del lote
    static int n = pesos.length;

    static int[] elegidos = new int[k];  // pesos elegidos en la combinacion actual
    static int soluciones = 0;

    // i = desde que paquete puedo elegir, cant = cuantos llevo, suma = peso acumulado
    static void backtracking(int i, int cant, int suma) {
        if (cant == k) { // ya tengo 4 paquetes
            if (suma == capacidad) {
                soluciones++;
                imprimir();
            }
            return;
        }
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

    public static void main(String[] args) {
        backtracking(0, 0, 0);
        System.out.println("Cantidad de combinaciones: " + soluciones);
    }
}