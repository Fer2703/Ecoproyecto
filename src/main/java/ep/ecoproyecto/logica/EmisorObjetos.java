/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ep.ecoproyecto;


import Interfaces.Dibujado;
import java.awt.Graphics2D;
import objetos.ObjetoCofre;
import objetos.ObjetoEquipo;
import objetos.ObjetoPuerta;
import objetos.ObjetoRecogible;

/**
 *
 * @author Cris
 */
public class EmisorObjetos {
    
    PanelJuego gp;
    
    public EmisorObjetos(PanelJuego gp){
        this.gp=gp;
    }
    
    public void establecerObj(){
        
        //----MUNDO 1:-----//
        int NumMap = 0;
        gp.obj[NumMap][0]=new ObjetoRecogible("llave",20,9,gp);
        gp.obj[NumMap][1]=new ObjetoRecogible("llave",5,7,gp);
        gp.obj[NumMap][2]=new ObjetoPuerta("puerta",7,8,gp);
        gp.obj[NumMap][3]=new ObjetoCofre("cofre",3,7,gp);
        gp.obj[NumMap][4]=new ObjetoEquipo("botas",6,6,gp);
        
        /*
        //----MUNDO 2:-----//
        NumMap = 1;
        
        //----MUNDO 3:-----//
        NumMap = 2;
        
        //----MUNDO 4:-----//
        NumMap = 3;
        
        //----MUNDO 5:-----//
        NumMap = 4;
        */
    }
    
    public void draw(Graphics2D g2){
        
            for(int i=0;i<gp.obj[0].length;i++){
                //if((gp.obj[i]!=null)&&(gp.obj[i].posicionX>jugador.xMapa && gp.obj[i].posicionX <jugador.xMapa+this.gp.getWidth())){
                if(gp.obj[gp.MapaActual][i]!=null){
                int PantallaX=gp.obj[gp.MapaActual][i].posicionX- gp.jugador.xMapa+gp.jugador.pantallaX;
                int PantallaY=gp.obj[gp.MapaActual][i].posicionY- gp.jugador.yMapa+gp.jugador.pantallaY;
                
                    if((gp.obj[gp.MapaActual][i].posicionX+gp.tamanioCasilla > gp.jugador.xMapa-gp.jugador.pantallaX)&&(gp.obj[gp.MapaActual][i].posicionX-gp.tamanioCasilla < gp.jugador.xMapa+gp.jugador.pantallaX)&&
                       (gp.obj[gp.MapaActual][i].posicionY+gp.tamanioCasilla > gp.jugador.yMapa-gp.jugador.pantallaY)&&(gp.obj[gp.MapaActual][i].posicionY-gp.tamanioCasilla < gp.jugador.yMapa+gp.jugador.pantallaY)){     

                        g2.drawImage(gp.obj[gp.MapaActual][i].image, PantallaX, PantallaY, gp.tamanioCasilla,gp.tamanioCasilla,null);
                    }
                    /*
                    g2.setColor(Color.red);
                    g2.fillRect(gp.obj[i].posicionX, gp.obj[i].posicionY, gp.obj[i].AreaobjX, gp.obj[i].AreaobjdefectoY);*/
               }
                
               
                
            }
            
    }
    
    
        
}
