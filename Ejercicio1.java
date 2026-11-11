public class Ejercicio1 {

    static final int NO = -1; 

    
    static int[][] tiempos = {
        { 5, 15,  3, 20, NO},   
        {20, 10, 20, 12,  5},   
        {13, 11, 19, 11, 16},   
        {19,  8, 10, NO,  5},   
        { 5, 17,  9, NO, 19}   
    };

    static int n = 5;
    static boolean[] zonaUsada = new boolean[n];
    static int[] asignacion = new int[n];       
    static int[] mejorAsignacion = new int[n];
    static int mejorTiempo = Integer.MAX_VALUE;

    static int nodos = 0;                       
    static boolean usarPodaB = false;

    
    static void backtracking(int p, int tiempoActual) {
        nodos++;


        if (usarPodaB && tiempoActual >= mejorTiempo) return;

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

    static void resolver(boolean podaB, String titulo) {
        usarPodaB = podaB;
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
        resolver(false, "a) Sin podas");
        resolver(true, "b) Con poda por mejor solucion");
    }
}