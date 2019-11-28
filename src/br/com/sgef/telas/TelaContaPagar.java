/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import br.com.sgef.dal.ModuloConexao;
import br.com.sgef.model.Fornecedor;
import br.com.sgef.dao.FornecedorDAO;
import br.com.sgef.dao.VeiculoDAO;
import br.com.sgef.model.Veiculo;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaContaPagar extends javax.swing.JInternalFrame {

    public static TelaContaPagar contapagar;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaContaPagar
     */
    public TelaContaPagar() {
        initComponents();
        txtCodFornecedor.setVisible(false);
        txtCaminhao.setVisible(false);
        
        txtValor.setDocument(new NumeroDocument(8, 2));
        
        tblContasP.getColumnModel().getColumn(0).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(1).setMaxWidth(100);
        tblContasP.getColumnModel().getColumn(2).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(3).setMaxWidth(100);
        tblContasP.getColumnModel().getColumn(4).setMaxWidth(100);
        tblContasP.getColumnModel().getColumn(5).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(6).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(7).setMaxWidth(150);
        tblContasP.getColumnModel().getColumn(8).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(9).setMaxWidth(200);
        tblContasP.getColumnModel().getColumn(10).setMaxWidth(80);
        

        //txtFornecedor.setVisible(false);
        conexao = ModuloConexao.conector();
        FornecedorDAO Fdao = new FornecedorDAO();
        VeiculoDAO Vdao = new VeiculoDAO();

        for (Fornecedor f : Fdao.read()) {
            cboFornecedor.setSelectedItem(null);
            cboFornecedor.addItem(f);

        }
        for (Veiculo v : Vdao.read()) {
            cboCaminhao.setSelectedItem(null);
            cboCaminhao.addItem(v);

        }

    }

    public void PopularJTable1(String sql) throws SQLException {
        try {
            PreparedStatement banco = (PreparedStatement) conexao.prepareStatement(sql);

            banco.execute(); // cria o vetor

            ResultSet resultado = banco.executeQuery(sql);

            DefaultTableModel mode = (DefaultTableModel) tblContasP.getModel();
            mode.setNumRows(0);

            while (resultado.next()) {
                mode.addRow(new Object[]{
                    //retorna os dados da tabela do BD, cada campo e um coluna.
                    resultado.getString("idContasP"),
                    resultado.getString("descricao"),
                    resultado.getString("pago"),
                    resultado.getString("data_vencimento"),
                    resultado.getString("especie"),
                    resultado.getString("parcela"),
                    resultado.getString("idVeiculo"),
                    resultado.getString("B.modelo"),
                    resultado.getString("idFornecedor"),
                    resultado.getString("C.fornecedor"),
                    resultado.getString("valor")

                });
            }

        } catch (SQLException ex) {
            System.out.println("o erro foi " + ex);
        }

    }

    private void pesquisar_ContaPagar() {
        String sql = "SELECT idContasP as Cod, descricao as Descrição, pago as Pago, DATE_FORMAT( data_vencimento, \"%d/%m/%Y\" ), especie as Especie, parcela as Parc, A.idVeiculo as Cod, C.modelo as Caminhão, A.idFornecedor as Cod, B.fornecedor as Fornecedor, valor as Valor \n"
                + "FROM tbl_contaspagar AS A\n"
                + "			INNER JOIN tbl_fornecedor AS B \n"
                + "			on \n"
                + "			A.idFornecedor = B.idFornecedor \n"
                + "                     INNER JOIN veiculo AS C \n"
                + "                     on \n"
                + "                     A.idVeiculo = C.idVeiculo "
                + "			WHERE descricao  LIKE ?";

        try {

            pst = conexao.prepareStatement(sql);
            //passando o conteudo da caixa de texto para o --> ?
            //atemcao ao porcentagem % que é a continuação do SQL
            pst.setString(1, txtPesquisar.getText() + "%");

            rs = pst.executeQuery();

            //abaixo usa a biblioteca rs2xml 
            tblContasP.setModel(DbUtils.resultSetToTableModel(rs));

            tblContasP.getColumnModel().getColumn(0).setMaxWidth(40);
            tblContasP.getColumnModel().getColumn(1).setMaxWidth(100);
            tblContasP.getColumnModel().getColumn(2).setMaxWidth(40);
            tblContasP.getColumnModel().getColumn(3).setMaxWidth(90);
            tblContasP.getColumnModel().getColumn(4).setMaxWidth(100);
            tblContasP.getColumnModel().getColumn(5).setMaxWidth(40);
            tblContasP.getColumnModel().getColumn(6).setMaxWidth(40);
            tblContasP.getColumnModel().getColumn(7).setMaxWidth(150);
            tblContasP.getColumnModel().getColumn(8).setMaxWidth(40);
            tblContasP.getColumnModel().getColumn(9).setMaxWidth(200);
            tblContasP.getColumnModel().getColumn(10).setMaxWidth(80);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void excluir() {

        String sql = "delete from tbl_contaspagar where idContasP=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCod.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Removido com Sucesso");
                // as linhas abaixo limpam os campos
                txtCod.setText(null);
                txtDesc.setText(null);
                txtDataVenc.setText(null);
                txtEspecie.setText(null);
                txtValor.setText(null);

                //ativa o btn salvar
                btnSave.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Não Excluido !!!");
                //limpa os campos
                txtCod.setText(null);
                txtDesc.setText(null);
                txtDataVenc.setText(null);
                txtEspecie.setText(null);
                txtValor.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);

            }

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }
    }

    public void setar_campos() {
        int setar = tblContasP.getSelectedRow();

        cboFornecedor.setEditable(true);

        txtCod.setText(tblContasP.getModel().getValueAt(setar, 0).toString());
        txtDesc.setText(tblContasP.getModel().getValueAt(setar, 1).toString());
        cboPago.setSelectedItem(tblContasP.getModel().getValueAt(setar, 2));
        txtDataVenc.setText(tblContasP.getModel().getValueAt(setar, 3).toString());
        txtEspecie.setText(tblContasP.getModel().getValueAt(setar, 4).toString());
        cboParcelas.setSelectedItem(tblContasP.getModel().getValueAt(setar, 5).toString());
        txtCaminhao.setText(tblContasP.getModel().getValueAt(setar, 6).toString());
        cboCaminhao.setSelectedItem(tblContasP.getModel().getValueAt(setar, 7));
        txtCodFornecedor.setText(tblContasP.getModel().getValueAt(setar, 8).toString());
        cboFornecedor.setSelectedItem(tblContasP.getModel().getValueAt(setar, 9));
        txtValor.setText(tblContasP.getModel().getValueAt(setar, 10).toString());

        if (cboPago.getSelectedItem() == "Sim") {
            cboPago.setEnabled(false);
            btnAlterar.setEnabled(false);
        } else {
            cboPago.setEnabled(true);
            btnAlterar.setEnabled(true);
        }

        // desabilitar o btn Adicionar
        cboFornecedor.setEditable(false);
        cboFornecedor.setEnabled(false);
        cboCaminhao.setEditable(false);
        cboCaminhao.setEnabled(false);
        btnSave.setEnabled(false);
    }

    private void adicionar() {

        //Método Adicionar esta tudo Ok Esta levando os ID Para o banco de dados    
        String sql = ("insert into tbl_contaspagar (descricao, pago, data_vencimento, especie , parcela, idVeiculo, idFornecedor, valor) values(?,?,?,?,?,?,?,?)");

        try {

            Fornecedor fornecedor = (Fornecedor) cboFornecedor.getSelectedItem();
            Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();

            pst = conexao.prepareStatement(sql);

            //AQUI É FORMATADO A DATA DE DD/MM/YYYY PARA => YYYY/MM/DD
            //SE ALERTAR NA PST 2 
            String x = txtDataVenc.getText();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            Date data = formatador.parse(str);

            String valorPositivo = txtValor.getText().replace(".","").replace(",",".");

            //valorPositivo.replaceAll(",", ".");
            float aDouble = Float.parseFloat(valorPositivo);
            aDouble = -1 * aDouble;

            //System.out.println(str);
            //pst.setString(1, codUser.getText());
            pst.setString(1, txtDesc.getText());
            pst.setString(2, cboPago.getSelectedItem().toString());
            pst.setDate(3, new java.sql.Date(data.getTime()));
            pst.setString(4, txtEspecie.getText());
            pst.setString(5, cboParcelas.getSelectedItem().toString());
            pst.setInt(6, veiculo.getId());
            pst.setInt(7, fornecedor.getId());
            pst.setDouble(8, aDouble);

            // Capturar o Texto do Combobox "obs converter para String
            //pst.setString(4, comboboxPerfilUser.getSelectedItem().toString());
            // validação dos campos obrigatórios
            if ((txtDataVenc.getText().isEmpty()) || (txtDesc.getText().isEmpty()) || (txtValor.getText().isEmpty()) || (txtEspecie.getText().isEmpty())) {
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
                    txtDataVenc.setText(null);
                    txtEspecie.setText(null);
                    txtValor.setText(null);

                }
            }

        } catch (SQLException | ParseException | NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    private void alterar() {
        String sql = "update tbl_contaspagar set descricao=?, pago=?, data_vencimento=?, especie=?, valor=?, idFornecedor=? where idContasP=?";

        //System.out.println(cboFornecedor.getSelectedItem());
        try {

            pst = conexao.prepareStatement(sql);

            String x = txtDataVenc.getText();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            java.util.Date data = formatador.parse(str);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            x = format.format(data);
            //System.out.println(x);

            Fornecedor fornecedor = new Fornecedor();
            //Fornecedor fornecedor = (Fornecedor) cboFornecedor.getSelectedItem();
            String codigoF = txtCodFornecedor.getText();
            int codigoFF = Integer.parseInt(codigoF);
            pst.setString(1, txtDesc.getText());
            pst.setString(2, cboPago.getSelectedItem().toString());
            pst.setString(3, x);
            pst.setString(4, txtEspecie.getText());
            pst.setString(5, txtValor.getText().replace(".","").replace(",","."));
            pst.setString(6, txtCodFornecedor.getText());
            pst.setString(7, txtCod.getText());

            // }
            if ((txtDesc.getText().isEmpty()) || (txtDataVenc.getText().isEmpty()) || (txtValor.getText().isEmpty())) {
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
                    txtDataVenc.setText(null);
                    txtEspecie.setText(null);
                    txtPesquisar.setText(null);
                    txtValor.setText(null);

                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                    btnSave.setEnabled(true);
                    cboFornecedor.setEnabled(true);
                }
            }
        } catch (SQLException | ParseException | NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void limpar() {
        txtCod.setText(null);
        txtDataVenc.setText(null);
        txtDesc.setText(null);
        txtEspecie.setText(null);
        txtValor.setText(null);

        btnSave.setEnabled(true);
        cboFornecedor.setEnabled(true);
        cboCaminhao.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtDesc = new javax.swing.JTextField();
        txtCod = new javax.swing.JTextField();
        txtDataVenc = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtDataVenc = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        txtEspecie = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContasP = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        cboFornecedor = new javax.swing.JComboBox<Object>();
        txtPesquisar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cboPago = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtCodFornecedor = new javax.swing.JTextField();
        cboParcelas = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cboCaminhao = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txtCaminhao = new javax.swing.JTextField();
        txtDataP = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtDataP = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        btnBuscar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF - Contas a Pagar");
        setFocusCycleRoot(false);
        setFocusable(false);
        setRequestFocusEnabled(false);
        setVerifyInputWhenFocusTarget(false);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
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

        txtCod.setEnabled(false);

        txtEspecie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEspecieActionPerformed(evt);
            }
        });

        jLabel13.setText("Cód");

        jLabel14.setText("Descrição");

        jLabel16.setText("Data Venc.");

        jLabel17.setText("Espécie");

        jLabel18.setText("Valor");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/saved.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/clean.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/delete.png"))); // NOI18N
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        tblContasP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cod", "Descrição", "Pago", "Vencimento", "Especie", "Parc", "Cod", "Caminhão", "Cod", "Fornecedor", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblContasP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblContasPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblContasP);

        jLabel19.setText("Fornecedor");

        cboFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFornecedorActionPerformed(evt);
            }
        });

        txtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarActionPerformed(evt);
            }
        });
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 0, 0));
        jLabel15.setText("Método de Pesquisa");

        cboPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não", "Sim" }));
        cboPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPagoActionPerformed(evt);
            }
        });

        jLabel1.setText("Pago");

        txtCodFornecedor.setEnabled(false);
        txtCodFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFornecedorActionPerformed(evt);
            }
        });

        cboParcelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cboParcelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboParcelasActionPerformed(evt);
            }
        });

        jLabel2.setText("Parcelas");

        jLabel3.setText("Caminhão");

        txtCaminhao.setEnabled(false);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/search.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDataP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 227, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtPesquisar)
                    .addComponent(cboCaminhao, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboFornecedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cboParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtEspecie))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(cboPago, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(txtValor, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(txtDataVenc))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataVenc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(cboPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel17))
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtCodFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel15))
                                    .addComponent(jButton2)
                                    .addComponent(btnExcluir)
                                    .addComponent(btnAlterar)
                                    .addComponent(btnSave))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtDataP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar))
                .addGap(40, 40, 40))
        );

        setBounds(0, 0, 884, 504);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:

        String x = txtDataVenc.getText();
        int adicionado = 0;

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String str = x;
        java.util.Date data = null;
        try {
            data = formatador.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(TelaContaPagar.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        x = format.format(data);

        //System.out.println(data);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data); //aqui você usa sua variável que chamei de "minhaData"
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH);
        int ano = calendar.get(GregorianCalendar.YEAR);
        //mes=mes+1;
        //System.out.println("dia é: "+dia);
        //System.out.println("mes é: "+mes);
        //System.out.println("ano é: "+ano);

        //System.out.println("data:"+dia+"/"+mes+"/"+ano);
        String desc = txtDesc.getText();
        String especie = txtEspecie.getText();
        int qtdParc = Integer.parseInt(cboParcelas.getSelectedItem().toString());
        String valor = txtValor.getText().replace(".","").replace(",",".");
        double z = Double.parseDouble(valor);

        double valorParcela = z / qtdParc;

        for (int y = 1; y <= qtdParc; y++) {
            System.out.println("Parcela:" + y);
            mes = mes + 1;

            if (mes == 13) {
                ano = ano + 1;
                mes = 1;

                String sql = ("insert into tbl_contaspagar (descricao, pago, data_vencimento, especie, parcela, idVeiculo, idFornecedor, valor) values(?,?,?,?,?,?,?,?)");

                try {
                    Fornecedor fornecedor = (Fornecedor) cboFornecedor.getSelectedItem();
                    Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();
                    pst = conexao.prepareStatement(sql);
                    //pst.setString(1, codUser.getText());
                    pst.setString(1, txtDesc.getText());
                    pst.setString(2, cboPago.getSelectedItem().toString());
                    pst.setString(3, ano + "-" + mes + "-" + dia);
                    // Capturar o Texto do Combobox "obs converter para String
                    pst.setString(4, txtEspecie.getText());
                    pst.setInt(5, y);
                    pst.setInt(6, veiculo.getId());
                    pst.setInt(7, fornecedor.getId());
                    pst.setDouble(8, valorParcela);
                    adicionado = pst.executeUpdate();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                System.out.println("Descrrição: " + desc);
                System.out.println("Espécie: " + especie);
                System.out.println("data:" + dia + "/" + mes + "/" + ano);
                System.out.println("Valor da Parcela: " + valorParcela);
                System.out.println("\n");
                System.out.println("================================================");
                System.out.println("\n");

            } else {

                String sql = ("insert into tbl_contaspagar (descricao, pago, data_vencimento, especie, parcela, idVeiculo, idFornecedor, valor) values(?,?,?,?,?,?,?,?)");

                try {
                    Fornecedor fornecedor = (Fornecedor) cboFornecedor.getSelectedItem();
                    Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();
                    pst = conexao.prepareStatement(sql);
                    //pst.setString(1, codUser.getText());
                    pst.setString(1, txtDesc.getText());
                    pst.setString(2, cboPago.getSelectedItem().toString());
                    pst.setString(3, ano + "-" + mes + "-" + dia);
                    // Capturar o Texto do Combobox "obs converter para String
                    pst.setString(4, txtEspecie.getText());
                    pst.setInt(5, y);
                    pst.setInt(6, veiculo.getId());
                    pst.setInt(7, fornecedor.getId());
                    pst.setDouble(8, valorParcela);
                    adicionado = pst.executeUpdate();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                System.out.println("Descrrição: " + desc);
                System.out.println("Espécie: " + especie);
                System.out.println("data:" + dia + "/" + mes + "/" + ano);
                System.out.println("Valor da Parcela: " + valorParcela);
                System.out.println("\n");
                System.out.println("================================================");
                System.out.println("\n");
            }

        }
        if (adicionado > 0) {
            JOptionPane.showMessageDialog(null, "Adicionado com Sucesso !!!");
            // as linhas abaixo limpam os campos

            // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
            //cboUsoPerfil.setSelectedItem(null);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // TODO add your handling code here:

        if (cboPago.getSelectedItem() == "Sim") {

            String sqlCaixa = ("insert into tbl_caixa\n"
                    + "(descricao, tipo, dataa, valor, idContasP,idVeiculo) \n"
                    + "VALUES(?,?,?,?,?,?)");
            //aqui vai a lógica de quando mudar para recebido dar um insert na tbl caixa
            try {
                String tipo = "Saida";

                String x = txtDataVenc.getText();

                SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
                String str = x;
                java.util.Date data = null;
                try {
                    data = formatador.parse(str);
                } catch (ParseException ex) {
                    Logger.getLogger(TelaContaPagar.class.getName()).log(Level.SEVERE, null, ex);
                }

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                x = format.format(data);

                pst = conexao.prepareStatement(sqlCaixa);
                pst.setString(1, txtDesc.getText());
                pst.setString(2, tipo);
                pst.setString(3, x);
                pst.setString(4, "-" + txtValor.getText().replace(".","").replace(",","."));
                pst.setString(5, txtCod.getText());
                pst.setString(6, txtCaminhao.getText());

                pst.execute();
            } catch (SQLException ex) {
                Logger.getLogger(TelaFreteReceber.class.getName()).log(Level.SEVERE, null, ex);
            }

            cboPago.setEnabled(false);

        }

        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:  
        limpar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:

        if (cboPago.getSelectedItem() == "Sim") {

            String sqlCaixa = ("delete from tbl_caixa where idContasP=?");
            //aqui vai a lógica de quando mudar para recebido dar um insert na tbl caixa
            try {

                pst = conexao.prepareStatement(sqlCaixa);
                pst.setString(1, txtCod.getText());

                pst.execute();

            } catch (SQLException ex) {
                Logger.getLogger(TelaFreteReceber.class.getName()).log(Level.SEVERE, null, ex);
            }

            cboPago.setEnabled(false);

        }

        excluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_ContaPagar();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblContasPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblContasPMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblContasPMouseClicked

    private void cboPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPagoActionPerformed

    private void cboFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFornecedorActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboFornecedorActionPerformed

    private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisarActionPerformed

    private void txtEspecieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEspecieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEspecieActionPerformed

    private void cboParcelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboParcelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboParcelasActionPerformed

    private void txtCodFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFornecedorActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        Date data = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String x;
        x = format.format(data);
        txtDataP.setText(x);

        try {
            // TODO add your handling code here:
            SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
            String dat;
            dat = formatador.format(data);
            txtDataP.setText(dat);
            this.PopularJTable1("SELECT idContasP, descricao, pago, DATE_FORMAT(data_vencimento, \"%d/%m/%Y\") as data_vencimento, especie, parcela, \n"
                    + "                    A.idVeiculo, B.modelo, A.idFornecedor, C.fornecedor , valor \n"
                    + "                                    FROM tbl_contaspagar as A \n"
                    + "                                    INNER JOIN tbl_fornecedor AS C \n"
                    + "                                    on \n"
                    + "                                    A.idFornecedor = C.idFornecedor \n"
                    + "                                    INNER JOIN veiculo AS B \n"
                    + "                                    on \n"
                    + "                                    A.idVeiculo = B.idVeiculo \n"
                    + "                    WHERE data_vencimento='" + dat + "'");

        } catch (SQLException ex) {
            Logger.getLogger(TelaContaPagar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_formInternalFrameOpened

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        tblContasP.getColumnModel().getColumn(0).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(1).setMaxWidth(100);
        tblContasP.getColumnModel().getColumn(2).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(3).setMaxWidth(100);
        tblContasP.getColumnModel().getColumn(4).setMaxWidth(100);
        tblContasP.getColumnModel().getColumn(5).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(6).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(7).setMaxWidth(150);
        tblContasP.getColumnModel().getColumn(8).setMaxWidth(40);
        tblContasP.getColumnModel().getColumn(9).setMaxWidth(200);
        tblContasP.getColumnModel().getColumn(10).setMaxWidth(80);

        try {
            // TODO add your handling code here:

            String x = txtDataP.getText();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            java.util.Date data = formatador.parse(str);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            x = format.format(data);
            //System.out.println(x);

            this.PopularJTable1("SELECT idContasP, descricao, pago, DATE_FORMAT(data_vencimento, \"%d/%m/%Y\") as data_vencimento, especie, parcela, \n"
                    + "                    A.idVeiculo, B.modelo, A.idFornecedor, C.fornecedor , valor \n"
                    + "                                    FROM tbl_contaspagar as A \n"
                    + "                                    INNER JOIN tbl_fornecedor AS C \n"
                    + "                                    on \n"
                    + "                                    A.idFornecedor = C.idFornecedor \n"
                    + "                                    INNER JOIN veiculo AS B \n"
                    + "                                    on \n"
                    + "                                    A.idVeiculo = B.idVeiculo \n"
                    + "                    WHERE data_vencimento='" + x + "'");

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(TelaContaPagar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboCaminhao;
    private javax.swing.JComboBox<Object> cboFornecedor;
    private javax.swing.JComboBox cboPago;
    private javax.swing.JComboBox cboParcelas;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblContasP;
    private javax.swing.JTextField txtCaminhao;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodFornecedor;
    private javax.swing.JTextField txtDataP;
    private javax.swing.JTextField txtDataVenc;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtEspecie;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
