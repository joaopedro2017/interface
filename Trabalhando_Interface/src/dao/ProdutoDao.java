/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jdbc.ConectionFactory;

/**
 *
 * @author alunoces
 */
public class ProdutoDao {
    
    private Connection conecta;    

    public ProdutoDao() {
        this.conecta = new ConectionFactory().conecta();
    }
    
    public void cadastrarProduto(Produto obj){
    
        try{
            String cmdsql = "insert into produto(pro_nome, pro_descricao, pro_preco) VALUES( ?, ?, ?)";
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getDescricao());
            stmt.setDouble(3, obj.getPreco());
            
            stmt.execute();
            stmt.close();            
        }catch (SQLException ex){
            throw new RuntimeException("Erro ao inserir produto!");
        }    
    }
    
    /**
     *
     * @return
     */
    public List<Produto>listarProdutos(){        
        try{
            
            List<Produto> lista = new ArrayList<Produto>();
            String Sql = "SELECT * FROM PRODUTO";
            PreparedStatement stmt = conecta.prepareStatement(Sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto c = new Produto();
                c.setId(rs.getInt("pro_id"));
                c.setNome(rs.getString("pro_nome"));
                c.setDescricao(rs.getString("pro_descricao"));
                c.setPreco(rs.getDouble("pro_preco"));                
                lista.add(c);
            }
            return lista;
            
        }catch(SQLException erro){
            throw new RuntimeException(erro);            
        }
    }
    
    public void excluirProduto(Produto obj){
        try{
            String cmdsql = "delete from produto where pro_id = ?";
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            stmt.setInt(1, obj.getId());
            stmt.execute();
            
            stmt.close();
        
        }catch(SQLException erro){
            throw new RuntimeException(erro);
        }
    }
    
    public void alterarProduto(Produto obj){        
        try{
            String cmdsql = "update produto set pro_nome=?, pro_descricao=?, pro_preco=? where pro_id = ?";
            
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getDescricao());
            stmt.setDouble(3, obj.getPreco());
            stmt.setInt(4, obj.getId());
            
            stmt.execute();
            stmt.close();
        
        }catch(SQLException erro){
            throw new RuntimeException(erro);
        }
    }    
    
}
