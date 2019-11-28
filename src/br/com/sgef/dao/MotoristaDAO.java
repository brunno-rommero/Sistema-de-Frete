/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.dao;

import br.com.sgef.dal.ConnectionFactory;
import br.com.sgef.model.Motorista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class MotoristaDAO {

    public void create(Motorista m) {
        
        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tbl_motorista (nameMotorista,enderecoMotorista,numeroMotorista,bairroMotorista,compleMotorista,telefone,comissao)VALUES(?,?,?,?,?,?,?)");
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getEndMoto());
            stmt.setString(3, m.getTelefone());
            stmt.setString(4, m.getBairroMoto());
            stmt.setString(5, m.getCompleMotorista());
            stmt.setString(6, m.getTelefone());
            stmt.setInt(7, m.getComissao());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<Motorista> read() {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Motorista> motoristas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbl_motorista");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Motorista motorista = new Motorista();

                motorista.setId(rs.getInt("idMotorista"));
                motorista.setName(rs.getString("nameMotorista"));
                motorista.setEndMoto(rs.getString("enderecoMotorista"));
                motorista.setNumMoto(rs.getInt("numeroMotorista"));
                motorista.setBairroMoto(rs.getString("bairroMotorista"));
                motorista.setCompleMotorista(rs.getString("compleMotorista"));
                motorista.setTelefone(rs.getString("telefone"));
                motorista.setComissao(rs.getInt("comissao"));
                motoristas.add(motorista);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return motoristas;

    }
    public List<Motorista> readForDesc(String desc) {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Motorista> motoristas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tbl_motorista WHERE nameMotorista LIKE ?");
            stmt.setString(1, "%"+desc+"%");
            
            rs = stmt.executeQuery();

            while (rs.next()) {

                Motorista motorista = new Motorista();

                motorista.setId(rs.getInt("idMotorista"));
                motorista.setName(rs.getString("nameMotorista"));
                motorista.setEndMoto(rs.getString("enderecoMotorista"));
                motorista.setNumMoto(rs.getInt("numeroMotorista"));
                motorista.setBairroMoto(rs.getString("bairroMotorista"));
                motorista.setCompleMotorista(rs.getString("compleMotorista"));
                motorista.setTelefone(rs.getString("telefone"));
                motorista.setComissao(rs.getInt("comissao"));
                motoristas.add(motorista);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return motoristas;

    }

    public void update(Motorista m) {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tbl_motorista SET nameMotorista = ? ,enderecoMotorista = ?,numeroMotorista = ?,bairroMotorista = ?,compleMotorista = ?,telefone = ?,comissao = ? WHERE idMotorista = ?");
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getEndMoto());
            stmt.setInt(3, m.getNumMoto());
            stmt.setString(4, m.getBairroMoto());
            stmt.setString(5, m.getCompleMotorista());
            stmt.setString(6, m.getTelefone());
            stmt.setInt(7, m.getComissao());
            stmt.setInt(6, m.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    public void delete(Motorista m) {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tbl_motorista WHERE idMotorista = ?");
            stmt.setInt(1, m.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

}