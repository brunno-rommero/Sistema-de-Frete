/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import java.sql.*;
import br.com.sgef.dal.ModuloConexao;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaCadMotorista extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCadMotorista
     */
    public TelaCadMotorista() {
        initComponents();
        conexao = ModuloConexao.conector();

        tblMotorista.getColumnModel().getColumn(0).setMaxWidth(40);
        tblMotorista.getColumnModel().getColumn(1).setMaxWidth(200);
        tblMotorista.getColumnModel().getColumn(2).setMaxWidth(200);
        tblMotorista.getColumnModel().getColumn(3).setMaxWidth(100);
        tblMotorista.getColumnModel().getColumn(4).setMaxWidth(150);
        tblMotorista.getColumnModel().getColumn(5).setMaxWidth(150);
        tblMotorista.getColumnModel().getColumn(6).setMaxWidth(150);
        tblMotorista.getColumnModel().getColumn(7).setMaxWidth(100);

    }

    private void adicionar() {

        String sql = ("insert into tbl_motorista (nameMotorista, enderecoMotorista, numeroMotorista, bairroMotorista, compleMotorista, telefone,comissao) values(?,?,?,?,?,?,?)");
        try {
            pst = conexao.prepareStatement(sql);
            //pst.setString(1, codUser.getText());
            pst.setString(1, nameMotorista.getText());
            pst.setString(2, enderecoMotorista.getText());
            pst.setString(3, numeroMotorista.getText());
            pst.setString(4, bairroMotorista.getText());
            pst.setString(5, compMotorista.getText());
            pst.setString(6, telefone.getText());
            pst.setString(7, txtComissao.getText());
            // Capturar o Texto do Combobox "obs converter para String
            //pst.setString(4, comboboxPerfilUser.getSelectedItem().toString());
            // validação dos campos obrigatórios
            if ((nameMotorista.getText().isEmpty()) || (enderecoMotorista.getText().isEmpty()) || (bairroMotorista.getText().isEmpty()) || (telefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Motorista Adicionado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    nameMotorista.setText(null);
                    enderecoMotorista.setText(null);
                    numeroMotorista.setText(null);
                    bairroMotorista.setText(null);
                    compMotorista.setText(null);
                    telefone.setText(null);
                    txtComissao.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void excluir() {

        String sql = "delete from tbl_motorista where idMotorista=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codMotorista.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Motorista Removido com Sucesso");
                // as linhas abaixo limpam os campos
                codMotorista.setText(null);
                nameMotorista.setText(null);
                enderecoMotorista.setText(null);
                numeroMotorista.setText(null);
                bairroMotorista.setText(null);
                compMotorista.setText(null);
                telefone.setText(null);
                txtComissao.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Motorista não Excluido !!!");
                //limpa os campos
                codMotorista.setText(null);
                nameMotorista.setText(null);
                enderecoMotorista.setText(null);
                numeroMotorista.setText(null);
                bairroMotorista.setText(null);
                compMotorista.setText(null);
                telefone.setText(null);
                txtComissao.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }
    }

    private void alterar() {
        String sql = "update tbl_motorista set idMotorista=?, nameMotorista=?, enderecoMotorista=?, numeroMotorista=?, bairroMotorista=?, compleMotorista=?, telefone=?, comissao=? where idMotorista=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codMotorista.getText());
            pst.setString(2, nameMotorista.getText());
            pst.setString(3, enderecoMotorista.getText());
            pst.setString(4, numeroMotorista.getText());
            pst.setString(5, bairroMotorista.getText());
            pst.setString(6, compMotorista.getText());
            pst.setString(7, telefone.getText());
            pst.setString(8, txtComissao.getText());
            pst.setString(9, codMotorista.getText());

            if ((nameMotorista.getText().isEmpty()) || (enderecoMotorista.getText().isEmpty()) || (numeroMotorista.getText().isEmpty()) || (telefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Motorista Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    codMotorista.setText(null);
                    nameMotorista.setText(null);
                    enderecoMotorista.setText(null);
                    numeroMotorista.setText(null);
                    bairroMotorista.setText(null);
                    compMotorista.setText(null);
                    telefone.setText(null);
                    txtComissao.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                    btnSave.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_usuario() {
        String sql = "select idMotorista as Cód, nameMotorista as Nome, enderecoMotorista as Endereço, numeroMotorista as Numero, bairroMotorista as Bairro, compleMotorista as Complemento,telefone as Telefone,comissao as Comissao from tbl_motorista where nameMotorista like ?";

        try {

            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de texto para o --> ?
            //atemcao ao porcentagem % que é a continuação do SQL
            pst.setString(1, txtPesquisar.getText() + "%");
            rs = pst.executeQuery();

            //abaixo usa a biblioteca rs2xml 
            tblMotorista.setModel(DbUtils.resultSetToTableModel(rs));

            tblMotorista.getColumnModel().getColumn(0).setMaxWidth(40);
            tblMotorista.getColumnModel().getColumn(1).setMaxWidth(200);
            tblMotorista.getColumnModel().getColumn(2).setMaxWidth(200);
            tblMotorista.getColumnModel().getColumn(3).setMaxWidth(100);
            tblMotorista.getColumnModel().getColumn(4).setMaxWidth(150);
            tblMotorista.getColumnModel().getColumn(5).setMaxWidth(150);
            tblMotorista.getColumnModel().getColumn(6).setMaxWidth(150);
            tblMotorista.getColumnModel().getColumn(7).setMaxWidth(100);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setar_campos() {
        int setar = tblMotorista.getSelectedRow();

        codMotorista.setText(tblMotorista.getModel().getValueAt(setar, 0).toString());
        nameMotorista.setText(tblMotorista.getModel().getValueAt(setar, 1).toString());
        enderecoMotorista.setText(tblMotorista.getModel().getValueAt(setar, 2).toString());
        numeroMotorista.setText(tblMotorista.getModel().getValueAt(setar, 3).toString());
        bairroMotorista.setText(tblMotorista.getModel().getValueAt(setar, 4).toString());
        compMotorista.setText(tblMotorista.getModel().getValueAt(setar, 5).toString());
        telefone.setText(tblMotorista.getModel().getValueAt(setar, 6).toString());
        txtComissao.setText(tblMotorista.getModel().getValueAt(setar, 7).toString());

        // desabilitar o btn Adicionar
        btnSave.setEnabled(false);
    }

    private void limpar() {
        codMotorista.setText(null);
        nameMotorista.setText(null);
        enderecoMotorista.setText(null);
        numeroMotorista.setText(null);
        bairroMotorista.setText(null);
        compMotorista.setText(null);
        telefone.setText(null);
        txtComissao.setText(null);

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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        codMotorista = new javax.swing.JTextField();
        nameMotorista = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        enderecoMotorista = new javax.swing.JTextField();
        numeroMotorista = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bairroMotorista = new javax.swing.JTextField();
        compMotorista = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        telefone = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("(##) #####-####");
            telefone = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        btnSave = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMotorista = new javax.swing.JTable();
        txtPesquisar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnExcluir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtComissao = new javax.swing.JTextField();

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF  -  Cadastro de motorista");
        setName(""); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jLabel1.setText("Código");

        jLabel2.setText("Nome*");

        codMotorista.setEditable(false);
        codMotorista.setEnabled(false);

        jLabel3.setText("Endereço*");

        jLabel4.setText("N°");

        jLabel5.setText("Bairro*");

        jLabel6.setText("Complemento");

        jLabel7.setText("Telefone*");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/saved.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/clean.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tblMotorista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cód", "Nome", "Endereço", "Numero", "Bairro", "Complemento", "Telefone", "Comissão"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMotorista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMotoristaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblMotorista);

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Método de Pesquisa");

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/delete.png"))); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel9.setText("Comissão em %");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(compMotorista)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(codMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameMotorista)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(enderecoMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bairroMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numeroMotorista)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 76, Short.MAX_VALUE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPesquisar)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtComissao, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
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
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enderecoMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numeroMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bairroMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtComissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel8))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave)
                        .addComponent(jButton3)
                        .addComponent(btnExcluir)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_usuario();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblMotoristaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMotoristaMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblMotoristaMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        alterar();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:

    }//GEN-LAST:event_formInternalFrameActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bairroMotorista;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField codMotorista;
    private javax.swing.JTextField compMotorista;
    private javax.swing.JTextField enderecoMotorista;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField nameMotorista;
    private javax.swing.JTextField numeroMotorista;
    private javax.swing.JTable tblMotorista;
    private javax.swing.JTextField telefone;
    private javax.swing.JTextField txtComissao;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
