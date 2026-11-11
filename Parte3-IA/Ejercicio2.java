public class Ejercicio2 {

    static int[] pesos = {14, 29, 5, 22, 10, 2, 26, 12, 7, 15};
    static int capacidad = 65;
    static int k = 4;                    // cantidad de paquetes del lote
    static int n = pesos.length;

    static int[] elegidos = new int[k];  // pesos elegidos en la combinacion actual
    static int soluciones = 0;

    static int nodos = 0;                // cuantas veces se llama al backtracking
    static boolean usarPodasB = false;
    static boolean usarPodaC = false;

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
        // b) Poda 1: si el peso ya llego o supera la capacidad, corto.
        // Todos los pesos son positivos: agregar paquetes solo aumenta el peso,
        // asi que ya no hay forma de volver a 65.
        //
        // CORRECCION (Parte 3, con IA):
        // Que estaba mal: la condicion era "suma > capacidad", entonces no cortaba cuando
        //   la suma ya era exactamente 65 pero todavia faltaban paquetes.
        // Por que estaba mal: si ya sume 65 y me falta al menos un paquete, al agregarlo
        //   (peso positivo) me paso seguro. Esa rama nunca da solucion y se seguia explorando.
        // Como se corrigio: se cambio a "suma >= capacidad". Este if esta despues del
        //   "if (cant == k)", asi que solo corta cuando todavia faltan paquetes y no pierde
        //   soluciones: siguen saliendo las 7. Nodos: b) 367 -> 358, todas 208 -> 199.
        if (usarPodasB && suma >= capacidad) return;

        // b) Poda 2: si no quedan suficientes paquetes para completar 4, corto.
        // Quedan (n - i) paquetes disponibles y me faltan (k - cant); si quedan menos
        // de los que faltan, es imposible armar el lote por esta rama.
        if (usarPodasB && n - i < k - cant) return;

        // c) Poda fuerte por defecto: aun sumando los paquetes MAS PESADOS que quedan,
        // no llego a la capacidad. Esa es la mejor suma posible de esta rama; si ni asi
        // llego a 65, ninguna eleccion real va a llegar, y corto sin perder soluciones.
        if (usarPodaC && suma + masPesados(i, k - cant) < capacidad) return;

        // j + 1: solo sigo con los paquetes que estan DESPUES del elegido,
        // asi no repito paquetes ni la misma combinacion en otro orden
        for (int j = i; j < n; j++) {
            elegidos[cant] = pesos[j];                       // elegir
            backtracking(j + 1, cant + 1, suma + pesos[j]);  // bajar
            elegidos[cant] = 0;                              // deshacer
        }
    }

    // Suma de los 'faltan' paquetes mas pesados entre los que quedan (desde la posicion 'desde').
    static int masPesados(int desde, int faltan) {
        int total = 0;
        boolean[] tomado = new boolean[n];
        for (int t = 0; t < faltan; t++) {
            int max = -1;
            for (int j = desde; j < n; j++) {
                if (!tomado[j] && (max == -1 || pesos[j] > pesos[max])) max = j;
            }
            if (max == -1) return total; // no quedan mas paquetes
            tomado[max] = true;
            total += pesos[max];
        }
        return total;
    }

    static void imprimir() {
        String texto = "";
        for (int t = 0; t < k; t++) {
            texto += elegidos[t];
            if (t < k - 1) texto += " + ";
        }
        System.out.println(texto + " = " + capacidad);
    }

    static void resolver(boolean podasB, boolean podaC, String titulo) {
        usarPodasB = podasB;
        usarPodaC = podaC;
        soluciones = 0;
        nodos = 0;
        System.out.println("=== " + titulo + " ===");
        backtracking(0, 0, 0);
        System.out.println("Combinaciones: " + soluciones + " - Nodos explorados: " + nodos);
    }

    public static void main(String[] args) {
        resolver(false, false, "a) Sin podas");
        resolver(true, false, "b) Con podas por factibilidad");
        resolver(true, true, "c) Con todas las podas");
    }
}