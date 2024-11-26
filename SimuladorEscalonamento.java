import java.util.*;

/**
 * Classe que representa um Processo no sistema. Cada processo tem um ID, nome, prioridade, tipo (I/O Bound ou CPU Bound),
 * tempo total de CPU, tempo restante, tempo de turnaround e tempo de espera.
 */
class Processo {
    int id;               // ID único do processo
    String nome;          // Nome do processo
    int prioridade;       // Prioridade do processo (quanto menor, maior a prioridade)
    boolean isIOBound;    // Indica se o processo é I/O Bound (verdadeiro) ou CPU Bound (falso)
    int tempoCPU;         // Tempo total de CPU que o processo requer
    int tempoRestante;    // Tempo restante de execução do processo
    int tempoTurnaround;  // Tempo de turnaround do processo (tempo desde a chegada até a finalização)
    int tempoEspera;      // Tempo de espera do processo na fila de prontos

    /**
     * Construtor para inicializar um novo Processo.
     * 
     * @param id ID único do processo.
     * @param nome Nome do processo.
     * @param prioridade Prioridade do processo (1 é a maior prioridade).
     * @param isIOBound Indica se o processo é I/O Bound.
     * @param tempoCPU Tempo total de CPU requerido pelo processo.
     */
    public Processo(int id, String nome, int prioridade, boolean isIOBound, int tempoCPU) {
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.isIOBound = isIOBound;
        this.tempoCPU = tempoCPU;
        this.tempoRestante = tempoCPU;
        this.tempoTurnaround = 0;
        this.tempoEspera = 0;
    }

    /**
     * Retorna uma string representando as informações principais de um processo.
     * 
     * @return String representando o processo.
     */
    @Override
    public String toString() {
        return String.format("%sID: %d, Nome: %s, Prioridade: %d, Tipo: %s, CPU Total: %d, Restante: %d%s",
                ConsoleColor.CYAN, id, nome, prioridade, isIOBound ? "I/O Bound" : "CPU Bound", tempoCPU, tempoRestante, ConsoleColor.RESET);
    }
}

/**
 * Classe que define as cores usadas no console para melhorar a legibilidade das saídas.
 */
class ConsoleColor {
    public static final String RESET = "\033[0m";      // Resetar estilo e cor
    public static final String CYAN = "\033[36m";      // Cor ciano
    public static final String RED = "\033[31m";       // Cor vermelha
    public static final String GREEN = "\033[32m";     // Cor verde
    public static final String YELLOW = "\033[33m";    // Cor amarela
    public static final String BLUE = "\033[34m";      // Cor azul
    public static final String BOLD = "\033[1m";       // Texto em negrito
    public static final String UNDERLINE = "\033[4m";  // Texto sublinhado
}

/**
 * Classe principal que simula o escalonamento de processos com diferentes algoritmos.
 * O simulador oferece a criação de processos, exibição da fila de prontos, e execução de escalonamento de processos
 * com base em algoritmos como Round Robin e Priority Scheduling.
 */
public class SimuladorEscalonamento {
    private static final Scanner scanner = new Scanner(System.in);  // Scanner para leitura de entradas do usuário
    private static final List<Processo> filaProntos = new ArrayList<>();  // Lista que armazena os processos prontos para execução

    /**
     * Função principal que inicia o simulador e apresenta o menu de opções para o usuário.
     * A função executa até que o usuário escolha a opção de sair.
     */
    public static void main(String[] args) {
        System.out.println(ConsoleColor.GREEN + "\n=== Simulador de Escalonamento de Processos ===" + ConsoleColor.RESET);
        while (true) {
            // Exibe o menu de opções
            System.out.println(ConsoleColor.YELLOW + "\n|---| Escolha uma opção |---|" + ConsoleColor.RESET);
            System.out.println("| 1 | Criar Processo       ");
            System.out.println("| 2 | Exibir Fila de Prontos");
            System.out.println("| 3 | Iniciar Escalonamento");
            System.out.println("| 4 | Sair                 ");
            System.out.print(ConsoleColor.YELLOW + "| Opção: " + ConsoleColor.RESET);
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consome a quebra de linha

            switch (opcao) {
                case 1 -> criarProcesso();          // Opção para criar um novo processo
                case 2 -> exibirFilaProntos();      // Opção para exibir a fila de prontos
                case 3 -> iniciarEscalonamento();   // Opção para iniciar o escalonamento de processos
                case 4 -> {
                    System.out.println(ConsoleColor.RED + "Encerrando o simulador." + ConsoleColor.RESET);
                    return;  // Sai do programa
                }
                default -> System.out.println(ConsoleColor.RED + "Opção inválida. Tente novamente." + ConsoleColor.RESET);  // Caso de opção inválida
            }
        }
    }

