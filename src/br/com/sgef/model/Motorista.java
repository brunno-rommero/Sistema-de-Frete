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
public class Motorista{
    
    private int idMotorista;
    private String nameMotorista;
    private String enderecoMotorista;
    private int numeroMotorista;
    private String bairroMotorista;
    private String compleMotorista;
    private String telefone;
    private int comissao;
  
    public int getId(){
        return idMotorista;
    }
    
    public void setId(int idMotorista){
        this.idMotorista = idMotorista;
    }
    
    public String getName(){
        return nameMotorista;
    }
    
    public void setName(String nameMotorista){
        this.nameMotorista = nameMotorista;        
    }
    public String getEndMoto(){
        return enderecoMotorista;
    }
    
    public void setEndMoto(String enderecoMotorista){
        this.enderecoMotorista = enderecoMotorista;
    }
    public int getNumMoto(){
        return numeroMotorista;
    }
    
    public void setNumMoto(int numeroMotorista){
        this.numeroMotorista = numeroMotorista;        
    }
    public String getBairroMoto(){
        return bairroMotorista;
    }
    
    public void setBairroMoto(String bairroMotorista){
        this.bairroMotorista = bairroMotorista;        
    }
    public String getCompleMotorista(){
        return compleMotorista;
    }
    
    public void setCompleMotorista(String compleMotorista){
        this.compleMotorista = compleMotorista;
    }
    public String getTelefone(){
        return telefone;
    }
    
    public void setTelefone(String telefone){
        this.telefone = telefone;        
    }
    
    public int getComissao(){
        return comissao;
    }
    
    public void setComissao(int comissao){
        this.comissao = comissao;        
    }
    
    @Override
    public String toString() {
        return getName(); //To change body of generated methods, choose Tools | Templates.
    }

}