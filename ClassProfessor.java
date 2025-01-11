import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ClassProfessor {

    private int opcaDoMenu;

    ClassProfessor() {
        this.opcaDoMenu = 0;
    }

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
                    escolherPasta(scanner);
                    break;
                case 2:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void escolherPasta(Scanner scanner) {
        File pastaRaiz = new File("Quiz");
        if (!pastaRaiz.exists()) {
            pastaRaiz.mkdir(); // Cria a pasta "Quiz" se não existir
        }

        File[] subpastas = pastaRaiz.listFiles(File::isDirectory);
        if (subpastas == null || subpastas.length == 0) {
            System.out.println("\nNenhuma pasta encontrada. Crie uma nova pasta para começar.");
            criarNovaPasta(pastaRaiz, scanner);
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
            exibirMenuAcoes(pastaSelecionada, scanner);
        } else if (escolha == subpastas.length + 1) {
            criarNovaPasta(pastaRaiz, scanner);
        } else {
            System.out.println("Escolha inválida.");
        }
    }

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
            exibirMenuAcoes(novaPasta, scanner);
        } else {
            System.out.println("Não foi possível criar a pasta. Talvez ela já exista.");
        }
    }

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
                    adicionarNovoTema(pasta, scanner);
                    break;
                case 2:
                    removerTema(pasta, scanner);
                    break;
                case 3:
                    exibirTemas(pasta);
                    break;
                case 4:
                    alterarTema(pasta, scanner);
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

    public static void main(String[] args) {
        new ClassProfessor().iniciarSistema();
    }
}
