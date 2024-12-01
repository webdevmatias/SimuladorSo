# Simulador de Escalonamento de Processos

Este projeto é um **Simulador de Escalonamento de Processos** desenvolvido em **Java** que permite criar e gerenciar processos em um sistema simulado. O objetivo é demonstrar os conceitos de escalonamento de processos, utilizando algoritmos como **Round Robin** e **Priority Scheduling**. 

O simulador permite a criação de processos com diferentes atributos, como prioridade e tipo (I/O Bound ou CPU Bound), e oferece uma interface simples para que o usuário interaja com o sistema.

---

## **Estruturas Utilizadas**

### 1. **Classe `Processo`**
Representa cada processo no sistema. Esta classe contém os seguintes atributos:

- **ID (int):** Identificador único do processo.
- **Nome (String):** Nome do processo.
- **Prioridade (int):** Indica a prioridade do processo (quanto menor o valor, maior a prioridade).
- **isIOBound (boolean):** Define se o processo é orientado a I/O (verdadeiro) ou CPU (falso).
- **tempoCPU (int):** Tempo total que o processo precisa na CPU.
- **tempoRestante (int):** Tempo restante para o processo concluir sua execução.
- **tempoTurnaround (int):** Tempo total desde a criação até a finalização do processo.
- **tempoEspera (int):** Tempo total que o processo esperou na fila de prontos.

A classe também inclui um método `toString` para apresentar as informações principais de um processo.

---

### 2. **Classe `ConsoleColor`**
Facilita a exibição de mensagens no terminal, utilizando cores para melhorar a legibilidade. Contém constantes que representam estilos e cores ANSI, como:
- `RESET`: Reseta o estilo do texto.
- `CYAN`, `RED`, `GREEN`, etc.: Definem cores para mensagens.

---

### 3. **Lista `filaProntos`**
- **Estrutura:** `List<Processo>`
- **Descrição:** Armazena todos os processos prontos para execução. Cada elemento é um objeto da classe `Processo`.
- **Função:** Organizar os processos de acordo com suas prioridades ou tempo restante, dependendo do algoritmo escolhido.

---

### 4. **Métodos Principais**

#### 4.1 `criarProcesso()`
- Permite ao usuário criar novos processos com atributos personalizados.
- Verifica se o **ID** fornecido já existe na `filaProntos`.

#### 4.2 `exibirFilaProntos()`
- Exibe os processos prontos na fila.
- Caso a fila esteja vazia, uma mensagem é exibida.

#### 4.3 `iniciarEscalonamento()`
- Oferece dois algoritmos de escalonamento para escolha do usuário:
  - **Round Robin:** Executa os processos em intervalos de tempo (quantum) fixos, garantindo fairness.
  - **Priority Scheduling:** Executa processos com base na prioridade definida pelo usuário.

#### 4.4 `roundRobin(int quantum)`
- Implementa o algoritmo Round Robin:
  - Cada processo recebe um **quantum** (tempo máximo de execução por vez).
  - Se o processo não terminar dentro do quantum, ele volta ao final da fila.
  - Os tempos de **turnaround** e **espera** são atualizados conforme o andamento.

#### 4.5 `priorityScheduling()`
- Implementa o algoritmo de escalonamento por prioridade:
  - Os processos são ordenados por prioridade (1 = mais alta).
  - Cada processo é executado até a conclusão antes do próximo iniciar.
  - Atualiza os tempos de **turnaround** e **espera**.

---

## **Algoritmos de Escalonamento**

### **1. Round Robin**
- **Descrição:** 
  - Divide o tempo da CPU em intervalos fixos (quantum) e distribui igualmente entre os processos.
- **Critérios de Avaliação:**
  - **Turnaround Time:** Tempo total desde a chegada do processo até a conclusão.
  - **Tempo de Espera:** Tempo total que o processo passou na fila de prontos.
  - **Equidade:** Garante que todos os processos tenham acesso à CPU em intervalos regulares.

---

### **2. Priority Scheduling**
- **Descrição:** 
  - Processos com maior prioridade (menor número) são executados antes.
- **Critérios de Avaliação:**
  - **Prioridade:** Processos mais importantes recebem atenção primeiro.
  - **Tempo de Espera e Turnaround:** Avaliados com base na ordem de execução.

---

## **Critérios de Avaliação**

1. **Turnaround Time:** Tempo desde a chegada até a conclusão de cada processo.
2. **Tempo de Espera:** Quanto tempo um processo ficou na fila antes de ser executado.
3. **Eficiência do Algoritmo:** Avaliação da adequação do algoritmo para cenários específicos:
   - Round Robin é mais adequado para sistemas multitarefa.
   - Priority Scheduling é ideal para sistemas que exigem prioridades rígidas.
4. **Uso Justo da CPU:** Garantir que todos os processos tenham acesso à CPU de maneira justa (especialmente no Round Robin).

---

## **Exemplo de Execução**

1. Criação de processos:
   ```
   ID: 1, Nome: ProcessoA, Prioridade: 1, Tipo: CPU Bound, CPU Total: 5
   ID: 2, Nome: ProcessoB, Prioridade: 3, Tipo: I/O Bound, CPU Total: 3
   ```

2. Escolha do algoritmo:
   ```
   Algoritmo: Round Robin
   Quantum: 2
   ```

3. Resultado (Round Robin):
   ```
   Executando processo: ProcessoA (quantum 2)
   Executando processo: ProcessoB (quantum 2)
   Executando processo: ProcessoA (quantum 1)
   Processo concluído: ProcessoA
   Processo concluído: ProcessoB
   ```

4. Resultado (Priority Scheduling):
   ```
   Executando processo: ProcessoA
   Processo concluído: ProcessoA
   Executando processo: ProcessoB
   Processo concluído: ProcessoB
   ```

---

## **Como Executar**
1. Compile o código:
   ```bash
   javac SimuladorEscalonamento.java
   ```
2. Execute o programa:
   ```bash
   java SimuladorEscalonamento
   ```

---

## **Contribuição**
Contribuições são bem-vindas! Envie pull requests ou abra issues para sugestões de melhorias.
