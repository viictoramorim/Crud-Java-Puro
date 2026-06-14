package Crud;

import Crud.service.ProdutoService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProdutoService service = new ProdutoService();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n========== CRUD DE PRODUTOS ==========");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Listar todos os produtos");
            System.out.println("3. Buscar produto por ID");
            System.out.println("4. Atualizar produto");
            System.out.println("5. Deletar produto");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            opcao = lerInt(scanner);

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Preço: ");
                    double preco = lerDouble(scanner);
                    System.out.print("Estoque: ");
                    int estoque = lerInt(scanner);
                    service.cadastrar(nome, preco, estoque);
                }
                case 2 -> service.listar();
                case 3 -> {
                    System.out.print("ID: ");
                    int id = lerInt(scanner);
                    service.buscar(id);
                }
                case 4 -> {
                    System.out.print("ID do produto a atualizar: ");
                    int id = lerInt(scanner);
                    System.out.print("Novo nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Novo preço: ");
                    double preco = lerDouble(scanner);
                    System.out.print("Novo estoque: ");
                    int estoque = lerInt(scanner);
                    service.atualizar(id, nome, preco, estoque);
                }
                case 5 -> {
                    System.out.print("ID do produto a deletar: ");
                    int id = lerInt(scanner);
                    service.deletar(id);
                }
                case 0 -> System.out.println("Encerrando... Até mais!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static int lerInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static double lerDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Digite um valor válido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }
}
