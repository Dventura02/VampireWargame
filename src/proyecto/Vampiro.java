/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Alejandro
 */
public class Vampiro extends Ficha{
    
    private Image img2 = new Image(getClass().getResourceAsStream("yes.png"));
    private ImageView blank = new ImageView(img2);
    
    public Vampiro(String n){
        test="V";
        color=n;
        
        if(n.equals("azul")){
            img=new Image(getClass().getResourceAsStream("vampiroazul.png"));
            
        }else {
            img=new Image(getClass().getResourceAsStream("vampirorojo.png"));
        }
        
       
        icon=new ImageView(img);    
        ataque= 3; //3
        vida= 4;
        escudo = 5;
        
    }
    
    @Override
   
 
    public void ataqueEspecial(LogicaVampire x,Button[][] bu, Button tardis, int superman, int batman) {
        
        int a= getX(tardis,bu);
        int b= getY(tardis,bu);
        
        
        
        System.out.println("Soy Vampiro");
        
        if (bu[a][b].getGraphic() != ((Node) blank)){ 
            int ataque= 1;
            int life=x.tablero[b][a].getVida();

            if(ataque>life){
                life=0;
            }
            else{
                life=life-ataque;
            }

            x.tablero[b][a].setVida(life);
            System.out.println("Vida: " + life);
        }
    }
}
    

