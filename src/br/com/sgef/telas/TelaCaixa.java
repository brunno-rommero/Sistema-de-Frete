/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import br.com.sgef.dal.ModuloConexao;
import br.com.sgef.dao.VeiculoDAO;
import br.com.sgef.model.Veiculo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaCaixa extends javax.swing.JInternalFrame {

    public static TelaCaixa caixa;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String dat;

    /**
     * Creates new form TelaCaixa
     */
    public TelaCaixa() {
        initComponents();
        conexao = ModuloConexao.conector();

        tblCaixa.getColumnModel().getColumn(0).setMaxWidth(40);
        tblCaixa.getColumnModel().getColumn(1).setMaxWidth(250);
        tblCaixa.getColumnModel().getColumn(2).setMaxWidth(150);
        tblCaixa.getColumnModel().getColumn(3).setMaxWidth(100);
        tblCaixa.getColumnModel().getColumn(4).setMaxWidth(40);
        tblCaixa.getColumnModel().getColumn(5).setMaxWidth(150);
        tblCaixa.getColumnModel().getColumn(6).setMaxWidth(100);

        txtValor.setDocument(new NumeroDocument(7, 2));

        cboCaminhao.setEditable(false);
        txtCaminhao.setVisible(false);

        VeiculoDAO Vdao = new VeiculoDAO();

        for (Veiculo v : Vdao.read()) {
            cboCaminhao.setSelectedItem(null);
            cboCaminhao.addItem(v);

        }
    }

    private void alterar() {
        String sql = "update tbl_caixa set  descricao=?, tipo=?, dataa=?, valor = REPLACE( REPLACE( ?, '.' ,'' ), ',', '.' ), idVeiculo=? where idCaixa=?";

        String x = txtData.getText();

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String str = x;
        java.util.Date data = null;
        try {
            data = formatador.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(TelaCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        x = format.format(data);
        //System.out.println(x);

        try {
            if (cboTipo.getSelectedItem() == "Saida") {

                pst = conexao.prepareStatement(sql);
                //pst.setString(1, codUser.getText());
                pst.setString(1, txtDesc.getText());
                pst.setString(2, (String) cboTipo.getSelectedItem());
                pst.setString(3, x);
                pst.setString(4, "-" + txtValor.getText());
                pst.setString(5, txtCaminhao.getText());
                pst.setString(6, txtCod.getText());

            } else if (cboTipo.getSelectedItem() == "Entrada") {
                pst = conexao.prepareStatement(sql);
                //pst.setString(1, codUser.getText());
                pst.setString(1, txtDesc.getText());
                pst.setString(2, (String) cboTipo.getSelectedItem());
                pst.setString(3, x);
                pst.setString(4, txtValor.getText());
                pst.setString(5, txtCaminhao.getText());
                pst.setString(6, txtCod.getText());

            }

            // validação dos campos obrigatórios
            if ((txtDesc.getText().isEmpty()) || (txtData.getText().isEmpty()) || (txtValor.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    txtCod.setText(null);
                    txtDesc.setText(null);
                    txtData.setText(null);
                    txtValor.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                }
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void limpar() {
        txtCod.setText(null);
        txtData.setText(null);
        txtDesc.setText(null);
        txtValor.setText(null);

        btnSave.setEnabled(true);
    }

    private void excluir() {

        String sql = "delete from tbl_caixa where idCaixa=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCod.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Usuário Removido com Sucesso");
                // as linhas abaixo limpam os campos
                txtCod.setText(null);
                txtData.setText(null);
                txtDesc.setText(null);
                txtValor.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não Excluido !!!");
                //limpa os campos
                txtCod.setText(null);
                txtData.setText(null);
                txtDesc.setText(null);
                txtValor.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);

            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }
    }

    private void adicionar() {

        String x = txtData.getText();

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String str = x;

        java.util.Date data = null;

        try {
            data = formatador.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(TelaRelCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        x = format.format(data);
        String val = txtValor.getText();
        String sql = ("INSERT INTO\n"
                + "   tbl_caixa\n"
                + "SET	\n"
                + "	descricao = ?,\n"
                + "	tipo = ?,\n"
                + "	dataa = ?,\n"
                + "     valor = REPLACE( REPLACE( ?, '.' ,'' ), ',', '.' ),\n"
                + "     idVeiculo = ?");
        try {

            if (cboTipo.getSelectedItem() == "Saida") {
                Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();

                pst = conexao.prepareStatement(sql);
                //pst.setString(1, codUser.getText());
                pst.setString(1, txtDesc.getText());
                pst.setString(2, (String) cboTipo.getSelectedItem());
                pst.setString(3, x);
                pst.setString(4, "-" + txtValor.getText());
                pst.setInt(5, veiculo.getId());

                // validação dos campos obrigatórios
                if ((txtDesc.getText().isEmpty()) || (txtData.getText().isEmpty()) || (txtValor.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

                } else {

                    // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                    // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                    int adicionado = pst.executeUpdate();
                    // a linha abaixo serve como entendimento da logica.
                    //System.out.println(adicionado);
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Adicionado com Sucesso !!!");
                        // as linhas abaixo limpam os campos
                        txtCod.setText(null);
                        txtDesc.setText(null);
                        txtData.setText(null);
                        txtValor.setText(null);
                        // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                        //cboUsoPerfil.setSelectedItem(null);

                    }
                }

            } else {

                Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();

                pst = conexao.prepareStatement(sql);
                //pst.setString(1, codUser.getText());
                pst.setString(1, txtDesc.getText());
                pst.setString(2, (String) cboTipo.getSelectedItem());
                pst.setString(3, x);
                pst.setString(4, txtValor.getText());
                pst.setInt(5, veiculo.getId());

                // validação dos campos obrigatórios
                if ((txtDesc.getText().isEmpty()) || (txtData.getText().isEmpty()) || (txtValor.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

                } else {

                    // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                    // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                    int adicionado = pst.executeUpdate();
                    // a linha abaixo serve como entendimento da logica.
                    //System.out.println(adicionado);
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Adicionado com Sucesso !!!");
                        // as linhas abaixo limpam os campos
                        txtCod.setText(null);
                        txtDesc.setText(null);
                        txtData.setText(null);
                        txtValor.setText(null);
                        // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                        //cboUsoPerfil.setSelectedItem(null);
                    }
                }
            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void PopularJTable(String sql) throws SQLException {
        try {
            PreparedStatement banco = (PreparedStatement) conexao.prepareStatement(sql);

            banco.execute(); // cria o vetor

            ResultSet resultado = banco.executeQuery(sql);

            DefaultTableModel model = (DefaultTableModel) tblCaixa.getModel();
            model.setNumRows(0);

            while (resultado.next()) {
                model.addRow(new Object[]{
                    //retorna os dados da tabela do BD, cada campo e um coluna.
                    resultado.getString("idCaixa"),
                    resultado.getString("descricao"),
                    resultado.getString("tipo"),
                    resultado.getString("dataa"),
                    resultado.getString("A.idVeiculo"),
                    resultado.getString("B.modelo"),
                    resultado.getString("valor")

                });
            }

        } catch (SQLException ex) {
            //System.out.println("o erro foi " + ex);
        }

    }

    private void saldo() {

        float total = 0;
        try {

            Statement stm = conexao.createStatement();

            ResultSet rs = stm.executeQuery("SELECT SUM(REPLACE(valor, ',', '.')) FROM tbl_caixa");
            while (rs.next()) {
                total = rs.getFloat(1);

                String tot = Float.toString(total);

                lbSaldo.setText(tot);

                if (total < 0) {
                    lbSaldo.setForeground(Color.red);
                } else {
                    lbSaldo.setForeground(Color.decode("#006400"));
                }

            }
        } catch (SQLException e) {
        }
    }

    public void setar_campos() {
        cboCaminhao.setEnabled(false);
        cboCaminhao.setEditable(true);

        int setar = tblCaixa.getSelectedRow();

        txtCod.setText(tblCaixa.getModel().getValueAt(setar, 0).toString());
        txtDesc.setText(tblCaixa.getModel().getValueAt(setar, 1).toString());
        cboTipo.setSelectedItem(tblCaixa.getModel().getValueAt(setar, 2).toString());
        txtData.setText(tblCaixa.getModel().getValueAt(setar, 3).toString());
        txtCaminhao.setText(tblCaixa.getModel().getValueAt(setar, 4).toString());
        cboCaminhao.setSelectedItem(tblCaixa.getModel().getValueAt(setar, 5));
        txtValor.setText(tblCaixa.getModel().getValueAt(setar, 6).toString());

        // desabilitar o btn Adicionar
        btnSave.setEnabled(false);
        cboCaminhao.setEditable(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lbSaldo = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        txtCod = new javax.swing.JTextField();
        txtDesc = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCaixa = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtData = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        jLabel19 = new javax.swing.JLabel();
        txtDataB = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtDataB = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        jButton1 = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        cboCaminhao = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtCaminhao = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF  - Caixa");
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
                formInternalFrameOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbSaldo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbSaldo.setForeground(new java.awt.Color(0, 204, 0));
        lbSaldo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSaldo.setText("Saldo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lbSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/clean.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/saved.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        txtCod.setEnabled(false);

        jLabel14.setText("Código");

        jLabel15.setText("Descrição");

        tblCaixa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Cod", "Descrição", "Tipo", "Data", "Cod", "Caminhão", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCaixaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCaixa);

        jLabel13.setText("Saldo Em Caixa");

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada", "Saida" }));

        jLabel16.setText("Valor");

        jLabel18.setText("Tipo");

        jLabel19.setText("Data");

        txtDataB.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataBActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/delete.png"))); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        cboCaminhao.setEditable(true);
        cboCaminhao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCaminhaoActionPerformed(evt);
            }
        });

        jLabel1.setText("Caminhão");

        txtCaminhao.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtDesc))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(88, 88, 88))
                                    .addComponent(txtValor)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 160, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDataB, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                    .addComponent(txtCaminhao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir)
                                .addGap(6, 6, 6)
                                .addComponent(btnAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cboCaminhao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel16)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnSave)
                                .addComponent(jButton1)
                                .addComponent(jButton2)
                                .addComponent(btnExcluir)
                                .addComponent(btnAlterar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDataB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        setBounds(0, 0, 841, 457);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        adicionar();
        saldo();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated

    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:  

        Date data = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String x;
        x = format.format(data);
        txtDataB.setText(x);

        try {
            // TODO add your handling code here:
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
            String dat;
            dat = formatador.format(data);
            txtDataB.setText(dat);

            this.PopularJTable("SELECT idCaixa, descricao, tipo, DATE_FORMAT( dataa, \"%d/%m/%Y\" ) as Dataa, A.idVeiculo, B.modelo, valor \n"
                    + "FROM tbl_caixa AS A\n"
                    + "INNER JOIN veiculo AS B \n"
                    + "on \n"
                    + "A.idVeiculo = B.idVeiculo\n"
                    + "where dataa='" + dat + "'");

        } catch (SQLException ex) {
            Logger.getLogger(TelaCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }
        saldo();
    }//GEN-LAST:event_formInternalFrameOpened

    private void tblCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCaixaMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblCaixaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        tblCaixa.getColumnModel().getColumn(0).setMaxWidth(40);
        tblCaixa.getColumnModel().getColumn(1).setMaxWidth(250);
        tblCaixa.getColumnModel().getColumn(2).setMaxWidth(150);
        tblCaixa.getColumnModel().getColumn(3).setMaxWidth(100);
        tblCaixa.getColumnModel().getColumn(4).setMaxWidth(40);
        tblCaixa.getColumnModel().getColumn(5).setMaxWidth(150);
        tblCaixa.getColumnModel().getColumn(6).setMaxWidth(100);

        try {
            // TODO add your handling code here:

            String x = txtDataB.getText();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            java.util.Date data = formatador.parse(str);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            x = format.format(data);
            //System.out.println(x);

            this.PopularJTable("SELECT idCaixa, descricao, tipo, DATE_FORMAT( dataa, \"%d/%m/%Y\" ) as Dataa, A.idVeiculo, B.modelo, valor \n"
                    + "FROM tbl_caixa AS A\n"
                    + "INNER JOIN veiculo AS B \n"
                    + "on \n"
                    + "A.idVeiculo = B.idVeiculo\n"
                    + "where dataa='" + x + "'");

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(TelaCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtDataBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataBActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        excluir();
        saldo();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:
        alterar();
        saldo();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void cboCaminhaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCaminhaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCaminhaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboCaminhao;
    private javax.swing.JComboBox cboTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbSaldo;
    private javax.swing.JTable tblCaixa;
    private javax.swing.JTextField txtCaminhao;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDataB;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
