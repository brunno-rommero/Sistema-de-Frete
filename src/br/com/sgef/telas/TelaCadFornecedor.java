/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import br.com.sgef.dal.ModuloConexao;
import br.com.sgef.dao.FornecedorDAO;
import br.com.sgef.dao.VeiculoDAO;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.xml.stream.events.EndElement;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaCadFornecedor extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCadFornecedor
     */
    public TelaCadFornecedor() {
        initComponents();
        conexao = ModuloConexao.conector();

        tblFornecedor.getColumnModel().getColumn(0).setMaxWidth(40);
        tblFornecedor.getColumnModel().getColumn(1).setMaxWidth(200);
        tblFornecedor.getColumnModel().getColumn(2).setMaxWidth(200);
        tblFornecedor.getColumnModel().getColumn(3).setMaxWidth(200);

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void adicionar() {

        String sql = ("insert into tbl_fornecedor (fornecedor, endereco, telefone) values(?,?,?)");
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, codUser.getText());
            pst.setString(1, DescFornecedor.getText());
            pst.setString(2, EndFornecedor.getText());
            pst.setString(3, TelefoneFornecedor.getText());

            if ((DescFornecedor.getText().isEmpty()) || (EndFornecedor.getText().isEmpty()) || (TelefoneFornecedor.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Fornecedor Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    DescFornecedor.setText(null);
                    EndFornecedor.setText(null);
                    TelefoneFornecedor.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_fornecedor() {
        String sql = "select idFornecedor as Cód, fornecedor as Fornecedor, endereco as Endereço, telefone as Telefone from tbl_fornecedor where fornecedor like ?";

        try {

            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de texto para o --> ?
            //atemcao ao porcentagem % que é a continuação do SQL
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();

            //abaixo usa a biblioteca rs2xml 
            tblFornecedor.setModel(DbUtils.resultSetToTableModel(rs));

            tblFornecedor.getColumnModel().getColumn(0).setMaxWidth(40);
            tblFornecedor.getColumnModel().getColumn(1).setMaxWidth(200);
            tblFornecedor.getColumnModel().getColumn(2).setMaxWidth(200);
            tblFornecedor.getColumnModel().getColumn(3).setMaxWidth(200);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setar_campos() {
        int setar = tblFornecedor.getSelectedRow();

        CodFornecedor.setText(tblFornecedor.getModel().getValueAt(setar, 0).toString());
        DescFornecedor.setText(tblFornecedor.getModel().getValueAt(setar, 1).toString());
        EndFornecedor.setText(tblFornecedor.getModel().getValueAt(setar, 2).toString());
        TelefoneFornecedor.setText(tblFornecedor.getModel().getValueAt(setar, 3).toString());

        // desabilitar o btn Adicionar
        btnSave.setEnabled(false);
    }

    private void limpar() {
        CodFornecedor.setText(null);
        DescFornecedor.setText(null);
        EndFornecedor.setText(null);
        TelefoneFornecedor.setText(null);

        btnSave.setEnabled(true);
    }

    private void alterar() {
        String sql = "update tbl_fornecedor set fornecedor=?, endereco=?, telefone=? where idFornecedor=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, DescFornecedor.getText());
            pst.setString(2, EndFornecedor.getText());
            pst.setString(3, TelefoneFornecedor.getText());
            pst.setString(4, CodFornecedor.getText());

            if ((CodFornecedor.getText().isEmpty()) || (DescFornecedor.getText().isEmpty()) || (EndFornecedor.getText().isEmpty()) || (TelefoneFornecedor.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Fornecedor Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    CodFornecedor.setText(null);
                    DescFornecedor.setText(null);
                    EndFornecedor.setText(null);
                    TelefoneFornecedor.setText(null);

                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                    btnSave.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluir() {

        String sql = "delete from tbl_fornecedor where idFornecedor=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, CodFornecedor.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor Removido com Sucesso");
                // as linhas abaixo limpam os campos
                CodFornecedor.setText(null);
                DescFornecedor.setText(null);
                EndFornecedor.setText(null);
                TelefoneFornecedor.setText(null);

                //ativa o btn salvar
                btnSave.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não Excluido !!!");
                //limpa os campos
                CodFornecedor.setText(null);
                DescFornecedor.setText(null);
                EndFornecedor.setText(null);
                TelefoneFornecedor.setText(null);

                //ativa o btn salvar
                btnSave.setEnabled(true);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        CodFornecedor = new javax.swing.JTextField();
        DescFornecedor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        EndFornecedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TelefoneFornecedor = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("(##) #####-####");
            TelefoneFornecedor = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        btnExcluir = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFornecedor = new javax.swing.JTable();
        txtPesquisar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF - Cadastro de Fornecedor");

        jLabel1.setText("Cód.");

        jLabel2.setText("Fornecedor*");

        CodFornecedor.setEnabled(false);

        jLabel3.setText("Endereço*");

        jLabel4.setText("Telefone*");

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/delete.png"))); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/clean.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/saved.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        tblFornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Cód", "Fornecedor", "Endereço", "Telefone"
            }
        ));
        tblFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFornecedorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblFornecedor);

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Método de Pesquisa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                    .addComponent(txtPesquisar)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(EndFornecedor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(TelefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CodFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(DescFornecedor))))
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
                    .addComponent(CodFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DescFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EndFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TelefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(jButton3)
                    .addComponent(btnExcluir)
                    .addComponent(jButton2))
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        alterar();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_fornecedor();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFornecedorMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblFornecedorMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CodFornecedor;
    private javax.swing.JTextField DescFornecedor;
    private javax.swing.JTextField EndFornecedor;
    private javax.swing.JTextField TelefoneFornecedor;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblFornecedor;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
