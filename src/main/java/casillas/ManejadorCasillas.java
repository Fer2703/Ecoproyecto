/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package casillas;

import Entidades.Jugador;
import ep.ecoproyecto.PanelJuego;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/**
 *
 * @author hp
 */
public class ManejadorCasillas {
    
    PanelJuego gp;
    Casilla[] casilla;
    int numCasillaMapa[][];

    public ManejadorCasillas(PanelJuego gp) {
        
        this.gp = gp;
        
        casilla = new Casilla[10];
        
        numCasillaMapa= new int[gp.maxColumnas+2][gp.maxFilas+2];
        cargarMapa("/mapas/mapaprueba.txt",0,0);
        getImagenCasilla();
        
        
    }
    
    public void getImagenCasilla(){
        try{
            casilla[0]=new Casilla();
            casilla[0].imagen= ImageIO.read(getClass().getResource("/casillas/pasto.png"));
            
            casilla[1]=new Casilla();
            casilla[1].imagen= ImageIO.read(getClass().getResource("/casillas/agua.png"));
            
            casilla[2]=new Casilla();
            casilla[2].imagen= ImageIO.read(getClass().getResource("/casillas/pared.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    
    }
    public void cargarMapa(String direccion,int dx,int dy){
        try{
            
            
            InputStream is = getClass().getResourceAsStream(direccion);
            BufferedReader br =new BufferedReader(new InputStreamReader(is));
        
            int colum=0+dx+8;
            int fila=0+8;
        
            while(colum < gp.maxColumnas+dx+8 && fila < gp.maxFilas+8){
            
       
                String line= br.readLine();
            
                while(colum < gp.maxColumnas+dx+8 ){
                    String[] numeros = line.split(" ");
                
                    int num = Integer.parseInt(numeros[colum]);
                
                    numCasillaMapa[colum-dx-8][fila-8]=num;
                    colum++;    
                }
                
                if (colum==gp.maxColumnas+dx+8){
                    colum=0+dx+8;
                    fila++;
                }
            }
        br.close();
        }
        catch (IOException e) {
     
        } 
    }
    
    public void actualizar(Jugador jugador,int dimX,int dimY){
          
        int i=jugador.x/64; 
        
        
      
        
        cargarMapa("/mapas/mapaprueba.txt",i,0);  
      
         
            
        cargarMapa("/mapas/mapaprueba.txt",0,1);
        cargarMapa("/mapas/mapaprueba.txt",i,0);  
        
    
    }
    
    public void dibujar(Graphics2D g2){
        int tamanio = 64;
        
        int columna=0;
        int fila=0;
        int x=0;
        int y=0;
        
        while (columna < gp.maxColumnas && fila <gp.maxFilas){
            
            int numCasilla= numCasillaMapa[columna][fila];
            
            
            g2.drawImage(casilla[numCasilla].imagen, x, y, gp.tamanioCasilla,gp.tamanioCasilla,null);
            columna++;
            x+=gp.tamanioCasilla;
            if(columna==gp.maxColumnas){
                columna=0;
                x=0;
                fila++;
                y+=gp.tamanioCasilla;
            }
        }      
        
    }
    
}
