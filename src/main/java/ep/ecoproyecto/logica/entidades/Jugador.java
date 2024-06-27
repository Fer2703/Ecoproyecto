package ep.ecoproyecto.logica.entidades;
import ep.ecoproyecto.gui.PanelJuego;
import ep.ecoproyecto.logica.KeyHandler;
import ep.ecoproyecto.logica.net.packets.Packet02Mov;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import ep.ecoproyecto.logica.tipografia.Fuentes;
import java.awt.Color;

/**
 *
 * @author C-A-F
 */

public class Jugador extends Entidad{

    KeyHandler keyH;
    public final int pantallaX;
    public final int pantallaY;
    protected String username;
    
    public Entidad inventario[]= new Entidad[10];
    public int dinero=0;
    public int llaves=0;
    public boolean interactuar;
    
    public int estado;
    public int estadojuego=1;
    public int estadotienda=2;
    public int eventoprevio;

    public Jugador(PanelJuego gp) {
        super(gp);
        this.keyH=new KeyHandler();
        this.username="";
        
        pantallaX=gp.screenWidth/2-(gp.tamanioCasilla/2);
        pantallaY=gp.screenHeight/2-(gp.tamanioCasilla/2);
        
        hitBox=new Rectangle();
        //donde empiza la hitbox en relacion a la esquina superior
        hitBox.x=16;
        hitBox.y=16;
        //tamanio de la hitbox
        hitBox.height=52;
        hitBox.width=48;
        
        //area de colision
        areadefectoX=hitBox.x;
        areadefectoY=hitBox.y;
        
        estado=estadojuego;
        
        valoresporDefecto();
        getPlayerImage();
    }
    
   
    
    public Jugador(PanelJuego gp, KeyHandler keyH, String username){
        
        super(gp);
        
        this.keyH=keyH;
        this.username=username;
        
        pantallaX=gp.screenWidth/2-(gp.tamanioCasilla/2);
        pantallaY=gp.screenHeight/2-(gp.tamanioCasilla/2);
        
        hitBox=new Rectangle();
        //donde empiza la hitbox en relacion a la esquina superior
        hitBox.x=8;
        hitBox.y=4;
        //tamanio de la hitbox
        hitBox.height=56;
        hitBox.width=48;
        
        //area de colision
        areadefectoX=hitBox.x;
        areadefectoY=hitBox.y;
        
        valoresporDefecto();
        getPlayerImage();
        
    }
    
    private void valoresporDefecto(){
        //posicion del jugador en el arreglo +1,
        xMapa=10*gp.tamanioCasilla+gp.tamanioCasilla;
        yMapa=10*gp.tamanioCasilla+gp.tamanioCasilla;
        
        estado=estadojuego;
        
        vel=4;
        veloriginal=vel;
        direction ="down"; 
        if(gp.socketserver!=null){
        Packet02Mov packet=new Packet02Mov(username, this.xMapa, this.yMapa,this.direction);
        packet.writeData(PanelJuego.juego.socketcliente);}
    }

    public String getDirection() {
        return direction;
    }

    public String getUsername() {
        return username;
    }
    
    private void getPlayerImage(){
        
        up1=configuracion("/player/jg_arr_01");
        up2=configuracion("/player/jg_arr_02");
        down1=configuracion("/player/jg_abj_01");
        down2=configuracion("/player/jg_abj_02");
        left1=configuracion("/player/jg_izq_01");
        left2=configuracion("/player/jg_izq_02");
        right1=configuracion("/player/jg_der_01");
        right2=configuracion("/player/jg_der_02");
        
    }
    
