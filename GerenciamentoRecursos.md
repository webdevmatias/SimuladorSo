# Gerenciamento de Recursos com Semáforos em Java

Este projeto demonstra o gerenciamento de acesso concorrente a recursos compartilhados em Java utilizando **Semáforos**. O objetivo é ilustrar como múltiplas threads podem acessar recursos compartilhados de forma controlada, evitando **condições de corrida** e garantindo a **exclusão mútua**.

---

## 🛠️ Estruturas e Conceitos Utilizados

### **1. Semáforo (`Semaphore`)**
- **Definição:** Um semáforo é uma estrutura de sincronização que restringe o número de threads que podem acessar um recurso compartilhado simultaneamente.
- **Funcionamento:**
  - **`acquire()`**: Tenta obter uma permissão para acessar o recurso. Se nenhuma permissão estiver disponível, a thread fica bloqueada até que outra thread libere uma.
  - **`release()`**: Libera uma permissão, permitindo que outra thread acesse o recurso.
- **No código:**
  - Usamos dois semáforos (`semaforoRecurso1` e `semaforoRecurso2`), cada um controlando o acesso a um recurso compartilhado (`recurso1` e `recurso2`), permitindo **apenas uma thread por vez**.

### **2. Recursos Compartilhados**
- Variáveis `recurso1` e `recurso2` são os **recursos compartilhados** pelas threads.
- Cada thread modifica o valor dessas variáveis de forma controlada pelos semáforos.

### **3. Threads**
- **Definição:** Uma thread é uma unidade de execução independente. Em Java, threads podem ser criadas com a classe `Thread` ou interfaces como `Runnable`.
- **No código:**
  - Criamos 5 threads que executam continuamente e tentam acessar os recursos compartilhados.
  - Cada thread aguarda sua vez para acessar os recursos, respeitando as permissões fornecidas pelos semáforos.

---

## 📜 Funcionamento do Programa

1. **Inicialização das Threads**
   - O programa inicia 5 threads numeradas de 1 a 5.
   - Cada thread tenta acessar `recurso1` e `recurso2`, alternadamente.

2. **Acesso Controlado aos Recursos**
   - Quando uma thread tenta acessar `recurso1`, ela aguarda até que o semáforo (`semaforoRecurso1`) conceda permissão.
   - Após concluir o uso de `recurso1`, ela tenta acessar `recurso2`, aguardando o semáforo correspondente.

3. **Execução Simultânea**
   - O programa simula trabalho nos recursos compartilhados utilizando `Thread.sleep()`.
   - Isso permite observar o comportamento das threads enquanto aguardam ou acessam os recursos.

4. **Liberação de Recursos**
   - Após usar os recursos, a thread libera as permissões (`release()`), permitindo que outras threads acessem os mesmos recursos.

---

## ⚙️ Critérios de Avaliação

### **1. Exclusão Mútua**
- O semáforo garante que **apenas uma thread por vez** acesse cada recurso, evitando **condições de corrida**.

### **2. Sincronização**
- As threads aguardam pacientemente suas permissões para acessar os recursos, demonstrando controle e ordenação.

### **3. Controle e Segurança**
- Os recursos são acessados de forma segura, sem que duas threads interfiram na execução uma da outra.

### **4. Liveness e Fairness**
- Todas as threads têm a chance de acessar os recursos, sem ficarem permanentemente bloqueadas.

---

## 💻 Código Explicado

```java
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
                    acessarRecursos(threadId); // Cada thread tenta acessar os dois recursos

                    try {
                        Thread.sleep(3000); // Pausa para alternância de threads
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
            semaforoRecurso1.acquire(); // Obtém permissão
            recurso1++;
            System.out.println("Thread " + threadId + " acessou Recurso 1. Valor: " + recurso1);
            Thread.sleep(1000); // Simula trabalho

            // BLOCO 2: Tentando acessar Recurso 2
            System.out.println("Thread " + threadId + " aguardando para acessar Recurso 2...");
            semaforoRecurso2.acquire(); // Obtém permissão
            recurso2++;
            System.out.println("Thread " + threadId + " acessou Recurso 2. Valor: " + recurso2);
            Thread.sleep(1000); // Simula trabalho

        } catch (InterruptedException e) {
            System.out.println("Thread " + threadId + " foi interrompida enquanto aguardava.");
            Thread.currentThread().interrupt();
        } finally {
            // BLOCO 3: Libera os recursos
            semaforoRecurso1.release(); 
            semaforoRecurso2.release(); 
            System.out.println("Thread " + threadId + " liberou os recursos.");
        }
    }
}
```

---

## 🔍 Observações
- O programa ilustra a importância de mecanismos de sincronização, como semáforos, para gerenciar recursos compartilhados.
- Ele demonstra controle eficiente e seguro, evitando erros comuns em sistemas concorrentes, como *deadlocks* ou *race conditions*.
