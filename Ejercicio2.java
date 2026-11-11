public class Ejercicio2 {

    static int[] pesos = {14, 29, 5, 22, 10, 2, 26, 12, 7, 15};
    static int capacidad = 65;
    static int k = 4;                    
    static int n = pesos.length;

    static int[] elegidos = new int[k];  
    static int soluciones = 0;

    static int nodos = 0;                
    static boolean usarPodasB = false;
    static boolean usarPodaC = false;

    
    static void backtracking(int i, int cant, int suma) {
        nodos++;

        if (cant == k) { // ya tengo 4 paquetes
            if (suma == capacidad) {
                soluciones++;
                imprimir();
            }
            return;
        }

        
        if (usarPodasB && suma > capacidad) return;


        if (usarPodasB && n - i < k - cant) return;


        if (usarPodaC && suma + masPesados(i, k - cant) < capacidad) return;

      
        for (int j = i; j < n; j++) {
            elegidos[cant] = pesos[j];                       // elegir
            backtracking(j + 1, cant + 1, suma + pesos[j]);  // bajar
            elegidos[cant] = 0;                              // deshacer
        }
    }

    
    static int masPesados(int desde, int faltan) {
        int total = 0;
        boolean[] tomado = new boolean[n];
        for (int t = 0; t < faltan; t++) {
            int max = -1;
            for (int j = desde; j < n; j++) {
                if (!tomado[j] && (max == -1 || pesos[j] > pesos[max])) max = j;
            }
            if (max == -1) return total; 
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