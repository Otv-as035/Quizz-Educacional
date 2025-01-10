import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.util.Scanner;


class ClassProfessor {

    private int opcaDoMenu = 0;

    public void criarQuiz() {
        String caminhoPasta = "Quiz"; 

        // Cria o objeto File
        File pasta = new File(caminhoPasta);

        // Verifica se a pasta existe
        if (pasta.exists() && pasta.isDirectory()) {
            // Lista as subpastas
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                System.out.println("\nArquivos de Niveis:");
                int contador = 0; // Contador para exibir opções numeradas

                for (File arquivo : arquivos) {
                    if (arquivo.isDirectory()) {
                        contador++;
                        System.out.println(contador + " - " + arquivo.getName());
                    }
                }

                if (contador == 0) {
                    System.out.println("Nenhuma subpasta foi encontrada.");
                    return;
                }

                // Solicita ao usuário escolher uma subpasta
                try (Scanner scanner = new Scanner(System.in)) {
                    System.out.print("\nDigite o número da subpasta que deseja abrir: ");
                    int escolha = scanner.nextInt();

                    if (escolha > 0 && escolha <= contador) {
                        int indice = 0; // Contador para localizar a pasta escolhida
                        for (File arquivo : arquivos) {
                            if (arquivo.isDirectory()) {
                                indice++;
                                if (indice == escolha) {
                                    // Abre a subpasta selecionada e exibe o menu de opções
                                    abrirPasta(arquivo);
                                    exibirMenuAcoes(arquivo);
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Escolha inválida.");
                    }
                }
            } else {
                System.out.println("Erro ao listar os arquivos da pasta.");
            }
        } else {
            System.out.print("\nA pasta especificada não existe ou não é um diretório.\n");
        }
    }

    private void abrirPasta(File pasta) {
        // Verifica se o sistema suporta Desktop
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                // Abre a pasta
                desktop.open(pasta);
                System.out.println("Pasta aberta: " + pasta.getName());
            } catch (IOException e) {
                System.out.println("Erro ao abrir a pasta: " + e.getMessage());
            }
        } else {
            System.out.println("A funcionalidade Desktop não é suportada neste sistema.");
        }
    }

    private void exibirMenuAcoes(File pasta) {
        try (Scanner scanner = new Scanner(System.in)) {
        

            while (opcaDoMenu != 5) { // O menu vai até o usuário escolher sair (opção 5)
                System.out.println("\nMenu de Ações:");
                System.out.println("1 - Adicionar novo tema");
                System.out.println("2 - Remover tema");
                System.out.println("3 - Exibir todas as perguntas");
                System.out.println("4 - Alterar perguntas");
                System.out.println("5 - Sair");
                System.out.print("Escolha uma opção: ");
                opcaDoMenu = scanner.nextInt();

                switch (opcaDoMenu) {
                    case 1:
                        adicionarNovoTema(pasta);
                        break;
                    case 2:
                        removerTema(pasta);
                        break;
                    case 3:
                        exibirPerguntas(pasta);
                        break;
                    case 4:
                        alterarPergunta(pasta); // Aqui chamamos a função para alterar a pergunta
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            }
        }
    }

    private void adicionarNovoTema(File pasta) {
        
    }

    private void removerTema(File pasta) {
        
    }

    private void exibirPerguntas(File pasta) {
       
    }

    private void alterarPergunta(File pasta) {
    
    }

    public static void main(String[] args) {
        // Executa a criação do quiz
        new ClassProfessor().criarQuiz();
    }
}