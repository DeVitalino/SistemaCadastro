import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        byte opcoes;

        System.out.println("Olá, seja bem-vindo ao CadastroSimples!");

        Scanner menu = new Scanner(System.in);

        System.out.println("1 - Cadastrar Usuário \n" +
                "2 - Remover Usuário\n" +
                "3- Banco de usuário\n" +
                "4 - Encerrar");

        System.out.println("Selecione a opção desejada: ");
        opcoes = menu.nextByte();


        switch (opcoes) {

            case 1: Scanner cadastro = new Scanner(System.in);

                System.out.println("Digite seu nome e sobrenome: ");
                String nome = cadastro.nextLine();

                System.out.println("Digite seu telefone:  ");
                String telefone = cadastro.nextLine();

                System.out.println("Digite seu Email: ");
                String email = cadastro.nextLine();

                System.out.println("Suas informações estão corretas?\n" +
                        nome +", " + telefone + " e " + email);

                Scanner confirmacaoCadastro = new Scanner(System.in);
                String confirmacao = confirmacaoCadastro.nextLine();

                while (confirmacao.equalsIgnoreCase("Não")) {

                    System.out.println("Digite suas informações corretamente: ");

                    System.out.println("Nome: ");
                    nome = cadastro.nextLine();

                    System.out.println("Telefone: ");
                    telefone = cadastro.nextLine();

                    System.out.println("Email: ");
                    email = cadastro.nextLine();

                    System.out.println("Suas informações estão corretas?\n" +
                            nome +", " + telefone + " e " + email);

                    confirmacao = confirmacaoCadastro.nextLine();

                }

                System.out.println("Cadastro realizado com sucesso!");
                break;




        }

    }
}