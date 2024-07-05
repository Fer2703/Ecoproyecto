package ep.ecoproyecto.logica.casillas;
import ep.ecoproyecto.logica.Herramientas;
import ep.ecoproyecto.gui.PanelJuego;
import ep.ecoproyecto.logica.Interfaces.Dibujado;
import ep.ecoproyecto.logica.entidades.Jugador;
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
public class ManejadorCasillas  implements Dibujado{
    
    PanelJuego gp;
    public Casilla[] casilla;
    public int numCasillaMapa[][];
    public String mapa[]= new String[6];

    public ManejadorCasillas(PanelJuego gp) {
        
        this.gp = gp;
        
        casilla = new Casilla[20];        
        
        //-----AGREGAR MUNDOS AQUI----------//
        
        mapa[0]="/mapas/mapaCentro.txt";
        mapa[1]="/mapas/mapapruebas.txt";
        mapa[2]="/mapas/tienda.txt";
        mapa[3]="/mapas/mapaDerecha.txt";
        mapa[4]="/mapas/mapaArriba.txt";
        mapa[5]="/mapas/mapaIzquierda.txt";
         
        
        
        cargarMapa(mapa[gp.mapaActual],0,0);
        getImagenCasilla();
        
    }
    
    private void getImagenCasilla(){

            
            configutacion(0, "agua", true);       
            configutacion(1, "aguaA", true);
            configutacion(2, "aguaAD", true);
            configutacion(3, "aguaAb", true);
            configutacion(4, "aguaAbI", true);
            configutacion(5, "aguaD", true);
            configutacion(6, "aguaDA", true);
            configutacion(7, "aguaI", true);
            configutacion(8, "aguaIA", true);
            configutacion(9, "arbol", true);
            configutacion(10, "arena", false);
            configutacion(11, "asfalto", true);
            configutacion(12, "pared", false);          
            configutacion(13, "pasto", false);
            configutacion(14, "lodo", false);
            configutacion(15, "pared", true);
    }
    
    public void configutacion(int id,String ruta, boolean colision){
        
        Herramientas herramienta = new Herramientas();
        
        try{

            casilla[id] = new Casilla();
            casilla[id].imagen= ImageIO.read(getClass().getResource("/casillas/"+ruta+".png"));
            casilla[id].imagen = herramienta.imagenEscalada(casilla[id].imagen, gp.tamanioCasilla, gp.tamanioCasilla);
            casilla[id].colision = colision;
            
        }catch(IOException e){

        }
        
    }
    
    private void cargarMapa(String direccion, int dx,int dy){
        try{
            
            
            InputStream is = getClass().getResourceAsStream(direccion);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                int colum=0;
                int fila=0;
                
                String line= br.readLine();
                
                if(line != null){
                    String[] arr=line.split(" ");

                    if(numCasillaMapa==null||numCasillaMapa.length != arr.length ){
                        numCasillaMapa=new int[arr.length][arr.length]  ;
                    }

                        while(line!=null &&colum <arr.length && fila < arr.length  ){

                            while(colum < arr.length){

                                String numeros[] = line.split(" ");

                                int num = Integer.parseInt(numeros[colum]);

                                numCasillaMapa[colum][fila]=num;
                                colum++;
                            }
                            if(colum==arr.length ){
                                colum=0;
                                fila++;

                            }
                    
                        line= br.readLine();
                    }
                }
            }
        }catch (IOException e) {
            
        } 
    }
    
    public void actualizar(Jugador jugador,int dimX,int dimY){
          
        int i=jugador.xMapa/64; 
        int j=jugador.yMapa/64;

        cargarMapa(mapa[gp.mapaActual],i,j);        
    
    }
    
    @Override
    public void dibujado(Graphics2D g2){
        
        int columna=0;
        int fila=0;
        
        
        while (columna < numCasillaMapa.length && fila < numCasillaMapa.length){
        
            int numCasilla = numCasillaMapa[columna][fila];
                
                int PosicionX=columna*gp.tamanioCasilla;
                int PosicionY=fila*gp.tamanioCasilla;
                int PantallaX=PosicionX- gp.jugador.xMapa+gp.jugador.pantallaX;
                int PantallaY=PosicionY- gp.jugador.yMapa+gp.jugador.pantallaY;
                
                if((PosicionX+gp.tamanioCasilla > gp.jugador.xMapa-gp.jugador.pantallaX)&&
                        (PosicionX-gp.tamanioCasilla < gp.jugador.xMapa+gp.jugador.pantallaX)&&
                   (PosicionY+gp.tamanioCasilla > gp.jugador.yMapa-gp.jugador.pantallaY)&&
                        (PosicionY-gp.tamanioCasilla < gp.jugador.yMapa+gp.jugador.pantallaY)){     
                
                    g2.drawImage(casilla[numCasilla].imagen, PantallaX, PantallaY,null);
                    
                }
                
                columna++;


                if(columna==numCasillaMapa.length){
                    columna=0;
                    fila++;
                }
       }      
    }        
}
    

