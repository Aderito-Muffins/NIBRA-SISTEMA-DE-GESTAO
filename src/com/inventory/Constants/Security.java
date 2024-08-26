/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.Constants;

import com.sun.jdi.connect.spi.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import javax.swing.JOptionPane;

/**
 *
 * @author Aderito
 */
public class Security {
    public static boolean checkContact(String Contacto){
        if(Contacto.startsWith("+258")){
            Contacto = Contacto.replace("+258", "");
            return false;
        }
        if(Contacto.startsWith("258")){
            Contacto = Contacto.replace("258", "");
            return false;
        }
        if(Contacto.isEmpty()){ JOptionPane.showMessageDialog(null, "ERRO [C00], Porfavor verifique o campo do telefone, esta vazio.");
            return true;}
        
        JOptionPane.showMessageDialog(null, "ERRO [C01], Porfavor verifique o campo do telefone, formato incorreto!");
        return true;
    }

    
    
    
    
    
    
}
