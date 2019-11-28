/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import java.sql.*;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import br.com.sgef.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaCadRecDes extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCadRecDes
     */
    public TelaCadRecDes() {
        initComponents();
        conexao = ModuloConexao.conector();
        tipoDR.setSelectedItem(null);
        
    }
    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);        
    }
    
    private void adicionar() {
    
    String sql = ("insert into tbl_recdes (descDR, tipoDR) values(?,?)");
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, codUser.getText());
            pst.setString(1, descDR.getText());
            pst.setString(2, tipoDR.getSelectedItem().toString());
           
            if ((descDR.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                System.out.println(adicionado);
                if (adicionado >= 0) {
                    JOptionPane.showMessageDialog(null, " Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    descDR.setText(null);
                    
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //tipoDR.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void excluir() {
    
    String sql = "delete from tbl_recdes where idDR=?";
        try {
            pst = conexao.prepareStatement(sql);
                pst.setString(1, codDR.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Removido com Sucesso");
                    // as linhas abaixo limpam os campos
                    codDR.setText(null);
                    descDR.setText(null);
                    //ativa o btn salvar
                    btnSave.setEnabled(true);
                } else {
                JOptionPane.showMessageDialog(null, "Usuário não Excluido !!!");
                    //limpa os campos
                    codDR.setText(null);
                    descDR.setText(null);
                    //ativa o btn salvar
                    btnSave.setEnabled(true);
                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");
          
        }
    }
    
    private void pesquisar_usuario(){
        String sql = "select idDR as Cód, descDR as Descrição, tipoDR as Tipo from tbl_recdes where descDR like ?";

        try {
            
            pst=conexao.prepareStatement(sql);
            //passando o conteudo da caixa de texto para o --> ?
            //atemcao ao porcentagem % que é a continuação do SQL
            pst.setString(1, txtPesquisar.getText() + "%");
            rs=pst.executeQuery();
            
            //abaixo usa a biblioteca rs2xml 
            tblDR.setModel(DbUtils.resultSetToTableModel(rs));
            
            
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }  
    }
    
    public void setar_campos() {
        int setar = tblDR.getSelectedRow();

        codDR.setText(tblDR.getModel().getValueAt(setar, 0).toString());
        descDR.setText(tblDR.getModel().getValueAt(setar, 1).toString());
        tipoDR.setSelectedItem(tblDR.getModel().getValueAt(setar, 2).toString());
        
        // desabilitar o btn Adicionar
        btnSave.setEnabled(false);
    }
    
    private void alterar() {
        String sql = "update tbl_recdes set idDR=?, descDR=?, tipoDR=? where idDR=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codDR.getText());
            pst.setString(2, descDR.getText());
            pst.setString(3, tipoDR.getSelectedItem().toString());
            pst.setString(4, codDR.getText());
                     

            if ((descDR.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    codDR.setText(null);
                    descDR.setText(null);
                    tipoDR.setSelectedItem(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema-> comboBox
                    
                    btnSave.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void limpar(){
        codDR.setText(null);
        descDR.setText(null);
        tipoDR.setSelectedItem(null);
        
        
        btnSave.setEnabled(true);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        codDR = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        descDR = new javax.swing.JTextField();
        tipoDR = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDR = new javax.swing.JTable();
        btnLimpar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);

        jLabel1.setText("Cód");

        codDR.setEditable(false);
        codDR.setEnabled(false);

        jLabel2.setText("Descrição");

        tipoDR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Receita", "Despesa" }));

        tblDR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblDR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDRMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDR);

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnSave.setText("Salvar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Método de Pesquisa");

        btnExcluir.setText("Exluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(codDR, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(descDR))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tipoDR, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave))
                    .addComponent(txtPesquisar)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(72, 72, 72)
                                .addComponent(jLabel2))
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codDR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descDR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoDR, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave)
                    .addComponent(btnLimpar)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        limpar();
        
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        alterar();
       
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_usuario();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblDRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDRMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblDRMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField codDR;
    private javax.swing.JTextField descDR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDR;
    private javax.swing.JComboBox tipoDR;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
