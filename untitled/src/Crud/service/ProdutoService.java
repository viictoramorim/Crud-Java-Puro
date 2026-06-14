package Crud.service;

import Crud.model.Produto;
import Crud.repository.ProdutoRepository;

import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService() {
        this.repository = new ProdutoRepository();
    }

    public void cadastrar(String nome, double preco, int estoque) {
        if (nome == null || nome.isBlank()) {
            System.out.println("Nome não pode ser vazio.");
            return;
        }
        if (preco < 0) {
            System.out.println("Preço não pode ser negativo.");
            return;
        }
        if (estoque < 0) {
            System.out.println("Estoque não pode ser negativo.");
            return;
        }
        repository.salvar(new Produto(0, nome, preco, estoque));
        System.out.println("Produto cadastrado com sucesso!");
    }

    public void listar() {
        List<Produto> produtos = repository.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("+-----+----------------------+------------+---------+");
        System.out.println("| ID  | Nome                 | Preço      | Estoque |");
        System.out.println("+-----+----------------------+------------+---------+");
        for (Produto p : produtos) {
            System.out.println(p);
        }
        System.out.println("+-----+----------------------+------------+---------+");
    }

    public void buscar(int id) {
        Produto p = repository.buscarPorId(id);
        if (p == null) {
            System.out.println("Produto não encontrado.");
        } else {
            System.out.println("+-----+----------------------+------------+---------+");
            System.out.println("| ID  | Nome                 | Preço      | Estoque |");
            System.out.println("+-----+----------------------+------------+---------+");
            System.out.println(p);
            System.out.println("+-----+----------------------+------------+---------+");
        }
    }

    public void atualizar(int id, String nome, double preco, int estoque) {
        if (repository.buscarPorId(id) == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        boolean ok = repository.atualizar(new Produto(id, nome, preco, estoque));
        System.out.println(ok ? "Produto atualizado com sucesso!" : "Falha ao atualizar.");
    }

    public void deletar(int id) {
        if (repository.buscarPorId(id) == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        boolean ok = repository.deletar(id);
        System.out.println(ok ? "Produto deletado com sucesso!" : "Falha ao deletar.");
    }
}
