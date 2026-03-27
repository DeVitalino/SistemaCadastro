import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class
Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Usuario> bancoUsuarios = new ArrayList<>();

        byte opcoes;
    while (true) {

        System.out.println("Olá, seja bem-vindo ao CadastroSimples!");

        System.out.println("1 - Cadastrar Usuário \n" +
                "2 - Remover Usuário\n" +
                "3- Banco de usuário\n" +
                "4 - Encerrar");

        System.out.println("Selecione a opção desejada: ");
        opcoes = sc.nextByte();
        sc.nextLine();

        switch (opcoes) {

            case 1:

                System.out.println("Digite seu nome e sobrenome: ");
                String nome = sc.nextLine();

                System.out.println("Digite seu telefone:  ");
                String telefone = sc.nextLine();

                System.out.println("Digite seu Email: ");
                String email = sc.nextLine();

                System.out.println("Suas informações estão corretas?\n" +
                        nome +", " + telefone + " e " + email);

                String confirmacao = sc.nextLine();

                while (confirmacao.equalsIgnoreCase("Não") ||
                        confirmacao.equalsIgnoreCase("não") ||
                        confirmacao.equalsIgnoreCase("nao") ||
                        confirmacao.equalsIgnoreCase("Nao")) {

                    System.out.println("Digite suas informações corretamente: ");

                    System.out.println("Nome: ");
                    nome = sc.nextLine();

                    System.out.println("Telefone: ");
                    telefone = sc.nextLine();

                    System.out.println("Email: ");
                    email = sc.nextLine();

                    System.out.println("Suas informações estão corretas?\n" +
                            nome +", " + telefone + " e " + email);

                    confirmacao = sc.nextLine();

                }

                Random gerador = new Random();
                int id = gerador.nextInt(900) + 100;

                Usuario novoUsuario = new Usuario(id, nome, telefone, email);
                bancoUsuarios.add(novoUsuario);
                System.out.println("Cadastro realizado! ID: " + id);
                break;

            case 2:

                System.out.println("Digite o ID do usuário: ");
                int idRemover = sc.nextInt();
                sc.nextLine();

                boolean encontrado = false;

                for (int i = 0; i < bancoUsuarios.size(); i++) {

                    if (bancoUsuarios.get(i).id == idRemover) {

                        bancoUsuarios.remove(i);
                        System.out.println("Usuário removido com sucesso!");

                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.out.println("Usuário não encontrado!");
                }

                break;

            case 3:

                if (bancoUsuarios.isEmpty()) {
                    System.out.println("Nenhum usuário cadastrado.");
                } else {

                    for (int i = 0; i < bancoUsuarios.size(); i++) {

                        Usuario u = bancoUsuarios.get(i);

                        System.out.println("ID: " + u.id);
                        System.out.println("Nome: " + u.nome);
                        System.out.println("Telefone: " + u.telefone);
                        System.out.println("Email: " + u.email);
                        System.out.println("------------------------");
                    }
                }

                break;

            case 4:

                System.out.println("Encerrando o sistema...");
                return;
        }

    }}

    static class Usuario {
        int id;
        String nome;
        String telefone;
        String email;


        public Usuario(int id, String nome, String telefone, String email) {
            this.id = id;
            this.nome = nome;
            this.telefone = telefone;
            this.email = email;
        }
    }

}