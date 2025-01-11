import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ClassProfessor {

    private int opcaDoMenu;

    /**
     * Construtor da classe que inicializa o menu principal.
     */
    ClassProfessor() {
        this.opcaDoMenu = 0;
    }

    /**
     * Inicia o sistema e exibe o menu principal.
     * O menu permite ao usuário escolher entre acessar uma pasta ou sair do sistema.
     */
    public void iniciarSistema() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Escolher uma pasta");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do Scanner

            switch (opcao) {
                case 1:
                    escolherPasta(scanner); // Chama o método para escolher uma pasta
                    break;
                case 2:
                    System.out.println("Saindo...");
                    scanner.close(); // Fecha o scanner ao sair
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Permite ao usuário escolher uma pasta já existente ou criar uma nova.
     * As pastas existentes são listadas para o usuário escolher.
     */
    private void escolherPasta(Scanner scanner) {
        File pastaRaiz = new File("Quiz");
        if (!pastaRaiz.exists()) {
            pastaRaiz.mkdir(); // Cria a pasta "Quiz" se não existir
        }

        File[] subpastas = pastaRaiz.listFiles(File::isDirectory);
        if (subpastas == null || subpastas.length == 0) {
            System.out.println("\nNenhuma pasta encontrada. Crie uma nova pasta para começar.");
            criarNovaPasta(pastaRaiz, scanner); // Cria nova pasta se não houver nenhuma
            return;
        }

        System.out.println("\nSubpastas disponíveis:");
        for (int i = 0; i < subpastas.length; i++) {
            System.out.println((i + 1) + " - " + subpastas[i].getName());
        }
        System.out.println((subpastas.length + 1) + " - Criar nova pasta");

        System.out.print("Escolha uma subpasta: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do Scanner

        if (escolha > 0 && escolha <= subpastas.length) {
            File pastaSelecionada = subpastas[escolha - 1];
            exibirMenuAcoes(pastaSelecionada, scanner); // Exibe o menu de ações para a pasta escolhida
        } else if (escolha == subpastas.length + 1) {
            criarNovaPasta(pastaRaiz, scanner); // Cria nova pasta caso o usuário escolha essa opção
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    /**
     * Cria uma nova pasta dentro do diretório raiz.
     * Solicita o nome da nova pasta e tenta criá-la.
     */
    private void criarNovaPasta(File pastaRaiz, Scanner scanner) {
        System.out.print("\nDigite o nome da nova pasta: ");
        String nomeNovaPasta = scanner.nextLine().trim();

        if (nomeNovaPasta.isEmpty()) {
            System.out.println("O nome da pasta não pode estar vazio.");
            return;
        }

        File novaPasta = new File(pastaRaiz, nomeNovaPasta);
        if (novaPasta.mkdir()) {
            System.out.println("Pasta criada com sucesso: " + novaPasta.getAbsolutePath());
            exibirMenuAcoes(novaPasta, scanner); // Exibe o menu de ações para a nova pasta
        } else {
            System.out.println("Não foi possível criar a pasta. Talvez ela já exista.");
        }
    }

    /**
     * Exibe o menu de ações para o usuário realizar operações dentro da pasta escolhida.
     * O usuário pode adicionar, remover, exibir ou alterar temas dentro da pasta.
     */
    private void exibirMenuAcoes(File pasta, Scanner scanner) {
        while (opcaDoMenu != 5) {
            System.out.println("\nMenu de Ações - Pasta: " + pasta.getName());
            System.out.println("1 - Adicionar novo tema");
            System.out.println("2 - Remover tema");
            System.out.println("3 - Exibir todos os temas");
            System.out.println("4 - Alterar tema");
            System.out.println("5 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcaDoMenu = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do Scanner

            switch (opcaDoMenu) {
                case 1:
                    adicionarNovoTema(pasta, scanner); // Chama o método para adicionar um novo tema
                    break;
                case 2:
                    removerTema(pasta, scanner); // Chama o método para remover um tema
                    break;
                case 3:
                    exibirTemas(pasta); // Chama o método para exibir todos os temas
                    break;
                case 4:
                    alterarTema(pasta, scanner); // Chama o método para alterar um tema
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        opcaDoMenu = 0; // Resetar o menu ao sair
    }

    /**
     * Adiciona um novo tema (arquivo de texto) dentro da pasta selecionada.
     * O tema é criado com o nome especificado pelo usuário.
     */
    private void adicionarNovoTema(File pasta, Scanner scanner) {
        System.out.print("\nDigite o nome do novo tema: ");
        String nomeTema = scanner.nextLine().trim();

        if (nomeTema.isEmpty()) {
            System.out.println("O nome do tema não pode estar vazio.");
            return;
        }

        File novoArquivo = new File(pasta, nomeTema + ".txt");
        try {
            if (novoArquivo.createNewFile()) {
                System.out.println("Tema criado com sucesso: " + novoArquivo.getAbsolutePath());
            } else {
                System.out.println("O tema já existe.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar o tema: " + e.getMessage());
        }
    }

    /**
     * Remove um tema (arquivo) da pasta selecionada.
     * O usuário escolhe qual tema deseja excluir, e o arquivo correspondente é deletado.
     */
    private void removerTema(File pasta, Scanner scanner) {
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum tema encontrado para remover.");
            return;
        }

        System.out.println("\nTemas disponíveis para remoção:");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.println((i + 1) + " - " + arquivos[i].getName());
        }

        System.out.print("Escolha o número do tema que deseja remover: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do Scanner

        if (escolha > 0 && escolha <= arquivos.length) {
            File temaSelecionado = arquivos[escolha - 1];
            if (temaSelecionado.delete()) {
                System.out.println("Tema removido com sucesso: " + temaSelecionado.getName());
            } else {
                System.out.println("Erro ao remover o tema: " + temaSelecionado.getName());
            }
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    /**
     * Exibe a lista de todos os temas disponíveis na pasta.
     * O usuário pode visualizar todos os arquivos de tema dentro da pasta selecionada.
     */
    private void exibirTemas(File pasta) {
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum tema encontrado.");
            return;
        }

        System.out.println("\nTemas disponíveis:");
        for (File arquivo : arquivos) {
            System.out.println("- " + arquivo.getName());
        }
    }

    /**
     * Permite ao usuário alterar o conteúdo de um tema (arquivo de texto) dentro da pasta selecionada.
     * O conteúdo do arquivo é exibido e o usuário pode modificá-lo.
     */
    private void alterarTema(File pasta, Scanner scanner) {
        // Lista os arquivos dentro da pasta para o usuário escolher qual deseja alterar
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum tema encontrado para alterar.");
            return;
        }

        System.out.println("\nTemas disponíveis para alteração:");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.println((i + 1) + " - " + arquivos[i].getName());
        }

        // Solicita ao usuário que escolha o arquivo que deseja alterar
        System.out.print("Escolha o número do tema que deseja alterar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        if (escolha > 0 && escolha <= arquivos.length) {
            File temaSelecionado = arquivos[escolha - 1];

            // Verifica se o arquivo é um arquivo de texto (.txt)
            if (!temaSelecionado.getName().endsWith(".txt")) {
                System.out.println("Somente arquivos .txt podem ser alterados.");
                return;
            }

            // Exibe o conteúdo atual do arquivo
            System.out.println("\nConteúdo atual do tema:");
            try (Scanner fileScanner = new Scanner(temaSelecionado)) {
                while (fileScanner.hasNextLine()) {
                    System.out.println(fileScanner.nextLine());
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o conteúdo do arquivo.");
                return;
            }

            // Solicita ao usuário a nova versão do conteúdo
            System.out.println("\nDigite o novo conteúdo para o tema (digite 'sair' para finalizar):");
            StringBuilder novoConteudo = new StringBuilder();
            String linha;
            while (!(linha = scanner.nextLine()).equalsIgnoreCase("sair")) {
                novoConteudo.append(linha).append("\n");
            }

            // Grava o novo conteúdo no arquivo
            try (FileWriter writer = new FileWriter(temaSelecionado)) {
                writer.write(novoConteudo.toString());
                System.out.println("Tema alterado com sucesso: " + temaSelecionado.getName());
            } catch (IOException e) {
                System.out.println("Erro ao alterar o tema: " + e.getMessage());
            }
        } else {
            System.out.println("Escolha inválida.");
        }
    }

    /**
     * Método principal que inicia o sistema de gerenciamento de temas.
     * @param args Argumentos da linha de comando (não utilizados aqui)
     */
    public static void main(String[] args) {
        new ClassProfessor().iniciarSistema();
    }
}
