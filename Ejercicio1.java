public class Ejercicio1 {

    static final int NO = -1; // el repartidor no puede cubrir esa zona

    // tiempos[repartidor][zona] en minutos
    static int[][] tiempos = {
        { 5, 15,  3, 20, NO},   // P1
        {20, 10, 20, 12,  5},   // P2
        {13, 11, 19, 11, 16},   // P3
        {19,  8, 10, NO,  5},   // P4
        { 5, 17,  9, NO, 19}    // P5
    };

    static int n = 5;
    static boolean[] zonaUsada = new boolean[n];
    static int[] asignacion = new int[n];       // asignacion[p] = zona que cubre el repartidor p
    static int[] mejorAsignacion = new int[n];
    static int mejorTiempo = Integer.MAX_VALUE;

    static int nodos = 0;                       // cuantas veces se llama al backtracking
    static boolean usarPodaB = false;
    static boolean usarPodaC = false;

    // p = repartidor que estoy asignando ahora
    static void backtracking(int p, int tiempoActual) {
        nodos++;

        // b) Poda: si el tiempo acumulado ya es mayor o igual al de la mejor solucion
        // encontrada, corto esta rama.
        // Por que no pierde la solucion optima: todos los tiempos son positivos, entonces
        // asignar a los repartidores que faltan solo puede SUMAR minutos, nunca restar.
        // Cualquier solucion completa de esta rama va a tardar >= tiempoActual >= mejorTiempo,
        // o sea que ninguna puede ser mejor que la que ya tengo.
        if (usarPodaB && tiempoActual >= mejorTiempo) return;

        // c) Poda fuerte: tiempo acumulado + cota optimista de lo que falta.
        if (usarPodaC && tiempoActual + cotaOptimista(p) >= mejorTiempo) return;

        if (p == n) { // ya asigne a los 5
            if (tiempoActual < mejorTiempo) {
                mejorTiempo = tiempoActual;
                for (int i = 0; i < n; i++) mejorAsignacion[i] = asignacion[i];
            }
            return;
        }
        for (int z = 0; z < n; z++) {
            if (!zonaUsada[z] && tiempos[p][z] != NO) {
                zonaUsada[z] = true;   // elegir
                asignacion[p] = z;
                backtracking(p + 1, tiempoActual + tiempos[p][z]); // bajar
                zonaUsada[z] = false;  // deshacer
            }
        }
    }

    // Cota optimista: a cada repartidor que falta le sumo su zona libre MAS RAPIDA,
    // aunque dos repartidores elijan la misma zona. La asignacion real nunca puede
    // ser mas rapida que esto, asi que la cota nunca sobreestima: es optimista.
    static int cotaOptimista(int p) {
        int cota = 0;
        for (int i = p; i < n; i++) {
            int minimo = Integer.MAX_VALUE;
            for (int z = 0; z < n; z++) {
                if (!zonaUsada[z] && tiempos[i][z] != NO && tiempos[i][z] < minimo) {
                    minimo = tiempos[i][z];
                }
            }
            if (minimo == Integer.MAX_VALUE) return 100000; // no le queda zona posible: rama imposible
            cota += minimo;
        }
        return cota;
    }

    static void resolver(boolean podaB, boolean podaC, String titulo) {
        usarPodaB = podaB;
        usarPodaC = podaC;
        mejorTiempo = Integer.MAX_VALUE;
        nodos = 0;
        backtracking(0, 0);

        System.out.println("=== " + titulo + " ===");
        for (int p = 0; p < n; p++) {
            int z = mejorAsignacion[p];
            System.out.println("P" + (p + 1) + " -> Z" + (z + 1) + " (" + tiempos[p][z] + " min)");
        }
        System.out.println("Tiempo total: " + mejorTiempo + " min - Nodos explorados: " + nodos);
    }

    public static void main(String[] args) {
        resolver(false, false, "a) Sin podas");
        resolver(true, false, "b) Con poda por mejor solucion");
        resolver(true, true, "c) Con las dos podas");
    }
}