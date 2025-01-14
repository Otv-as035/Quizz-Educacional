import java.util.Scanner;

public class ClassPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Criação de instâncias de Usuário, Professor e Jogador
        Usuario usuario = new Usuario();
        ClassProfessor professor = new ClassProfessor();
        //Jogador jogador = new Jogador(); // Classe Jogador ainda precisa ser criada

        while (true) {
            System.out.println("\n=== Sistema Principal ===");
            System.out.println("1. Fazer Login");
            System.out.println("2. Cadastro de Novo Usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
            
            switch (opcao) {
                case 1:
                    // Login
                    String tipoUsuario = usuario.menuDeAutenticacao();
                    if (tipoUsuario != null) {
                        // Redireciona conforme o tipo de usuário (Aluno ou Professor)
                        if (tipoUsuario.equals("Professor")) {
                            professor.iniciarSistema();
                        } else if (tipoUsuario.equals("Aluno")) {
                            System.out.println("Bem-vindo, Aluno! Acesso ao conteúdo de quizzes.");
                            // Aqui podemos chamar um menu ou funcionalidades específicas do Aluno
                        }
                    }
                    break;
                    
                case 2:
                    // Cadastro de novo usuário
                    Usuario.cadastrarUsuario(usuario, scanner);
                    break;

                case 5:
                    // Sair do sistema
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}


