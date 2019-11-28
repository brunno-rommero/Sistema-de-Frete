/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.model;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class ContasPagar{
    
    private int id;
    private String descricao;
    private String pago;
    private String data_vencimento;
    private String especie;
    private String valor;
    private Fornecedor Fornecedor;
    
    public ContasPagar(){
        Fornecedor = new Fornecedor();
    }
    public ContasPagar(int id, String descricao, String pago, String data_vencimento, String especie, String valor, Fornecedor Fornecedor){
    
    this.id = id;
    this.descricao = descricao;
    this.pago = pago;
    this.data_vencimento = data_vencimento;
    this.especie = especie;
    this.valor = valor;
    this.Fornecedor = Fornecedor;

    }
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getDescricao(){
        return descricao;
    }
    
    public void setDescricao(String descricao){
        this.descricao = descricao;        
    }
    public String getPago(){
        return pago;
    }
    
    public void setPago(String pago){
        this.pago = pago;
    }
    public String getDataa(){
        return data_vencimento;
    }
    
    public void setDataa(String dataa){
        this.data_vencimento = dataa;        
    }
    public String getEspecie(){
        return especie;
    }
    
    public void setEspecie(String especie){
        this.especie = especie;        
    }
    public String getValor(){
        return valor;
    }
    
    public void setValor(String valor){
        this.valor = valor;        
    }

}
