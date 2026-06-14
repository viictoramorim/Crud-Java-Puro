package Crud.repository;

import Crud.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    //Private -> Só a essa classe acessa ela.
    //Static -> É quando ele pertence a essa classe não é um obj.
    //Final -> É por que é uma constante e por isso em maiusculo (URL,USER, PASSWORD)
    private static final String URL = "jdbc:h2:./loja";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public ProdutoRepository() {
        iniciarConsole();
        criarTabela();
    }

    private void iniciarConsole() {
        try {
            org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (SQLException e) {
            System.out.println("Erro ao iniciar console: " + e.getMessage());
        }
    }

    private void criarTabela(){
        String sql = """
                CREATE TABLE IF NOT EXISTS produto (
                    id       INT AUTO_INCREMENT PRIMARY KEY,
                    nome     VARCHAR(100) NOT NULL,
                    preco    DOUBLE NOT NULL,
                    estoque  INT NOT NULL
                )  
                """;
        try (Connection conn = connectar(); Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e){
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    public void salvar(Produto produto){
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?,?,?)";
        try (Connection conn = connectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getEstoque());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public List<Produto> listarTodos(){
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = connectar(); Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                lista.add(new Produto (
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public Produto buscarPorId(int id){
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = connectar(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, estoque = ? WHERE id = ?";
        try (Connection conn = connectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getEstoque());
            ps.setInt(4, produto.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = connectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
        return false;
    }

    //Private -> Só essa classe acessa ela.
    //Connection -> É o metodo que conecta o banco de dados.
    //Throws SQLException -> É para caso não connecte ele manda um erro.
    //DriverManager -> Ele conectar com essa credencias (URL,USER, PASSWORD)
    private Connection connectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
