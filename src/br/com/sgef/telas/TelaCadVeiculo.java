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
public class TelaCadVeiculo extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCadVeiculo
     */
    public TelaCadVeiculo() {
        initComponents();
        conexao = ModuloConexao.conector();

        tblVeiculo.getColumnModel().getColumn(0).setMaxWidth(40);
        tblVeiculo.getColumnModel().getColumn(1).setMaxWidth(150);
        tblVeiculo.getColumnModel().getColumn(2).setMaxWidth(100);
        tblVeiculo.getColumnModel().getColumn(3).setMaxWidth(100);
        tblVeiculo.getColumnModel().getColumn(4).setMaxWidth(150);
        tblVeiculo.getColumnModel().getColumn(5).setMaxWidth(150);

    }

    private void adicionar() {

        String sql = ("insert into veiculo (modelo, ano, placa, fabricante, renavan) values(?,?,?,?,?)");
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, codUser.getText());
            pst.setString(1, modeloVeiculo.getText());
            pst.setString(2, anoVeiculo.getText());
            pst.setString(3, placaVeiculo.getText());
            pst.setString(4, fabricanteVeiculo.getText());
            pst.setString(5, renavanVeiculo.getText());
            // Capturar o Texto do Combobox "obs converter para String
            //pst.setString(4, comboboxPerfilUser.getSelectedItem().toString());
            // validação dos campos obrigatórios
            if ((modeloVeiculo.getText().isEmpty()) || (anoVeiculo.getText().isEmpty()) || (placaVeiculo.getText().isEmpty()) || (fabricanteVeiculo.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Veículo Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    modeloVeiculo.setText(null);
                    anoVeiculo.setText(null);
                    placaVeiculo.setText(null);
                    fabricanteVeiculo.setText(null);
                    renavanVeiculo.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluir() {

        String sql = "delete from veiculo where idVeiculo=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codVeiculo.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Veículo Removido com Sucesso");
                // as linhas abaixo limpam os campos
                codVeiculo.setText(null);
                modeloVeiculo.setText(null);
                anoVeiculo.setText(null);
                placaVeiculo.setText(null);
                fabricanteVeiculo.setText(null);
                renavanVeiculo.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Veículo não Excluido !!!");
                //limpa os campos
                codVeiculo.setText(null);
                modeloVeiculo.setText(null);
                anoVeiculo.setText(null);
                placaVeiculo.setText(null);
                fabricanteVeiculo.setText(null);
                renavanVeiculo.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }
    }

    private void pesquisar_veiculo() {
        String sql = "select idVeiculo as Cód, modelo as Modelo, ano as Ano, placa as Placa, fabricante as Fabricante, renavan as Renavan from veiculo where modelo like ?";

        try {

            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de texto para o --> ?
            //atemcao ao porcentagem % que é a continuação do SQL
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();

            //abaixo usa a biblioteca rs2xml 
            tblVeiculo.setModel(DbUtils.resultSetToTableModel(rs));

            tblVeiculo.getColumnModel().getColumn(0).setMaxWidth(40);
            tblVeiculo.getColumnModel().getColumn(1).setMaxWidth(150);
            tblVeiculo.getColumnModel().getColumn(2).setMaxWidth(100);
            tblVeiculo.getColumnModel().getColumn(3).setMaxWidth(100);
            tblVeiculo.getColumnModel().getColumn(4).setMaxWidth(150);
            tblVeiculo.getColumnModel().getColumn(5).setMaxWidth(150);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setar_campos() {
        int setar = tblVeiculo.getSelectedRow();

        codVeiculo.setText(tblVeiculo.getModel().getValueAt(setar, 0).toString());
        modeloVeiculo.setText(tblVeiculo.getModel().getValueAt(setar, 1).toString());
        anoVeiculo.setText(tblVeiculo.getModel().getValueAt(setar, 2).toString());
        placaVeiculo.setText(tblVeiculo.getModel().getValueAt(setar, 3).toString());
        fabricanteVeiculo.setText(tblVeiculo.getModel().getValueAt(setar, 4).toString());
        renavanVeiculo.setText(tblVeiculo.getModel().getValueAt(setar, 5).toString());
        // desabilitar o btn Adicionar
        btnSave.setEnabled(false);
    }

    private void alterar() {
        String sql = "update veiculo set idVeiculo=?, modelo=?, ano=?, placa=?, fabricante=?, renavan=? where idVeiculo=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codVeiculo.getText());
            pst.setString(2, modeloVeiculo.getText());
            pst.setString(3, anoVeiculo.getText());
            pst.setString(4, placaVeiculo.getText());
            pst.setString(5, fabricanteVeiculo.getText());
            pst.setString(6, renavanVeiculo.getText());
            pst.setString(7, codVeiculo.getText());

            if ((modeloVeiculo.getText().isEmpty()) || (anoVeiculo.getText().isEmpty()) || (placaVeiculo.getText().isEmpty()) || (fabricanteVeiculo.getText().isEmpty())) {
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
                    codVeiculo.setText(null);
                    modeloVeiculo.setText(null);
                    anoVeiculo.setText(null);
                    placaVeiculo.setText(null);
                    fabricanteVeiculo.setText(null);
                    renavanVeiculo.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                    btnSave.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        codVeiculo.setText(null);
        modeloVeiculo.setText(null);
        anoVeiculo.setText(null);
        placaVeiculo.setText(null);
        fabricanteVeiculo.setText(null);
        renavanVeiculo.setText(null);

        btnSave.setEnabled(true);
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codVeiculo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        modeloVeiculo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        anoVeiculo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        placaVeiculo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fabricanteVeiculo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        renavanVeiculo = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVeiculo = new javax.swing.JTable();
        txtPesquisar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF  -  Cadastro de veículo");
        setToolTipText("");

        codVeiculo.setEditable(false);
        codVeiculo.setEnabled(false);

        jLabel1.setText("Código");

        jLabel2.setText("Modelo");

        jLabel3.setText("Ano ");

        jLabel4.setText("Placa");

        jLabel5.setText("Fabricante");

        jLabel6.setText("Renavan");

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/clean.png"))); // NOI18N
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/saved.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        tblVeiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cód", "Modelo", "Ano", "Placa", "Fabricante", "Renavan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVeiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVeiculoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVeiculo);

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Método de Pesquisa");

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/delete.png"))); // NOI18N
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
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(placaVeiculo)
                                .addComponent(codVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel5))
                                        .addGap(0, 418, Short.MAX_VALUE))
                                    .addComponent(modeloVeiculo, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(anoVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fabricanteVeiculo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(renavanVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modeloVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(anoVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(placaVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fabricanteVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(renavanVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSave)
                                .addComponent(btnAlterar))
                            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(btnLimpar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_veiculo();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblVeiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVeiculoMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblVeiculoMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anoVeiculo;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField codVeiculo;
    private javax.swing.JTextField fabricanteVeiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField modeloVeiculo;
    private javax.swing.JTextField placaVeiculo;
    private javax.swing.JTextField renavanVeiculo;
    private javax.swing.JTable tblVeiculo;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
