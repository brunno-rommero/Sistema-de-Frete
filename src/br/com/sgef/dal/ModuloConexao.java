/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.dal;

//import com.sun.glass.ui.Cursor;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class ModuloConexao {
     //metodo responsavel por estabelecer a conexao com o banco
    public static Connection  conector(){
        java.sql.Connection conexao = null;
        String driver;
        driver = "com.mysql.jdbc.Driver";
        String url;
        url = "jdbc:mysql://localhost:3306/sgef";
        String user = "root";
        String password = "123";
        //estabelecendo a conexao com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user,password);
            return conexao;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Não foi possivel estabecer a conexão com o banco de dados");
            
            //System.out.println(e);
            return null;
           
        }
        
        
    }
     
}
