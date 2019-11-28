/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.telas;

import br.com.sgef.dal.ModuloConexao;
import br.com.sgef.dao.VeiculoDAO;
import br.com.sgef.model.Veiculo;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class TelaRelCaixa extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaRelCaixa
     */
    public TelaRelCaixa() {
        initComponents();
        conexao = ModuloConexao.conector();

        VeiculoDAO Vdao = new VeiculoDAO();

        for (Veiculo v : Vdao.read()) {
            cboCaminhao.setSelectedItem(null);
            cboCaminhao.addItem(v);

        }

    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void gerarRelatorio() {

        String x = txtDataI.getText();
        String y = txtDataF.getText();

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String str = x;
        String stry = y;
        java.util.Date data = null;
        java.util.Date datay = null;

        try {
            data = formatador.parse(str);
            datay = formatador.parse(stry);
        } catch (ParseException ex) {
            Logger.getLogger(TelaRelCaixa.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        x = format.format(data);
        y = format.format(datay);

        //System.out.println(x); // aqui tras a data inicial em formato americano ou seja formato do DB
        //System.out.println(y); // aqui tras a data final em formato americano ou seja formato do DB
        int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a impressão deste relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // abrir relatorio 
            //se CX list for = a todos Vai gerar esse Rel
            if (cboTipo.getSelectedItem() == "Todos") {
                try {

                    Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();

                    Object cboPegarValor = cboTipo.getSelectedItem();
                    Object cboCam = veiculo.getId();

                    /* HashMap de parametros utilizados no relatório. Sempre instanciados */
                    Map parameters = new HashMap();
                    //Passa as datas como parâmetro para aparecer no relatório
                    parameters.put("DataI", x);
                    parameters.put("DataF", y);
                    parameters.put("Tipo", cboPegarValor);//Aqui vc passa os parâmetros para um hashmap, que será enviado para o relatório
                    parameters.put("Veiculo", cboCam);//Aqui vc passa os parâmetros para um hashmap, que será enviado para o relatório

                    JasperPrint print = JasperFillManager.fillReport("C:/reports/MovimentaçãoCaixaTodos.jasper", parameters, conexao);
                    
                    JasperViewer.viewReport(print, false);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            } else {
                try {

                    Veiculo veiculo = (Veiculo) cboCaminhao.getSelectedItem();

                    Object cboPegarValor = cboTipo.getSelectedItem();
                    Object cboCam = veiculo.getId();

                    /* HashMap de parametros utilizados no relatório. Sempre instanciados */
                    Map parameters = new HashMap();
                    //Passa as datas como parâmetro para aparecer no relatório
                    parameters.put("DataI", x);
                    parameters.put("DataF", y);
                    parameters.put("Tipo", cboPegarValor);//Aqui vc passa os parâmetros para um hashmap, que será enviado para o relatório
                    parameters.put("Veiculo", cboCam);//Aqui vc passa os parâmetros para um hashmap, que será enviado para o relatório

                    JasperPrint print = JasperFillManager.fillReport("C:/reports/MovimentaçãoCaixa.jasper", parameters, conexao);
                    JasperViewer.viewReport(print, false);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
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

        jLabel1 = new javax.swing.JLabel();
        txtDataI = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtDataI = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        txtDataF = new javax.swing.JTextField();
        try{
            javax.swing.text.MaskFormatter data= new javax.swing.text.MaskFormatter("##/##/####");
            txtDataF = new javax.swing.JFormattedTextField(data);
        }
        catch (Exception e){
        }
        jLabel2 = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cboCaminhao = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("SGEF - Relatório Movimentação do Caixa");

        jLabel1.setText("Periodo De:");

        jLabel2.setText("Até");

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Entrada", "Saida" }));
        cboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/sgef/icones/print.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Caminhão");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDataI, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTipo, 0, 127, Short.MAX_VALUE))
                                .addGap(30, 30, 30)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 25, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(39, 39, 39)
                                .addComponent(txtDataF, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboCaminhao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboCaminhao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setBounds(0, 0, 393, 238);
    }// </editor-fold>//GEN-END:initComponents

    private void cboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        gerarRelatorio();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboCaminhao;
    private javax.swing.JComboBox cboTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtDataF;
    private javax.swing.JTextField txtDataI;
    // End of variables declaration//GEN-END:variables

   
}
