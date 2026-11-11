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

    // p = repartidor que estoy asignando ahora
    static void backtracking(int p, int tiempoActual) {
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

    public static void main(String[] args) {
        backtracking(0, 0);
        for (int p = 0; p < n; p++) {
            int z = mejorAsignacion[p];
            System.out.println("P" + (p + 1) + " -> Z" + (z + 1) + " (" + tiempos[p][z] + " min)");
        }
        System.out.println("Tiempo total: " + mejorTiempo + " min");
    }
}