package Crud.repository;

import Crud.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.text.html.HTML.Tag.SELECT;

public class produtoRepository {

    //Private -> Só a essa classe acessa ela.
    //Static -> É quando ele pertence a essa classe não é um obj.
    //Final -> É por que é uma constante e por isso em maiusculo (URL,USER, PASSWORD)
    private static final String URL = "jdbc:h2:mem:loja;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public produtoRepository(){
        criarTabela();
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

    //Private -> Só essa classe acessa ela.
    //Connection -> É o metodo que conecta o banco de dados.
    //Throws SQLException -> É para caso não connecte ele manda um erro.
    //DriverManager -> Ele conectar com essa credencias (URL,USER, PASSWORD)
    private Connection connectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
