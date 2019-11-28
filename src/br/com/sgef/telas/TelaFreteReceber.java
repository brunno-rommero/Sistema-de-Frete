/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import br.com.sgef.dal.ModuloConexao;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaFreteReceber extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaFreteReceber
     */
    public TelaFreteReceber() {
        initComponents();
        conexao = ModuloConexao.conector();

        tblFreteReceber.getColumnModel().getColumn(0).setMaxWidth(40);
        tblFreteReceber.getColumnModel().getColumn(1).setMaxWidth(200);
        tblFreteReceber.getColumnModel().getColumn(2).setMaxWidth(70);
        tblFreteReceber.getColumnModel().getColumn(3).setMaxWidth(50);
        tblFreteReceber.getColumnModel().getColumn(4).setMaxWidth(150);
        tblFreteReceber.getColumnModel().getColumn(5).setMaxWidth(40);
        tblFreteReceber.getColumnModel().getColumn(6).setMaxWidth(150);
        tblFreteReceber.getColumnModel().getColumn(7).setMaxWidth(80);
        tblFreteReceber.getColumnModel().getColumn(8).setMaxWidth(150);
        tblFreteReceber.getColumnModel().getColumn(9).setMaxWidth(100);
        tblFreteReceber.getColumnModel().getColumn(10).setMaxWidth(100);

        txtValor.setDocument(new NumeroDocument(7, 2));

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void pesquisar_fretereceber() {
        String sql = ("select a.idFreteReceber as Cód,a.descricao as Descrição, \n"
                + "a.idViagem as Viagem,a.idVeiculo as Cód,b.modelo as Caminhão,a.idMotorista as Cód,c.nameMotorista as Motorista,\n"
                + "recebido as Recebido, percurso as Percurso, DATE_FORMAT( dataa, \"%d/%m/%Y\" ) AS Data, valor as Valor FROM tbl_fretereceber a\n"
                + "inner join veiculo b on a.idVeiculo = b.idVeiculo\n"
                + "inner join tbl_motorista c on a.idMotorista = c.idMotorista\n"
                + "WHERE descricao LIKE ?");

        try {

            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de texto para o --> ?
            //atemcao ao porcentagem % que é a continuação do SQL
            pst.setString(1, txtPesquisar1.getText() + "%");

            rs = pst.executeQuery();
            //abaixo usa a biblioteca rs2xml 
            tblFreteReceber.setModel(DbUtils.resultSetToTableModel(rs));

            tblFreteReceber.getColumnModel().getColumn(0).setMaxWidth(40);
            tblFreteReceber.getColumnModel().getColumn(1).setMaxWidth(200);
            tblFreteReceber.getColumnModel().getColumn(2).setMaxWidth(70);
            tblFreteReceber.getColumnModel().getColumn(3).setMaxWidth(50);
            tblFreteReceber.getColumnModel().getColumn(4).setMaxWidth(150);
            tblFreteReceber.getColumnModel().getColumn(5).setMaxWidth(40);
            tblFreteReceber.getColumnModel().getColumn(6).setMaxWidth(150);
            tblFreteReceber.getColumnModel().getColumn(7).setMaxWidth(80);
            tblFreteReceber.getColumnModel().getColumn(8).setMaxWidth(150);
            tblFreteReceber.getColumnModel().getColumn(9).setMaxWidth(100);
            tblFreteReceber.getColumnModel().getColumn(10).setMaxWidth(100);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setar_campos() {
        int setar = tblFreteReceber.getSelectedRow();

        txtCod.setText(tblFreteReceber.getModel().getValueAt(setar, 0).toString());
        txtDesc.setText(tblFreteReceber.getModel().getValueAt(setar, 1).toString());
        txtCodFec.setText(tblFreteReceber.getModel().getValueAt(setar, 2).toString());
        txtCodCaaminhao.setText(tblFreteReceber.getModel().getValueAt(setar, 3).toString());
        txtCaminhao.setText(tblFreteReceber.getModel().getValueAt(setar, 4).toString());
        txtCodMotorista.setText(tblFreteReceber.getModel().getValueAt(setar, 5).toString());
        txtMotorista.setText(tblFreteReceber.getModel().getValueAt(setar, 6).toString());
        cboRecebido.setSelectedItem(tblFreteReceber.getModel().getValueAt(setar, 7).toString());
        txtPercurso.setText(tblFreteReceber.getModel().getValueAt(setar, 8).toString());
        txtData.setText(tblFreteReceber.getModel().getValueAt(setar, 9).toString());
        txtValor.setText(tblFreteReceber.getModel().getValueAt(setar, 10).toString());
        if (cboRecebido.getSelectedItem() == "Sim") {
            cboRecebido.setEnabled(false);
            btnAlterar.setEnabled(false);
        } else {
            cboRecebido.setEnabled(true);
            btnAlterar.setEnabled(true);
        }

    }

    private void alterar() {
        String sql = "update tbl_fretereceber set descricao=?, idViagem=?, idVeiculo=?, idMotorista=?, recebido=?, percurso=?, dataa=?, valor = REPLACE( REPLACE( ?, '.' ,'' ), ',', '.' ) where idFreteReceber=?";
        try {
            String x = txtData.getText();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            java.util.Date data = formatador.parse(str);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            x = format.format(data);

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtDesc.getText());
            pst.setString(2, txtCodFec.getText());
            pst.setString(3, txtCodCaaminhao.getText());
            pst.setString(4, txtCodMotorista.getText());
            pst.setString(5, cboRecebido.getSelectedItem().toString());
            pst.setString(6, txtPercurso.getText());
            pst.setString(7, x);
            pst.setString(8, txtValor.getText().replace(',', '.'));
            pst.setString(9, txtCod.getText());
            // Capiturando dados combobox

            if ((txtDesc.getText().isEmpty()) || (txtCodFec.getText().isEmpty()) || (txtValor.getText().isEmpty())) {
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

                    txtCod.setText(null);
                    txtDesc.setText(null);
                    txtCodFec.setText(null);
                    txtCaminhao.setText(null);
                    txtMotorista.setText(null);
                    txtPercurso.setText(null);
                    txtData.setText(null);
                    txtValor.setText(null);

                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                    btnAlterar.setEnabled(false);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jLabel9 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        txtCod = new javax.swing.JTextField();
        txtDesc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        cboRecebido = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtCodFec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCaminhao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMotorista = new javax.swing.JTextField();
        txtPercurso = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPesquisar1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFreteReceber = new javax.swing.JTable();
        btnAlterar = new javax.swing.JButton();
        txtData = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCodCaaminhao = new javax.swing.JTextField();
        txtCodMotorista = new javax.swing.JTextField();

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Método de Pesquisa");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cód", "Nome", "Login", "Senha", "Perfil"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF - Frete a Receber");

        txtCod.setEnabled(false);

        txtDesc.setEnabled(false);

        jLabel1.setText("Cód.");

        jLabel2.setText("Descrição");

        jLabel3.setText("Valor");

        txtValor.setEnabled(false);
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });

        cboRecebido.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não", "Sim" }));
        cboRecebido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRecebidoActionPerformed(evt);
            }
        });

        jLabel4.setText("Recebido");

        txtCodFec.setEnabled(false);

        jLabel5.setText("Cód. Fec. Viagem");

        txtCaminhao.setEnabled(false);

        jLabel6.setText("Caminhão");

        jLabel7.setText("Motorista");

        jLabel8.setText("Percurso");

        txtMotorista.setEnabled(false);

        txtPercurso.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("Método de Pesquisa");

        txtPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisar1ActionPerformed(evt);
            }
        });
        txtPesquisar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisar1KeyReleased(evt);
            }
        });

        tblFreteReceber.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cód", "Descrição", "Viagem", "Cód", "Caminhão", "Cód", "Motorista", "Recebido", "Percurso", "Data", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFreteReceber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFreteReceberMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblFreteReceber);

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        txtData.setEnabled(false);

        jLabel11.setText("Data");

        txtCodCaaminhao.setEnabled(false);

        txtCodMotorista.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAlterar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPesquisar1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 250, Short.MAX_VALUE))
                            .addComponent(txtDesc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPercurso, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtCodFec, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(46, 46, 46))
                            .addComponent(cboRecebido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCodCaaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCaminhao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodFec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPercurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodCaaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCodMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(btnAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );

        setBounds(0, 0, 984, 469);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblUserMouseClicked

    private void txtPesquisar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisar1KeyReleased
        // TODO add your handling code here:
        pesquisar_fretereceber();

    }//GEN-LAST:event_txtPesquisar1KeyReleased

    private void tblFreteReceberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFreteReceberMouseClicked
        // TODO add your handling code here:
        setar_campos();

    }//GEN-LAST:event_tblFreteReceberMouseClicked

    private void cboRecebidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRecebidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboRecebidoActionPerformed

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtValorKeyReleased

    private void txtValorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtValorKeyPressed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:

        String x = txtData.getText();

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String str = x;
        java.util.Date data = null;
        try {
            data = formatador.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(TelaFreteReceber.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        x = format.format(data);

        if (cboRecebido.getSelectedItem() == "Sim") {

            String sqlCaixa = ("INSERT INTO\n"
                    + "   tbl_caixa\n"
                    + "     SET	\n"
                    + "     descricao = ?,\n"
                    + "     tipo = ?,\n"
                    + "     dataa = ?,\n"
                    + "     valor = REPLACE( REPLACE( ?, '.' ,'' ), ',', '.' ),\n"
                    + "     idViagem = ?,\n"
                    + "     idFreteReceber = ?,\n"
                    + "     idVeiculo = ?");

            //aqui vai a lógica de quando mudar para recebido dar um insert na tbl caixa
            try {
                String tipo = "Entrada";
                pst = conexao.prepareStatement(sqlCaixa);
                pst.setString(1, txtDesc.getText());
                pst.setString(2, tipo);
                pst.setString(3, x);
                pst.setString(4, txtValor.getText());
                pst.setString(5, txtCodFec.getText());
                pst.setString(6, txtCod.getText());
                pst.setString(7, txtCodCaaminhao.getText());
                pst.execute();
            } catch (SQLException ex) {
                Logger.getLogger(TelaFreteReceber.class.getName()).log(Level.SEVERE, null, ex);
            }

            cboRecebido.setEnabled(false);

        }

        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JComboBox cboRecebido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTable tblFreteReceber;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField txtCaminhao;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodCaaminhao;
    private javax.swing.JTextField txtCodFec;
    private javax.swing.JTextField txtCodMotorista;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtMotorista;
    private javax.swing.JTextField txtPercurso;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtPesquisar1;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
