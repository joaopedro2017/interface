/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import jdbc.ConectionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.ConectionFactory;

/**
 *
 * @author alunoces
 */
public class UsuarioDao {
    
    private Connection conecta;

    public UsuarioDao() {
        this.conecta = new ConectionFactory().conecta();
    }    
    
    public boolean efetuarLogin(String email, String senha){        
        try {
            
            String cmdsql = "select * from usuario where usu_email=? and usu_senha=?";
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()){
                return true;
            }else{
                return false;
            }       
        }catch(SQLException e){
            throw new RuntimeException();
        }    
    }
    
    public String prioridadeAcesso(String email, String senha){        
        
        try {            
            String cmdsql = "select usu_tipo from usuario where usu_email=? and usu_senha=?";
            PreparedStatement stmt = conecta.prepareStatement(cmdsql);
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.first()){
                return rs.getString("usu_tipo");
            }else{
                return " ";
            }      
        }catch(SQLException e){
            throw new RuntimeException();
        }    
    }

    
}