    /**
     * Função que permite ao usuário criar um novo processo e adicioná-lo à fila de prontos.
     * Garante que o ID do processo seja único.
     */
    private static void criarProcesso() {
        System.out.println(ConsoleColor.GREEN + "\n=== Criar Processo ===" + ConsoleColor.RESET);
        System.out.print("ID do Processo: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consome a quebra de linha

        // Verifica se o ID já existe na fila
        while (existeProcessoComID(id)) {
            System.out.println(ConsoleColor.RED + "Erro: Já existe um processo com esse ID. Tente outro ID." + ConsoleColor.RESET);
            System.out.print("ID do Processo: ");
            id = scanner.nextInt();
            scanner.nextLine();  // Consome a quebra de linha
        }

        System.out.print("Nome do Processo: ");
        String nome = scanner.nextLine();

        System.out.print("Prioridade do Processo (1 a 5, 1 = mais alta): ");
        int prioridade = scanner.nextInt();

        System.out.print("Processo é I/O Bound? (true/false): ");
        boolean isIOBound = scanner.nextBoolean();

        System.out.print("Tempo total de CPU (1 a 10 ms): ");
        int tempoCPU = scanner.nextInt();

        // Cria um novo processo e adiciona à fila de prontos
        filaProntos.add(new Processo(id, nome, prioridade, isIOBound, tempoCPU));
        System.out.println(ConsoleColor.GREEN + "Processo adicionado à fila de prontos.\n" + ConsoleColor.RESET);
    }

    /**
     * Função que verifica se já existe um processo com o ID fornecido.
     * 
     * @param id O ID a ser verificado.
     * @return true se já existe um processo com esse ID, false caso contrário.
     */
    private static boolean existeProcessoComID(int id) {
        for (Processo p : filaProntos) {
            if (p.id == id) {
                return true;  // Retorna verdadeiro se o ID já existir
            }
        }
        return false;  // Retorna falso se não encontrar o ID
    }

    /**
     * Função que exibe todos os processos na fila de prontos.
     */
    private static void exibirFilaProntos() {
        System.out.println("\n" + ConsoleColor.CYAN + "\n=== Fila de Prontos ===" + ConsoleColor.RESET);
        if (filaProntos.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Nenhum processo na fila.\n" + ConsoleColor.RESET);
        } else {
            // Exibe cada processo da fila
            filaProntos.forEach(System.out::println);
        }
    }

    /**
     * Função que inicia o processo de escalonamento de acordo com o algoritmo escolhido pelo usuário.
     */
    private static void iniciarEscalonamento() {
        if (filaProntos.isEmpty()) {
            System.out.println(ConsoleColor.RED + "Nenhum processo para escalonar.\n" + ConsoleColor.RESET);
            return;
        }

        System.out.println("\n" + ConsoleColor.YELLOW + "|-----| Escolha o algoritmo de escalonamento |-----|" + ConsoleColor.RESET);
        System.out.println("| 1 | Round Robin (RR)     |");
        System.out.println("| 2 | Priority Scheduling (PS) |");
        System.out.print("| Algoritmo: ");
        int algoritmo = scanner.nextInt();

        System.out.print("Defina o tempo de quantum (1 a 10 ms): ");
        int quantum = scanner.nextInt();

        switch (algoritmo) {
            case 1 -> roundRobin(quantum);         // Chama o algoritmo Round Robin
            case 2 -> priorityScheduling();        // Chama o algoritmo Priority Scheduling
            default -> System.out.println(ConsoleColor.RED + "Algoritmo inválido!" + ConsoleColor.RESET);
        }
    }

    /**
     * Função que simula o escalonamento de processos usando o algoritmo Round Robin (RR).
     * 
     * @param quantum Tempo de quantum definido pelo usuário.
     */
    private static void roundRobin(int quantum) {
        System.out.println(ConsoleColor.GREEN + "\n=== Escalonamento Round Robin ===" + ConsoleColor.RESET);

        int tempoAtual = 0;

        for (Processo processo : filaProntos) {
            System.out.println(ConsoleColor.BLUE + "Executando processo: " + processo.nome + ConsoleColor.RESET);

            int tempoExecucao = Math.min(quantum, processo.tempoRestante);
            processo.tempoRestante -= tempoExecucao;
            tempoAtual += tempoExecucao;

            // Atualiza os tempos de turnaround e espera
            processo.tempoTurnaround = tempoAtual;
            processo.tempoEspera = processo.tempoTurnaround - processo.tempoCPU;

            if (processo.tempoRestante > 0) {
                filaProntos.add(processo);  // Coloca o processo de volta na fila caso não tenha terminado
            } else {
                System.out.println("Processo concluído: " + processo.nome);
            }
        }

        System.out.println(ConsoleColor.GREEN + "Escalonamento Round Robin concluído!" + ConsoleColor.RESET);
    }

    /**
     * Função que simula o escalonamento de processos usando o algoritmo Priority Scheduling.
     */
    private static void priorityScheduling() {
        System.out.println(ConsoleColor.GREEN + "\n=== Escalonamento Priority Scheduling ===" + ConsoleColor.RESET);

        // Ordena os processos pela prioridade
        filaProntos.sort(Comparator.comparingInt(p -> p.prioridade));

        int tempoAtual = 0;

        for (Processo processo : filaProntos) {
            System.out.println(ConsoleColor.BLUE + "Executando processo: " + processo.nome + ConsoleColor.RESET);
            processo.tempoTurnaround = tempoAtual + processo.tempoCPU;
            processo.tempoEspera = tempoAtual;
            tempoAtual += processo.tempoCPU;

            System.out.println("Processo concluído: " + processo.nome);
        }

        System.out.println(ConsoleColor.GREEN + "Escalonamento Priority Scheduling concluído!" + ConsoleColor.RESET);
    }
}
