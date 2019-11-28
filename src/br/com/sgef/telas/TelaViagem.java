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
import br.com.sgef.model.Motorista;
import br.com.sgef.dao.MotoristaDAO;
import br.com.sgef.dao.VeiculoDAO;
import br.com.sgef.model.Veiculo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaViagem extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int lastId;

    /**
     * Creates new form TelaViagem
     */
    public TelaViagem() {
        initComponents();
        if (cboFreteReceber.getSelectedItem() == "Simadmin") {
            txtFreteReceber.setDocument(new NumeroDocument(8, 2));

        }
        txtAdiantamento.setDocument(new NumeroDocument(8, 2));
        txtFreteIda.setDocument(new NumeroDocument(8, 2));
        txtFreteVolta.setDocument(new NumeroDocument(8, 2));
        txtLiquidoAdiant.setDocument(new NumeroDocument(8, 2));
        txtLiquidoViagem.setDocument(new NumeroDocument(8, 2));
        txtDiesel.setDocument(new NumeroDocument(8, 2));
        txtArla.setDocument(new NumeroDocument(8, 2));
        txtPedagio.setDocument(new NumeroDocument(8, 2));
        txtComissao.setDocument(new NumeroDocument(8, 2));
        txtAcessorio.setDocument(new NumeroDocument(8, 2));
        txtEstacionamento.setDocument(new NumeroDocument(8, 2));
        txtChapa.setDocument(new NumeroDocument(8, 2));
        txtGorjeta.setDocument(new NumeroDocument(8, 2));
        txtOutro.setDocument(new NumeroDocument(8, 2));
        txtTotalEntrada.setDocument(new NumeroDocument(8, 2));
        txtTotalSaida.setDocument(new NumeroDocument(8, 2));

        conexao = ModuloConexao.conector();
        txtVeiculo.setVisible(false);
        txtMotorista.setVisible(false);

        VeiculoDAO Vdao = new VeiculoDAO();
        MotoristaDAO Mdao = new MotoristaDAO();

        for (Veiculo v : Vdao.read()) {
            cboCaminhao.setSelectedItem(null);
            cboCaminhao.addItem(v);

        }

        for (Motorista m : Mdao.read()) {
            cboMotorista.setSelectedItem(null);
            cboMotorista.addItem(m);
        }

    }

    private void excluir() {

        String sql = "delete from tbl_viagem where idViagem=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCod.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Removido com Sucesso");
                // as linhas abaixo limpam os campos
                txtCod.setText(null);
                txtCodV.setText(null);
                cboCaminhao.setSelectedItem(null);
                txtData.setText(null);
                cboMotorista.setSelectedItem(null);
                txtPercurso.setText(null);
                txtAdiantamento.setText(null);
                txtFreteIda.setText(null);
                txtFreteVolta.setText(null);
                txtTotalEntrada.setText(null);
                txtKmIda.setText(null);
                txtKmVolta.setText(null);
                txtQtdLitros.setText(null);
                txtMedia.setText(null);
                txtLiquidoAdiant.setText(null);
                txtDescFreteRec.setText(null);
                txtFreteReceber.setText(null);
                txtLiquidoViagem.setText(null);
                txtDiesel.setText(null);
                txtArla.setText(null);
                txtPedagio.setText(null);
                txtComissao.setText(null);
                txtAcessorio.setText(null);
                txtEstacionamento.setText(null);
                txtChapa.setText(null);
                txtGorjeta.setText(null);
                txtOutro.setText(null);
                txtTotalSaida.setText(null);
                txtKmPercorrido.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Não Excluido !!!");
                //limpa os campos
                txtCod.setText(null);
                txtCodV.setText(null);
                cboCaminhao.setSelectedItem(null);
                txtData.setText(null);
                cboMotorista.setSelectedItem(null);
                txtPercurso.setText(null);
                txtAdiantamento.setText(null);
                txtFreteIda.setText(null);
                txtFreteVolta.setText(null);
                txtTotalEntrada.setText(null);
                txtKmIda.setText(null);
                txtKmVolta.setText(null);
                txtQtdLitros.setText(null);
                txtMedia.setText(null);
                txtLiquidoAdiant.setText(null);
                txtDescFreteRec.setText(null);
                txtFreteReceber.setText(null);
                txtLiquidoViagem.setText(null);
                txtDiesel.setText(null);
                txtArla.setText(null);
                txtPedagio.setText(null);
                txtComissao.setText(null);
                txtAcessorio.setText(null);
                txtEstacionamento.setText(null);
                txtChapa.setText(null);
                txtGorjeta.setText(null);
                txtOutro.setText(null);
                txtTotalSaida.setText(null);
                txtKmPercorrido.setText(null);
                //ativa o btn salvar
                btnSave.setEnabled(true);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }
    }

    private void pesquisar_viagem() {

        String num_viagem = JOptionPane.showInputDialog(null, "Digite o número do fechamento de Viagem ? ");
        String sql = "SELECT b.idViagem, b.CodViagemCam, a.modelo, b.dataa, \n"
                + "(SELECT nameMotorista FROM tbl_motorista INNER JOIN tbl_viagem ON tbl_motorista.idMotorista=tbl_viagem.idMotorista WHERE idViagem=" + num_viagem + ")\n"
                + ",b.percurso,b.adiantamento,b.freteIda,b.freteVolta,b.totalEntrada,b.kmIda,b.kmVolta,b.qtdLitros,\n"
                + "b.media,b.kmPercorrido,b.liquidoComAdiantamento,b.cbofretereceber, b.descFreteReceber ,b.freteReceber,b.liquidoViagem,b.oleoDiesel,b.arla,b.pedagio,\n"
                + "b.comissao,b.acessorio,b.estacionamento,b.chapa,b.gorjeta,b.outrasDesp,b.totalSaida,a.idVeiculo,b.idMotorista\n"
                + " FROM veiculo as A INNER JOIN tbl_viagem as B on a.idVeiculo = b.idVeiculo WHERE idViagem=" + num_viagem;

        try {

            pst = conexao.prepareStatement(sql);
            //pst.setString(1, txtCod.getText().toString());
            rs = pst.executeQuery();

            //Aqui vamos formatar a data de yyyy-mm-dd para => dd/mm/yyyy
            //String x = tblContasP.getModel().getValueAt(setar, 2).toString();
            if (rs.next()) {

                //Converte yyyy-mm-dd para dd/mm/yyyy 
                String dat = rs.getString(4);
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date d1 = null;
                try {
                    d1 = formatador.parse(dat);

                } catch (ParseException ex) {
                    System.out.println(ex);
                }
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                dat = format.format(d1);

                //fazer inner join para trazer nomeMotorista e modelo Veiculo//
                txtCod.setText(rs.getString(1));
                txtCodV.setText(rs.getString(2));
                //cboCaminhao.setSelectedItem(rs.getString(2));
                cboCaminhao.getModel().setSelectedItem(rs.getString(3));
                txtData.setText(dat);
                cboMotorista.getModel().setSelectedItem(rs.getString(5));
                txtPercurso.setText(rs.getString(6));
                txtAdiantamento.setText(rs.getString(7));
                txtFreteIda.setText(rs.getString(8));
                txtFreteVolta.setText(rs.getString(9));
                txtTotalEntrada.setText(rs.getString(10));
                txtKmIda.setText(rs.getString(11));
                txtKmVolta.setText(rs.getString(12));
                txtQtdLitros.setText(rs.getString(13));
                txtMedia.setText(rs.getString(14));
                txtKmPercorrido.setText(rs.getString(15));
                txtLiquidoAdiant.setText(rs.getString(16));
                cboFreteReceber.setSelectedItem(rs.getString(17));
                txtDescFreteRec.setText(rs.getString(18));
                txtFreteReceber.setText(rs.getString(19));
                txtLiquidoViagem.setText(rs.getString(20));
                txtDiesel.setText(rs.getString(21));
                txtArla.setText(rs.getString(22));
                txtPedagio.setText(rs.getString(23));
                txtComissao.setText(rs.getString(24));
                txtAcessorio.setText(rs.getString(25));
                txtEstacionamento.setText(rs.getString(26));
                txtChapa.setText(rs.getString(27));
                txtGorjeta.setText(rs.getString(28));
                txtOutro.setText(rs.getString(29));
                txtTotalSaida.setText(rs.getString(30));
                txtVeiculo.setText(rs.getString(31));
                txtMotorista.setText(rs.getString(32));

                btnSave.setEnabled(false);
                cboCaminhao.setEnabled(false);
                cboMotorista.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Fechamento de Viagem não encontrada");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void alterar() {

        String sql = "update tbl_viagem set idViagem=?, CodViagemCam=?, idVeiculo=?, dataa=?, idMotorista=?,"
                + " percurso=?, adiantamento=?, freteIda=?, freteVolta=?, totalEntrada=?,kmIda=?,"
                + "kmVolta=?,qtdLitros=?,media=?,kmPercorrido=?,liquidoComAdiantamento=?, cbofretereceber=?, descFreteReceber=?, freteReceber=?,"
                + "liquidoViagem=?,oleoDiesel=?,arla=?,pedagio=?, comissao=?, acessorio=?, estacionamento=?,"
                + " chapa=?, gorjeta=?, outrasDesp=?, totalSaida=?  where idViagem=?";
        try {

            Veiculo veiculo = new Veiculo();
            Motorista motorista = new Motorista();

            //System.out.println(cboCaminhao.getSelectedItem());
            pst = conexao.prepareStatement(sql);
            //AQUI É FORMATADO A DATA DE DD/MM/YYYY PARA => YYYY/MM/DD
            //SE ALERTAR NA PST 2 
            String x = txtData.getText();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            java.util.Date data = formatador.parse(str);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            x = format.format(data);
            //System.out.println(x);

            pst.setString(1, txtCod.getText());
            pst.setString(2, txtCodV.getText());
            pst.setString(3, txtVeiculo.getText());
            pst.setString(4, x);//Data
            pst.setString(5, txtMotorista.getText());
            //System.out.println(veiculo.getId());
            pst.setString(6, txtPercurso.getText());
            pst.setString(7, txtAdiantamento.getText().replace(',', '.'));
            pst.setString(8, txtFreteIda.getText().replace(',', '.'));
            pst.setString(9, txtFreteVolta.getText().replace(',', '.'));
            pst.setString(10, txtTotalEntrada.getText().replace(',', '.'));
            pst.setString(11, txtKmIda.getText());
            pst.setString(12, txtKmVolta.getText());
            pst.setString(13, txtQtdLitros.getText());
            pst.setString(14, txtMedia.getText());
            pst.setString(15, txtKmPercorrido.getText());
            pst.setString(16, txtLiquidoAdiant.getText().replace(',', '.'));
            pst.setString(17, cboFreteReceber.getSelectedItem().toString());
            pst.setString(18, txtDescFreteRec.getText());
            pst.setString(19, txtFreteReceber.getText().replace(',', '.'));
            pst.setString(20, txtLiquidoViagem.getText().replace(',', '.'));
            pst.setString(21, txtDiesel.getText().replace(',', '.'));
            pst.setString(22, txtArla.getText().replace(',', '.'));
            pst.setString(23, txtPedagio.getText().replace(',', '.'));
            pst.setString(24, txtComissao.getText().replace(',', '.'));
            pst.setString(25, txtAcessorio.getText().replace(',', '.'));
            pst.setString(26, txtEstacionamento.getText().replace(',', '.'));
            pst.setString(27, txtChapa.getText().replace(',', '.'));
            pst.setString(28, txtGorjeta.getText().replace(',', '.'));
            pst.setString(29, txtOutro.getText().replace(',', '.'));
            pst.setString(30, txtTotalSaida.getText().replace(',', '.'));
            pst.setString(31, txtCod.getText());

            if ((txtData.getText().isEmpty()) || (txtPercurso.getText().isEmpty()) || (txtFreteIda.getText().isEmpty()) || (txtFreteVolta.getText().isEmpty()) || (txtLiquidoViagem.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a alteração de dados na tabel
                int adicionado = pst.executeUpdate();

                //aqui entra a alteração dos forms dependentes
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Fechamento de Viagem Alterado com Sucesso !!!");
                    // as linhas abaixo limpam os campos
                    txtCod.setText(null);
                    txtCodV.setText(null);
                    cboCaminhao.setSelectedItem(null);
                    txtData.setText(null);
                    cboMotorista.setSelectedItem(null);
                    txtPercurso.setText(null);
                    txtAdiantamento.setText(null);
                    txtFreteIda.setText(null);
                    txtFreteVolta.setText(null);
                    txtTotalEntrada.setText(null);
                    txtKmIda.setText(null);
                    txtKmVolta.setText(null);
                    txtQtdLitros.setText(null);
                    txtMedia.setText(null);
                    txtLiquidoAdiant.setText(null);
                    txtFreteReceber.setText(null);
                    txtLiquidoViagem.setText(null);
                    txtDescFreteRec.setText(null);
                    txtDiesel.setText(null);
                    txtArla.setText(null);
                    txtPedagio.setText(null);
                    txtComissao.setText(null);
                    txtAcessorio.setText(null);
                    txtEstacionamento.setText(null);
                    txtChapa.setText(null);
                    txtGorjeta.setText(null);
                    txtOutro.setText(null);
                    txtTotalSaida.setText(null);
                    txtKmPercorrido.setText(null);
                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);

                    btnSave.setEnabled(true);
                    cboCaminhao.setEnabled(true);
                    cboMotorista.setEnabled(true);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void cboFreteRec() {

        if (cboFreteReceber.getSelectedItem() == "Não") {

            txtDescFreteRec.setEnabled(false);
            txtFreteReceber.setEnabled(false);
        } else {
            txtDescFreteRec.setEnabled(true);
            txtFreteReceber.setEnabled(true);
        }

    }

    public void gerarRelatorio() {

        int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a impressão ?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // abrir relatorio 
            try {
                String Codigo = txtCod.getText();
                /* HashMap de parametros utilizados no relatório. Sempre instanciados */
                Map parameters = new HashMap();
                //Passa as datas como parâmetro para aparecer no relatório
                parameters.put("CodViagem", Codigo);//Aqui vc passa os parâmetros para um hashmap, que será enviado para o relatório

                JasperPrint print = JasperFillManager.fillReport("C:/reports/FechamentoViagem.jasper", parameters, conexao);
                JasperViewer.viewReport(print, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    private void adicionar() {

        //Método Adicionar esta tudo Ok Esta levando os ID Para o banco de dados    
        String sql = ("insert into tbl_viagem (CodViagemCam, idVeiculo, dataa, idMotorista, percurso, adiantamento, freteIda, freteVolta, totalEntrada, kmIda, kmVolta, qtdLitros, media, kmPercorrido, liquidoComAdiantamento, cbofretereceber, descFreteReceber, freteReceber, liquidoViagem, oleoDiesel, arla, pedagio, comissao, acessorio, estacionamento, chapa, gorjeta, outrasDesp, totalSaida) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        try {
            Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();
            Motorista motorista = (Motorista) cboMotorista.getSelectedItem();

            pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //AQUI É FORMATADO A DATA DE DD/MM/YYYY PARA => YYYY/MM/DD
            //SE ALERTAR NA PST 2 
            String x = txtData.getText();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String str = x;
            java.util.Date data = formatador.parse(str);

            //pst.setString(1, codUser.getText());
            pst.setString(1, txtCodV.getText());
            pst.setInt(2, veiculo.getId());
            pst.setDate(3, new java.sql.Date(data.getTime()));
            pst.setInt(4, motorista.getId());
            pst.setString(5, txtPercurso.getText());
            pst.setString(6, txtAdiantamento.getText().replace(',', '.'));
            pst.setString(7, txtFreteIda.getText().replace(',', '.'));
            pst.setString(8, txtFreteVolta.getText().replace(',', '.'));
            pst.setString(9, txtTotalEntrada.getText().replace(',', '.'));
            pst.setString(10, txtKmIda.getText());
            pst.setString(11, txtKmVolta.getText());
            pst.setString(12, txtQtdLitros.getText());
            pst.setString(13, txtMedia.getText());
            pst.setString(14, txtKmPercorrido.getText());
            pst.setString(15, txtLiquidoAdiant.getText().replace(',', '.'));
            pst.setString(16, cboFreteReceber.getSelectedItem().toString());
            pst.setString(17, txtDescFreteRec.getText());
            pst.setString(18, txtFreteReceber.getText().replace(',', '.'));
            pst.setString(19, txtLiquidoViagem.getText().replace(',', '.'));
            pst.setString(20, txtDiesel.getText().replace(',', '.'));
            pst.setString(21, txtArla.getText().replace(',', '.'));
            pst.setString(22, txtPedagio.getText().replace(',', '.'));
            pst.setString(23, txtComissao.getText().replace(',', '.'));
            pst.setString(24, txtAcessorio.getText().replace(',', '.'));
            pst.setString(25, txtEstacionamento.getText().replace(',', '.'));
            pst.setString(26, txtChapa.getText().replace(',', '.'));
            pst.setString(27, txtGorjeta.getText().replace(',', '.'));
            pst.setString(28, txtOutro.getText().replace(',', '.'));
            pst.setString(29, txtTotalSaida.getText().replace(',', '.'));

            // Capturar o Texto do Combobox "obs converter para String
            //pst.setString(4, comboboxPerfilUser.getSelectedItem().toString());
            // validação dos campos obrigatórios
            if ((txtPercurso.getText().isEmpty()) || (txtFreteIda.getText().isEmpty()) || (txtData.getText().isEmpty()) || (txtAdiantamento.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

            } else {

                // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                // a estrutura abaixo é usada para confirmar a inserção de dados na tabel
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve como entendimento da logica.
                //System.out.println(adicionado);

                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    lastId = rs.getInt(1);
                    System.out.println(lastId);
                    if (cboFreteReceber.getSelectedItem() == "Sim") {

                        String sqlFreteRececber = ("insert into tbl_fretereceber\n"
                                + "(descricao, idViagem, idVeiculo, idMotorista, recebido, percurso, dataa, valor) \n"
                                + "VALUES(?,?,?,?,?,?,?,?)");
                        try {
                            String recebido = "Não";

                            pst = conexao.prepareStatement(sqlFreteRececber);
                            pst.setString(1, txtDescFreteRec.getText());
                            pst.setInt(2, lastId);
                            pst.setInt(3, veiculo.getId());
                            pst.setInt(4, motorista.getId());
                            pst.setString(5, recebido);
                            pst.setString(6, txtPercurso.getText());
                            pst.setDate(7, new java.sql.Date(data.getTime()));
                            pst.setString(8, txtFreteReceber.getText());

                            //pst.setString(6, txtCod.getText());
                            pst.execute();
                        } catch (SQLException ex) {
                            Logger.getLogger(TelaFreteReceber.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                    //===========================aqui começaa
                    String sqlCaixa = ("insert into tbl_caixa\n"
                            + "(descricao, tipo, dataa, valor, idViagem, idVeiculo) \n"
                            + "VALUES(?,?,?,?,?,?)");
                    //aqui vai a lógica de quando mudar para recebido dar um insert na tbl caixa

                    try {
                        data = formatador.parse(str);
                    } catch (ParseException ex) {
                        Logger.getLogger(TelaViagem.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {

                        String tipo = "Entrada";
                        pst = conexao.prepareStatement(sqlCaixa);
                        pst.setString(1, txtPercurso.getText());
                        pst.setString(2, tipo);
                        pst.setDate(3, new java.sql.Date(data.getTime()));
                        pst.setString(4, txtLiquidoViagem.getText().replace(',', '.'));
                        pst.setInt(5, lastId);
                        pst.setInt(6, veiculo.getId());

                        //pst.setString(6, txtCod.getText());
                        pst.execute();
                    } catch (SQLException ex) {
                        Logger.getLogger(TelaFreteReceber.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //aqui termina =======================
                }

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Fechamento de viagem Adicionado com Sucesso !!!");

                    // as linhas abaixo limpam os campos
                    txtCod.setText(null);
                    txtCodV.setText(null);
                    cboCaminhao.setSelectedItem(null);
                    txtData.setText(null);
                    cboMotorista.setSelectedItem(null);
                    txtPercurso.setText(null);
                    txtAdiantamento.setText(null);
                    txtFreteIda.setText(null);
                    txtFreteVolta.setText(null);
                    txtTotalEntrada.setText(null);
                    txtKmIda.setText(null);
                    txtKmVolta.setText(null);
                    txtQtdLitros.setText(null);
                    txtMedia.setText(null);
                    txtKmPercorrido.setText(null);
                    txtLiquidoAdiant.setText(null);
                    txtDescFreteRec.setText(null);
                    txtFreteReceber.setText(null);
                    txtLiquidoViagem.setText(null);
                    txtDiesel.setText(null);
                    txtArla.setText(null);
                    txtPedagio.setText(null);
                    txtComissao.setText(null);
                    txtAcessorio.setText(null);
                    txtEstacionamento.setText(null);
                    txtChapa.setText(null);
                    txtGorjeta.setText(null);
                    txtOutro.setText(null);
                    txtTotalSaida.setText(null);

                    // Limpar campo combobox porem não é necessário, pode prejudicar o sistema
                    //cboUsoPerfil.setSelectedItem(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void calcularKmPercorrido() {

        int valor1 = 0;
        int valor2 = 0;

        valor1 = Integer.parseInt(txtKmIda.getText().replace(',', '.'));
        valor2 = Integer.parseInt(txtKmVolta.getText().replace(',', '.'));
        int soma = valor2 - valor1;

        txtKmPercorrido.setText(String.valueOf(soma));

    }

    public void calcularComissao() {

        double valor1 = 0;
        double valor2 = 0;

        Motorista motorista = (Motorista) cboMotorista.getSelectedItem();

        valor1 = Double.parseDouble(txtFreteIda.getText().replace(".","").replace(",","."));
        valor2 = Double.parseDouble(txtFreteVolta.getText().replace(".","").replace(",","."));
        double comissao = (valor1 + valor2) / 100 * motorista.getComissao();

        txtComissao.setText(String.format("%.2f", comissao));

    }

    public void calcularMedia() {

        double valor1 = 0;
        double valor2 = 0;

        valor1 = Double.parseDouble(txtQtdLitros.getText().replace(',', '.'));
        valor2 = Double.parseDouble(txtKmPercorrido.getText().replace(',', '.'));
        double soma = valor2 / valor1;

        txtMedia.setText(String.valueOf(String.format("%.2f", soma)));

    }

    public void calcularLiquidoAdiant() {

        double valor1 = 0;
        double valor2 = 0;
        double valor3 = 0;

        valor1 = Double.parseDouble(txtTotalEntrada.getText().replace(".","").replace(",","."));
        valor2 = Double.parseDouble(txtTotalSaida.getText().replace(".","").replace(",","."));
        valor3 = Double.parseDouble(txtFreteReceber.getText().replace(".","").replace(",","."));
        double soma = valor1 - valor2 - valor3;

        txtLiquidoAdiant.setText(String.valueOf(String.format("%.2f", soma)));

    }

    public void calcularLiquidoViagem() {

        double valor1 = 0;
        double valor2 = 0;
        double valor3 = 0;
        double valor4 = 0;

        valor1 = Double.parseDouble(txtFreteIda.getText().replace(".","").replace(",","."));
        valor2 = Double.parseDouble(txtFreteVolta.getText().replace(".","").replace(",","."));
        valor3 = Double.parseDouble(txtTotalSaida.getText().replace(".","").replace(",","."));
        valor4 = Double.parseDouble(txtFreteReceber.getText().replace(".","").replace(",","."));
        double soma = valor1 + valor2 - valor3 - valor4;

        txtLiquidoViagem.setText(String.valueOf(String.format("%.2f", soma)));

    }

    public void somarTotalEntrada() {

        double valor1=0;
        double valor2 = 0;
        double valor3 = 0;
        double soma = 0;

        valor1 = Double.parseDouble(txtAdiantamento.getText().replace(".","").replace(",","."));
        valor2 = Double.parseDouble(txtFreteIda.getText().replace(".","").replace(",","."));
        valor3 = Double.parseDouble(txtFreteVolta.getText().replace(".","").replace(",","."));
        System.out.println(valor1);
        System.out.println(valor2);
        System.out.println(valor3);
        soma = valor1 + valor2 + valor3;

        txtTotalEntrada.setText(String.valueOf(String.format("%.2f", soma)));
        

    }

    public void somarTotalSaida() {

        double valor1 = 0;
        double valor2 = 0;
        double valor3 = 0;
        double valor4 = 0;
        double valor5 = 0;
        double valor6 = 0;
        double valor7 = 0;
        double valor8 = 0;
        double valor9 = 0;

        valor1 = Double.parseDouble(txtDiesel.getText().replace(".","").replace(",","."));
        valor2 = Double.parseDouble(txtArla.getText().replace(".","").replace(",","."));
        valor3 = Double.parseDouble(txtPedagio.getText().replace(".","").replace(",","."));
        valor4 = Double.parseDouble(txtComissao.getText().replace(".","").replace(",","."));
        valor5 = Double.parseDouble(txtAcessorio.getText().replace(".","").replace(",","."));
        valor6 = Double.parseDouble(txtEstacionamento.getText().replace(".","").replace(",","."));
        valor7 = Double.parseDouble(txtChapa.getText().replace(".","").replace(",","."));
        valor8 = Double.parseDouble(txtGorjeta.getText().replace(".","").replace(",","."));
        valor9 = Double.parseDouble(txtOutro.getText().replace(".","").replace(",","."));
        double soma = valor1 + valor2 + valor3 + valor4 + valor5 + valor6 + valor7 + valor8 + valor9;

        txtTotalSaida.setText(String.valueOf(String.format("%.2f", soma)));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCod = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtData = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        cboCaminhao = new javax.swing.JComboBox<Object>();
        jLabel5 = new javax.swing.JLabel();
        txtPercurso = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboMotorista = new javax.swing.JComboBox<Object>();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtAdiantamento = new javax.swing.JTextField();
        txtFreteIda = new javax.swing.JTextField();
        txtFreteVolta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtDiesel = new javax.swing.JTextField();
        txtArla = new javax.swing.JTextField();
        txtPedagio = new javax.swing.JTextField();
        txtComissao = new javax.swing.JTextField();
        txtAcessorio = new javax.swing.JTextField();
        txtEstacionamento = new javax.swing.JTextField();
        txtChapa = new javax.swing.JTextField();
        txtGorjeta = new javax.swing.JTextField();
        txtOutro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtKmIda = new javax.swing.JTextField();
        txtKmVolta = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtQtdLitros = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtKmPercorrido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtMedia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTotalEntrada = new javax.swing.JTextField();
        txtTotalSaida = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtLiquidoViagem = new javax.swing.JTextField();
        txtLiquidoAdiant = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        btnCalcular = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtVeiculo = new javax.swing.JTextField();
        txtMotorista = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtDescFreteRec = new javax.swing.JTextField();
        txtFreteReceber = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtCodV = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        cboFreteReceber = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF - Fechamento de Viagem");
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

        txtCod.setEditable(false);
        txtCod.setEnabled(false);

        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        jLabel5.setText("Percurso Percorrido");

        jLabel2.setText("Data");

        jLabel3.setText("Motorista");

        jLabel4.setText("N° Viagem");

        jLabel1.setText("Caminhão");

        cboMotorista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboMotoristaMouseClicked(evt);
            }
        });
        cboMotorista.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                cboMotoristaComponentMoved(evt);
            }
        });
        cboMotorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMotoristaActionPerformed(evt);
            }
        });
        cboMotorista.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                cboMotoristaInputMethodTextChanged(evt);
            }
        });
        cboMotorista.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cboMotoristaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        cboMotorista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboMotoristaKeyReleased(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Adiantamento: R$");

        jLabel15.setText("Frete de ida: R$");

        jLabel16.setText("Frete de volta: R$");

        txtAdiantamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdiantamentoActionPerformed(evt);
            }
        });
        txtAdiantamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAdiantamentoKeyReleased(evt);
            }
        });

        txtFreteIda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFreteIdaActionPerformed(evt);
            }
        });
        txtFreteIda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFreteIdaKeyReleased(evt);
            }
        });

        txtFreteVolta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFreteVoltaActionPerformed(evt);
            }
        });
        txtFreteVolta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFreteVoltaKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 51));
        jLabel6.setText("Entrada");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(44, 44, 44)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFreteIda, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(txtAdiantamento)
                    .addComponent(txtFreteVolta, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtAdiantamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtFreteIda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtFreteVolta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel18.setText("Óleo Diesel: R$ ");

        jLabel19.setText("Arla: R$");

        jLabel20.setText("Pedagio: R$");

        jLabel21.setText("Comissão: R$");

        jLabel22.setText("Acessórios: R$");

        jLabel23.setText("Estacionamento: R$");

        jLabel24.setText("Chapa: R$");

        jLabel25.setText("Gorjeta: R$");

        jLabel26.setText("Outras despesas: R$");

        txtDiesel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDieselKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDieselKeyReleased(evt);
            }
        });

        txtArla.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtArlaKeyReleased(evt);
            }
        });

        txtPedagio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPedagioKeyReleased(evt);
            }
        });

        txtComissao.setEnabled(false);
        txtComissao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtComissaoKeyReleased(evt);
            }
        });

        txtAcessorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAcessorioKeyReleased(evt);
            }
        });

        txtEstacionamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEstacionamentoKeyReleased(evt);
            }
        });

        txtChapa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtChapaKeyReleased(evt);
            }
        });

        txtGorjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGorjetaKeyReleased(evt);
            }
        });

        txtOutro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOutroKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Saída");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel7))
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDiesel)
                    .addComponent(txtArla)
                    .addComponent(txtPedagio)
                    .addComponent(txtComissao)
                    .addComponent(txtAcessorio)
                    .addComponent(txtEstacionamento)
                    .addComponent(txtChapa)
                    .addComponent(txtGorjeta)
                    .addComponent(txtOutro, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtDiesel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtArla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtPedagio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtComissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtAcessorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtEstacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtChapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtGorjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtOutro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("TOTAL:   R$");

        jLabel9.setText("KM Ida");

        jLabel10.setText("KM Volta");

        txtKmIda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKmIdaActionPerformed(evt);
            }
        });
        txtKmIda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKmIdaKeyReleased(evt);
            }
        });

        txtKmVolta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKmVoltaKeyReleased(evt);
            }
        });

        jLabel11.setText("Qtd  Litros");

        txtQtdLitros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtdLitrosKeyReleased(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/saved.png"))); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/alter.png"))); // NOI18N
        jButton4.setPreferredSize(new java.awt.Dimension(81, 81));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/delete.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtKmPercorrido.setEditable(false);

        jLabel12.setText("KM Percorrido");

        txtMedia.setEditable(false);

        jLabel13.setText("Média");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 204, 51));
        jLabel17.setText("TOTAL:   R$");

        txtTotalEntrada.setEnabled(false);
        txtTotalEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalEntradaActionPerformed(evt);
            }
        });
        txtTotalEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalEntradaKeyReleased(evt);
            }
        });

        txtTotalSaida.setEnabled(false);
        txtTotalSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalSaidaActionPerformed(evt);
            }
        });
        txtTotalSaida.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtTotalSaidaInputMethodTextChanged(evt);
            }
        });
        txtTotalSaida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalSaidaKeyReleased(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel29.setText("Líquido viagem: R$");

        txtLiquidoViagem.setEditable(false);
        txtLiquidoViagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLiquidoViagemActionPerformed(evt);
            }
        });

        txtLiquidoAdiant.setEditable(false);

        jLabel27.setText("Líquido C/ Adiant.:  R$");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtLiquidoViagem, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(txtLiquidoAdiant))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtLiquidoAdiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(txtLiquidoViagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCalcular.setText("Calcular");
        btnCalcular.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCalcularMouseClicked(evt);
            }
        });
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/search.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(81, 81));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtMotorista.setEditable(false);
        txtMotorista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMotoristaActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel30.setText("Desc. Frete Receber:");

        jLabel31.setText("Valor Frete a Receber: ");

        txtDescFreteRec.setText("0");
        txtDescFreteRec.setEnabled(false);

        txtFreteReceber.setText("0,00");
        txtFreteReceber.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescFreteRec)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtFreteReceber, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtDescFreteRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtFreteReceber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel28.setText("N Viagem Cam.");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/print.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cboFreteReceber.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Não", "Sim" }));
        cboFreteReceber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFreteReceberActionPerformed(evt);
            }
        });

        jLabel32.setText("Frete a Receber");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(cboMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPercurso))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(46, 46, 46)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(txtCodV, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtData)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(328, 328, 328)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(175, 175, 175)
                                .addComponent(txtTotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtKmIda, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtKmVolta, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtKmPercorrido, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel32)
                                            .addComponent(cboFreteReceber, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtQtdLitros, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMedia, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(39, 39, 39)))
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(152, 152, 152)
                                .addComponent(txtTotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPercurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMotorista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel17))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMedia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQtdLitros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKmIda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel9)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtKmVolta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKmPercorrido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCalcular)
                            .addComponent(cboFreteReceber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        setBounds(0, 0, 742, 614);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void txtKmIdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKmIdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKmIdaActionPerformed

    private void txtAdiantamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdiantamentoKeyReleased
        // TODO add your handling code here:
        somarTotalEntrada();

    }//GEN-LAST:event_txtAdiantamentoKeyReleased

    private void txtFreteIdaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFreteIdaKeyReleased
        // TODO add your handling code here:
        somarTotalEntrada();
        calcularComissao();

    }//GEN-LAST:event_txtFreteIdaKeyReleased

    private void txtFreteVoltaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFreteVoltaKeyReleased
        // TODO add your handling code here:
        somarTotalEntrada();
        calcularComissao();
    }//GEN-LAST:event_txtFreteVoltaKeyReleased

    private void txtTotalEntradaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalEntradaKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTotalEntradaKeyReleased

    private void txtAdiantamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdiantamentoActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtAdiantamentoActionPerformed

    private void txtFreteIdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFreteIdaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtFreteIdaActionPerformed

    private void txtFreteVoltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFreteVoltaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtFreteVoltaActionPerformed

    private void txtDieselKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDieselKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtDieselKeyReleased

    private void txtArlaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArlaKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtArlaKeyReleased

    private void txtPedagioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPedagioKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtPedagioKeyReleased

    private void txtComissaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComissaoKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtComissaoKeyReleased

    private void txtAcessorioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAcessorioKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtAcessorioKeyReleased

    private void txtEstacionamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstacionamentoKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtEstacionamentoKeyReleased

    private void txtChapaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChapaKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtChapaKeyReleased

    private void txtGorjetaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGorjetaKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtGorjetaKeyReleased

    private void txtOutroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOutroKeyReleased
        // TODO add your handling code here:
        somarTotalSaida();
    }//GEN-LAST:event_txtOutroKeyReleased

    private void txtKmIdaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKmIdaKeyReleased
        // TODO add your handling code here:
        calcularKmPercorrido();
    }//GEN-LAST:event_txtKmIdaKeyReleased

    private void txtKmVoltaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKmVoltaKeyReleased
        // TODO add your handling code here:
        calcularKmPercorrido();
    }//GEN-LAST:event_txtKmVoltaKeyReleased

    private void txtQtdLitrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtdLitrosKeyReleased
        // TODO add your handling code here:
        calcularMedia();
    }//GEN-LAST:event_txtQtdLitrosKeyReleased

    private void txtTotalSaidaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalSaidaKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTotalSaidaKeyReleased

    private void txtTotalSaidaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTotalSaidaInputMethodTextChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTotalSaidaInputMethodTextChanged

    private void txtTotalEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalEntradaActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_txtTotalEntradaActionPerformed

    private void txtDieselKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDieselKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDieselKeyPressed

    private void btnCalcularMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalcularMouseClicked
        // TODO add your handling code here:
        calcularLiquidoAdiant();
        calcularLiquidoViagem();
    }//GEN-LAST:event_btnCalcularMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        pesquisar_viagem();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here: Botão Altterar

        String dat = txtData.getText();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d1 = null;
        try {
            d1 = formatador.parse(dat);

        } catch (ParseException ex) {
            System.out.println(ex);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        dat = format.format(d1);
        System.out.println(dat);

        if (cboFreteReceber.getSelectedItem() == "Sim") {

            String sql = "update tbl_fretereceber set descricao=?, percurso=?, dataa=?, valor=? where idViagem=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtDescFreteRec.getText());
                pst.setString(2, txtPercurso.getText());
                pst.setString(3, dat);
                pst.setString(4, txtFreteReceber.getText());
                // Capiturando dados combobox
                pst.setString(5, txtCod.getText());

                if ((txtDescFreteRec.getText().isEmpty()) || (txtPercurso.getText().isEmpty()) || (txtData.getText().isEmpty()) || (txtFreteReceber.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha Todos os Campos Obrigatórios (*)");

                } else {

                    // a linha abaixo atualiza a tabela de Usuarios com os dados do Formulário
                    // a estrutura abaixo é usada para confirmar a alteração de dados na tabel
                    int adicionado = pst.executeUpdate();
                    // a linha abaixo serve como entendimento da logica.
                    //System.out.println(adicionado);
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Dados do Frete a Receber Alterado com Sucesso !!!");

                        btnSave.setEnabled(true);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }

        alterar();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        // TODO add your handling code here:

    }//GEN-LAST:event_formInternalFrameActivated

    private void cboMotoristaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_cboMotoristaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMotoristaInputMethodTextChanged

    private void cboMotoristaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboMotoristaKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMotoristaKeyReleased

    private void cboMotoristaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboMotoristaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMotoristaMouseClicked

    private void cboMotoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMotoristaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMotoristaActionPerformed

    private void cboMotoristaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cboMotoristaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMotoristaAncestorAdded

    private void cboMotoristaComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_cboMotoristaComponentMoved
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMotoristaComponentMoved

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:

        String sql = "delete from tbl_fretereceber where idViagem=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCod.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Frete a receber aqui informado nesse fechamento de viagem Removido com Sucesso");
                // as linhas abaixo limpam os campos

            } else {
                JOptionPane.showMessageDialog(null, "Não Excluido !!!");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }

        // exclui registro do caixa
        String sqlCaixa = "delete from tbl_caixa where idViagem=?";
        try {
            pst = conexao.prepareStatement(sqlCaixa);
            pst.setString(1, txtCod.getText());
            int apagado = pst.executeUpdate();
            if (apagado > 0) {
                JOptionPane.showMessageDialog(null, "Frete a receber aqui informado nesse fechamento de viagem Removido com Sucesso do caixa");
                // as linhas abaixo limpam os campos

            } else {
                JOptionPane.showMessageDialog(null, "Não Excluido do Caixa !!!");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo deu errado !!!");

        }

        excluir();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtMotoristaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMotoristaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMotoristaActionPerformed

    private void txtLiquidoViagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLiquidoViagemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLiquidoViagemActionPerformed

    private void txtTotalSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalSaidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalSaidaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        gerarRelatorio();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void cboFreteReceberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFreteReceberActionPerformed
        // TODO add your handling code here:
        cboFreteRec();
    }//GEN-LAST:event_cboFreteReceberActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<Object> cboCaminhao;
    private javax.swing.JComboBox cboFreteReceber;
    private javax.swing.JComboBox<Object> cboMotorista;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtAcessorio;
    private javax.swing.JTextField txtAdiantamento;
    private javax.swing.JTextField txtArla;
    private javax.swing.JTextField txtChapa;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtCodV;
    private javax.swing.JTextField txtComissao;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDescFreteRec;
    private javax.swing.JTextField txtDiesel;
    private javax.swing.JTextField txtEstacionamento;
    private javax.swing.JTextField txtFreteIda;
    private javax.swing.JTextField txtFreteReceber;
    private javax.swing.JTextField txtFreteVolta;
    private javax.swing.JTextField txtGorjeta;
    private javax.swing.JTextField txtKmIda;
    private javax.swing.JTextField txtKmPercorrido;
    private javax.swing.JTextField txtKmVolta;
    private javax.swing.JTextField txtLiquidoAdiant;
    private javax.swing.JTextField txtLiquidoViagem;
    private javax.swing.JTextField txtMedia;
    private javax.swing.JTextField txtMotorista;
    private javax.swing.JTextField txtOutro;
    private javax.swing.JTextField txtPedagio;
    private javax.swing.JTextField txtPercurso;
    private javax.swing.JTextField txtQtdLitros;
    private javax.swing.JTextField txtTotalEntrada;
    private javax.swing.JTextField txtTotalSaida;
    private javax.swing.JTextField txtVeiculo;
    // End of variables declaration//GEN-END:variables
}