    public void update(){
        if(keyH != null){        
            interactuar=false;
            if (keyH.upPressed==true||keyH.leftPressed==true||keyH.downPressed==true||keyH.rightPressed==true||keyH.ePressed==true){
                //actualizamos la posicion del jugador sumando o restando su velocidad
                if(keyH.upPressed==true){
                    direction="up";
                }else if(keyH.leftPressed==true){
                    direction="left";
                }else if(keyH.downPressed==true){
                    direction="down";
                }else if(keyH.rightPressed==true){
                    direction="right";
                }else if(keyH.ePressed==true){
                    interactuar=true;
                }
                Packet02Mov packet=new Packet02Mov(username, this.xMapa, this.yMapa,this.direction);
                packet.writeData(PanelJuego.juego.socketcliente);
                
                switch(estado){
                    case 1:estadoJuego();
                        break;
                    case 2:
                        break;
                }
            }    
        } 
    }
    
    
    public void estadoJuego(){
               
                //colision Casillas
                colision=false;
                gp.colisiones.revisarColision(this);
            
                //colision NPC
                   //int entidadID=gp.colisiones.chequeoEntidades(this, gp.NPC);
                   //intereaccionNCP(entidadID);
                intereaccionNCP(gp.colisiones.chequeoEntidades(this, gp.NPC));
                //colision objetos
                    //int objID=gp.colisiones.chequeoObjetos(this, true);
                    //recogerobjetos(objID);
                recogerobjetos(gp.colisiones.chequeoObjetos(this, true));
            
                //colision eventos
                gp.ControlEventos.chequeoEvento();
            
            
                if(colision==false && interactuar==false){
                    switch (direction) {
                            case "up" -> {yMapa-=vel;
                                hitBox.x=16;
                                hitBox.width=30;
                            }
                            case "left" -> {
                                xMapa-=vel;
                                hitBox.x=14;
                                hitBox.width=36;
                            }
                                
                            case "down" ->{ 
                                yMapa+=vel;
                                hitBox.x=14;                                                            
                                hitBox.width=36;}
                            
                            case "right" -> {
                                xMapa+=vel;
                                hitBox.x=16;                              
                                hitBox.width=30;
                            }
                    }
                }
  
                Packet02Mov packet=new Packet02Mov(username, this.xMapa, this.yMapa,this.direction);
                packet.writeData(PanelJuego.juego.socketcliente);
         

                    spriteCounter++;
                    if (spriteCounter>10){
                        if (spriteNum == 2 )
                        {spriteNum=1;}
                        else{
                        if (spriteNum == 1)
                        spriteNum=2;
                        }
                        spriteCounter = 0;
                    }
    }

    public void recogerobjetos(int id){
        
        if(id!=999){
            //usa el nombre del objeto para saber con cual objeto colisiona 
            String objnombre=gp.obj[gp.mapaActual][id].nombre;
            
            //switch para el nombre
            //nota se puede usar un getclass para saber el tipo o usar 
            switch(objnombre){
                case "llave" -> {
                    llaves++;
                    gp.efectos(2);
                    gp.hud.mostrarmensaje("conseguiste una llave");
                    this.inventario[1]=gp.obj[gp.mapaActual][id];
                    gp.obj[gp.mapaActual][id]=null;
                    System.out.println("llaves: "+llaves);
                }
                case "puerta" -> {
                        if(llaves>0){
                            llaves--;
                            gp.efectos(5);
                            gp.hud.mostrarmensaje("puerta abierta");
                            gp.obj[gp.mapaActual][id]=null;
                            if(llaves==0){
                                this.inventario[1]=null;
                            }
                        }else{
                            gp.hud.mostrarmensaje("no tienes llaves para esta puerta");
                            if(llaves==0){
                                this.inventario[1]=null;
                            }
                        }
                    }    
                case "botas" -> {
                    if(this.inventario[0]==null){
                        gp.efectos(4);
                        gp.hud.mostrarmensaje("conseguiste "+objnombre);
                        vel=vel+2;
                        this.inventario[0]=gp.obj[gp.mapaActual][id];
                        gp.obj[gp.mapaActual][id]=null;
                    }
                }
                case "cofre" -> {
                    gp.hud.victoriamensaje=true;
                    gp.obj[gp.mapaActual][id]=null;
                }
                case "coin" -> {
                    dinero++;
                    gp.hud.victoriamensaje=true;
                    gp.obj[gp.mapaActual][id]=null;
                }

            }
            
        }
        
    }
    
    public void RecogerObjeto(int i){
        if(i!=999){
            gp.obj[gp.mapaActual][i]=null;
        }
    }
          
