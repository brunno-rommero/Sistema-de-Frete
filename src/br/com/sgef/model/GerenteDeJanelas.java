/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgef.model;

import br.com.sgef.telas.TelaCadUser;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author BrunoRomeroAlencar
 */
public class GerenteDeJanelas {
    
    private static JDesktopPane desktop;
    
    public GerenteDeJanelas(JDesktopPane jDesktopPane){
        GerenteDeJanelas.desktop = jDesktopPane;
    }
    
    public void abrirJanelas(JInternalFrame jInternalFrame){
        if (jInternalFrame.isVisible()) {
            jInternalFrame.toFront();
            jInternalFrame.requestFocus();
        }else{
            desktop.add(jInternalFrame);
            
            TelaCadUser usuario = new TelaCadUser();
            jInternalFrame.setVisible(true);
        }
    }
    
}
