import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Usuario {

    private String nome;
    private int matricula;
    private String email;
    private String senha;
    private final Scanner scan = new Scanner(System.in);

    public void autenticar() {
        System.out.println("##### Autenticação de Usuário #####");
        System.out.print("LOGIN: ");
        email = scan.nextLine();
        System.out.print("SENHA: ");
        senha = scan.nextLine();

        // Verificar o tipo de usuário com base nos arquivos
        String tipoUsuario = buscarArquivo();
        if (null == tipoUsuario) {
            System.out.println("Credenciais inválidas.");
        } else switch (tipoUsuario) {
            case "Professor":
                System.out.println("Bem-vindo, Professor!");
                // Ações específicas para professores
                break;
            case "Aluno":
                System.out.println("Bem-vindo, Aluno!");
                // Ações específicas para alunos
                break;
            default:
                System.out.println("Credenciais inválidas.");
                break;
        }
    }

    public String buscarArquivo() {
        // Verificar no arquivo de professores
        if (verificarCredenciais("Senhas/SenhasProfessores.txt")) {
            return "Professor";
        }

        // Verificar no arquivo de alunos
        if (verificarCredenciais("Senhas/SenhasAlunos.txt")) {
            return "Aluno";
        }

        // Retornar caso o usuário não seja encontrado
        return "Usuário Não Válido";
    }

    private boolean verificarCredenciais(String caminhoArquivo) {
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists() && arquivo.isFile()) {
            try (Scanner leitor = new Scanner(arquivo)) {
                while (leitor.hasNextLine()) {
                    String linha = leitor.nextLine();
                    // Formato esperado: email;senha
                    String[] partes = linha.split(";");
                    if (partes.length == 2 && partes[0].equals(email) && partes[1].equals(senha)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
        }
        return false;
    }

    public void cadastrarUsuario() {
        System.out.println("##### Cadastro de Novo Usuário #####");
        System.out.print("Digite o nome: ");
        nome = scan.nextLine();
        System.out.print("Digite a matrícula: ");
        matricula = Integer.parseInt(scan.nextLine());
        System.out.print("Digite o e-mail: ");
        email = scan.nextLine();
        System.out.print("Digite a senha: ");
        senha = scan.nextLine();
        System.out.print("Informe o tipo de usuário (Professor/Aluno): ");
        String tipoUsuario = scan.nextLine();

        // Determinar o arquivo de destino
        String caminhoArquivo;
        if ("Professor".equalsIgnoreCase(tipoUsuario)) {
            caminhoArquivo = "Senhas/SenhasProfessores.txt";
        } else if ("Aluno".equalsIgnoreCase(tipoUsuario)) {
            caminhoArquivo = "Senhas/SenhasAlunos.txt";
        } else {
            System.out.println("Tipo de usuário inválido.");
            return;
        }

        // Salvar no arquivo
        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(email + ";" + senha + "\n");
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o usuário: " + e.getMessage());
        }
    }

}
