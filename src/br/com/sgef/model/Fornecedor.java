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
public class Fornecedor{
    
    private int idFornecedor;
    private String fornecedor;
    private String endereco;
    private String telefone;
    
  
    public int getId(){
        return idFornecedor;
    }
    
    public void setId(int idFornecedor){
        this.idFornecedor = idFornecedor;
    }
    
    public String getFornecedor(){
        return fornecedor;
    }
    
    public void setFornecedor(String fornecedor){
        this.fornecedor = fornecedor;        
    }
    public String getEndereco(){
        return endereco;
    }
    
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public String getTelefone(){
        return telefone;
    }
    
    public void setTelefone(String telefone){
        this.telefone = telefone;        
    }

    @Override
    public String toString() {
        return getFornecedor(); //To change body of generated methods, choose Tools | Templates.
    }


}

