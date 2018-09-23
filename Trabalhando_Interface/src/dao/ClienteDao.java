/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConectionFactory;
import model.Cliente;

/**
 *
 * @author JohnPeter
 */
public class ClienteDao {
    
    private Connection conecta;
    int atual = 0;

    public ClienteDao() {
        this.conecta = new ConectionFactory().conecta();
    }
    
    public void cadastrarCliente(Cliente obj){
    
        try{
            String cmdsql = "insert into cliente(cli_nome, cli_email, cli_telefone, cli_celular, cli_rua, "+
                    "cli_numero, cli_complemento, cli_bairro, cli_cep, cli_estado, cli_cidade) "+ 
                    "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getTelefone());
            stmt.setString(4, obj.getCelular());
            stmt.setString(5, obj.getRua());
            stmt.setString(6, obj.getNumero());
            stmt.setString(7, obj.getComplento());
            stmt.setString(8, obj.getBairro());
            stmt.setString(9, obj.getCep());
            stmt.setString(10, obj.getEstado());
            stmt.setString(11, obj.getCidade());          
            
            stmt.execute();
            stmt.close();            
        }catch (SQLException ex){
            throw new RuntimeException("Erro ao inserir cliente!");
        }    
    }
    
    private Cliente carregaDados(ResultSet rs, Statement stmt) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCodigo(rs.getInt("cli_codigo") );
        cliente.setNome( rs.getString("cli_nome") );
        cliente.setEmail( rs.getString("cli_email") );
        cliente.setTelefone( rs.getString("cli_telefone") );
        cliente.setCelular( rs.getString("cli_celular") );
        cliente.setRua( rs.getString("cli_rua") );
        cliente.setNumero( rs.getString("cli_numero") );
        cliente.setComplento( rs.getString("cli_complemento") );
        cliente.setBairro( rs.getString("cli_bairro") );
        cliente.setCep( rs.getString("cli_cep") );
        cliente.setEstado( rs.getString("cli_estado") );
        cliente.setCidade( rs.getString("cli_cidade") );
        
        stmt.close();
        return cliente;
    }
    
    public void alterarCliente(Cliente obj){
        try{
            String cmdsql = "update cliente set cli_nome=?, cli_email=?, cli_telefone=?, cli_celular=?, cli_rua=?, "+
                    "cli_numero=?, cli_complemento=?, cli_bairro=?, cli_cep=?, cli_estado=?, cli_cidade=? "+ 
                    "where cli_codigo=?";            
            
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getTelefone());
            stmt.setString(4, obj.getCelular());
            stmt.setString(5, obj.getRua());
            stmt.setString(6, obj.getNumero());
            stmt.setString(7, obj.getComplento());
            stmt.setString(8, obj.getBairro());
            stmt.setString(9, obj.getCep());
            stmt.setString(10, obj.getEstado());
            stmt.setString(11, obj.getCidade());
            stmt.setInt(12, obj.getCodigo());
            
            stmt.execute();
            stmt.close();
        
        }catch(SQLException erro){
            throw new RuntimeException(erro);
        }
    }
    
    public void excluirCliente(Cliente obj){
        try{
            String cmdsql = "delete from cliente where cli_codigo = ?";
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            stmt.setInt(1, obj.getCodigo());
            stmt.execute();
            
            stmt.close();
        
        }catch(SQLException erro){
            throw new RuntimeException(erro);
        }
    }
    
    public Cliente primeiroRegistro() {                //METODO QUE CARREGA O ULTIMO REGISTRO DO BD
        Cliente cliente = null;
        try {
            String Sql = "select * from cliente";
            Statement stmt = conecta.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);           
            ResultSet rs = stmt.executeQuery( Sql);
            rs.first();
            
            atual = rs.getConcurrency();
            cliente = carregaDados(rs, stmt);
        } catch (Exception e2) {
            System.out.println("ERRO: "+e2.getMessage());
        }  
        return cliente;
    }
    public Cliente anteriorRegistro() {                //METODO QUE CARREGA O ULTIMO REGISTRO DO BD
        Cliente cliente = null;
        try {
            String Sql = "select * from cliente";            
            Statement stmt = conecta.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
            ResultSet rs = stmt.executeQuery( Sql);
            
            rs.clearWarnings();
            if(!rs.first()){
                rs.previous();
            }
            
            cliente = carregaDados(rs, stmt);
        } catch (Exception e2) {
            System.out.println("ERRO: "+e2.getMessage());
        } 
        return cliente;
    }
    public Cliente proximoRegistro() {                //METODO QUE CARREGA O PROXIMO REGISTRO DO EM RELACAO A QUAL SE ESTA NO BD
        Cliente cliente = null;
        try {
            String Sql = "select * from cliente";
            Statement stmt = conecta.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);           
            ResultSet rs = stmt.executeQuery( Sql);
            
            if(!rs.isLast()){
                rs.next();
            }
            cliente = carregaDados(rs, stmt);        
        } catch (Exception e2) {
            System.out.println("ERRO" + e2.getMessage());
        } 
        return cliente;
    }
    public Cliente ultimoRegistro() {                //METODO QUE CARREGA O ULTIMO REGISTRO DO BD
        Cliente cliente = null;
        try {
            String Sql = "select * from cliente";
            Statement stmt = conecta.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);           
            ResultSet rs = stmt.executeQuery( Sql);
            rs.last();
            cliente = carregaDados(rs, stmt);
        } catch (Exception e2) {
            System.out.println("ERRO: "+e2.getMessage());
        }  
        return cliente;
    }
    
}
