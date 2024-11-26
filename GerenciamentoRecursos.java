import java.util.concurrent.Semaphore;

public class GerenciamentoRecursos {

    // Semáforos para controlar acesso a cada recurso
    private static final Semaphore semaforoRecurso1 = new Semaphore(1); // Permite apenas 1 thread por vez
    private static final Semaphore semaforoRecurso2 = new Semaphore(1);

    // Recursos compartilhados
    private static int recurso1 = 0;
    private static int recurso2 = 0;

    public static void main(String[] args) {
        // Cria 5 threads concorrentes
        for (int i = 1; i <= 5; i++) {
            int threadId = i; // Identificador único da thread
            new Thread(() -> {
                while (true) {
                    // Cada thread tenta acessar os dois recursos
                    acessarRecursos(threadId);

                    // Simula pausa para alternância de threads
                    try {
                        Thread.sleep(3000); // Thread "descansa" antes de tentar novamente
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread " + threadId + " interrompida.");
                    }
                }
            }).start();
        }
    }

    private static void acessarRecursos(int threadId) {
        try {
            // BLOCO 1: Tentando acessar Recurso 1
            System.out.println("Thread " + threadId + " aguardando para acessar Recurso 1...");
            semaforoRecurso1.acquire(); // Obtém permissão para acessar Recurso 1
            recurso1++; // Modifica o recurso compartilhado
            System.out.println("Thread " + threadId + " acessou Recurso 1. Valor: " + recurso1);
            Thread.sleep(1000); // Simula trabalho no recurso

            // BLOCO 2: Tentando acessar Recurso 2
            System.out.println("Thread " + threadId + " aguardando para acessar Recurso 2...");
            semaforoRecurso2.acquire(); // Obtém permissão para acessar Recurso 2
            recurso2++; // Modifica o recurso compartilhado
            System.out.println("Thread " + threadId + " acessou Recurso 2. Valor: " + recurso2);
            Thread.sleep(1000); // Simula trabalho no recurso

        } catch (InterruptedException e) {
            System.out.println("Thread " + threadId + " foi interrompida enquanto aguardava.");
            Thread.currentThread().interrupt();
        } finally {
            // BLOCO 3: Libera os recursos
            semaforoRecurso1.release(); // Libera o Recurso 1 para outras threads
            semaforoRecurso2.release(); // Libera o Recurso 2 para outras threads
            System.out.println("Thread " + threadId + " liberou os recursos.");
        }
    }
}
