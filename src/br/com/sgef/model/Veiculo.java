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
public class Veiculo{

    
    private int idVeiculo;
    private String modelo;
    private int ano;
    private String placa;
    private String fabricante;
    private int renavan;
  
    public int getId(){
        return idVeiculo;
    }
    
    public void setId(int idVeiculo){
        this.idVeiculo = idVeiculo;
    }
    
    public String getModelo(){
        return modelo;
    }
    
    public void setModelo(String modelo){
        this.modelo = modelo;        
    }
    public int getAno(){
        return ano;
    }
    
    public void setAno(int ano){
        this.ano = ano;
    }
    public String getPlaca(){
        return placa;
    }
    
    public void setPlaca(String placa){
        this.placa = placa;        
    }
    public String getFabricante(){
        return fabricante;
    }
    
    public void setFabricante(String fabricante){
        this.fabricante = fabricante;        
    }
    public int getRenavan(){
        return renavan;
    }
    
    public void setRenavan(int renavan){
        this.renavan = renavan;
    }
    
    @Override
    public String toString() {
        return getModelo(); //To change body of generated methods, choose Tools | Templates.
    }

}