    public void intereaccionNCP(int id) {
        if(id!=999){
            if (interactuar==true){
                if(gp.NPC[gp.mapaActual][id].movimiento==true){
                    if("right".equals(direction)){
                        gp.NPC[gp.mapaActual][id].direction="left";
                    }
                    if("left".equals(direction)){
                        gp.NPC[gp.mapaActual][id].direction="right";
                    }
                    if("up".equals(direction)){
                        gp.NPC[gp.mapaActual][id].direction="down";
                    }
                    if("down".equals(direction)){
                        gp.NPC[gp.mapaActual][id].direction="up";
                    }
                }
                if(gp.NPC[gp.mapaActual][id].Mensaje!=null){
                    gp.hud.mostrarmensaje(gp.NPC[gp.mapaActual][id].Mensaje);
                }
                if(gp.NPC[gp.mapaActual][id] instanceof Tienda){
                    gp.hud.mostrarmensaje("tienda");
                }
            }
        }
    }
    
    public void dibujado(Graphics2D g2){

        BufferedImage image = null;  

        switch(direction){
            case "up" -> {     
                if (spriteNum==1){
                    image=up1;
                }
                  if (spriteNum==2){
                    image=up2;
                }
            break;
            }
            case "down" -> {
                if (spriteNum==1)
                    image=down1;
                if (spriteNum==2)
                    image=down2;                              
                break;
            }
            case "left" ->  {     
                if (spriteNum==1)
                    image=left1;
                if (spriteNum==2)
                    image=left2;
                break;
            }
            case "right" -> {      
                if (spriteNum==1)   
                    image=right1;
                if (spriteNum==2)   
                    image=right2;
                break;
            }
            
        }
       
        
        if(gp.jugador.equals(this)) {
            g2.drawImage(image,pantallaX,pantallaY,null);   
 
            if (username != null){
                Fuentes tipoFuente=new Fuentes();
                g2.setFont((tipoFuente.fuente(tipoFuente.upheaval,0,20)));
                int textX = pantallaX + (image.getWidth() - g2.getFontMetrics().stringWidth(username))/2 ;
                int textY = pantallaY - 5;

                //Bordes Negros//
                 g2.setColor(Color.BLACK);
                 g2.drawString(username, textX - 2, textY - 2);
                 g2.drawString(username, textX - 2, textY + 2);
                 g2.drawString(username, textX + 2, textY - 2);
                 g2.drawString(username, textX + 2, textY + 2);
                 g2.drawString(username, textX, textY - 2);
                 g2.drawString(username, textX, textY + 2);
                 g2.drawString(username, textX - 2, textY);
                 g2.drawString(username, textX + 2, textY);

                 //Letras Blancas//
                 g2.setColor(Color.WHITE);
                 g2.drawString(username, textX, textY);

            
            
            
            }
        }else
        {
            int dibX=gp.jugador.pantallaX+this.xMapa-gp.jugador.xMapa;
            int dibY=gp.jugador.pantallaY+this.yMapa-gp.jugador.yMapa;
           // g2.drawImage(image,xMapa,yMapa,null);
            g2.drawImage(image,dibX,dibY,null);
            
            
            if (username != null){
                Fuentes tipoFuente=new Fuentes();
                g2.setFont((tipoFuente.fuente(tipoFuente.upheaval,0,20)));
                int textX = dibX + (image.getWidth() - g2.getFontMetrics().stringWidth(username))/2 ;
                int textY = dibY - 5;

                //Bordes Negros//
                 g2.setColor(Color.BLACK);
                 g2.drawString(username, textX - 2, textY - 2);
                 g2.drawString(username, textX - 2, textY + 2);
                 g2.drawString(username, textX + 2, textY - 2);
                 g2.drawString(username, textX + 2, textY + 2);
                 g2.drawString(username, textX, textY - 2);
                 g2.drawString(username, textX, textY + 2);
                 g2.drawString(username, textX - 2, textY);
                 g2.drawString(username, textX + 2, textY);

                 //Letras Blancas//
                 g2.setColor(Color.WHITE);
                 g2.drawString(username, textX, textY);
            }
            
        }
    }


}
