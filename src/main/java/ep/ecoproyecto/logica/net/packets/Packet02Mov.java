/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ep.ecoproyecto.logica.net.packets;

import ep.ecoproyecto.logica.net.Cliente;
import ep.ecoproyecto.logica.net.Server;

public class Packet02Mov extends Packet{
    
    private String username,dir;
    private int x,y;
    
    
    public Packet02Mov(byte[] data) {
        super(02);
        String[] dataArray=readData(data).split(",");
        
        this.username = dataArray[0];   
        this.x=Integer.parseInt(dataArray[1]);
        this.y=Integer.parseInt(dataArray[2]);
        this.dir = dataArray[3];
        
    }

    public Packet02Mov(String username,int x,int y,String dir) {
        super(02);
        this.username = username;
        this.x=x;
        this.y=y;
        this.dir=dir;
    }

    public String getDir() {
        return dir;
    }
    
    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

   
    @Override
    public void writeData(Cliente cliente) {
        cliente.enviarData(getData());
    }

    @Override
    public void writeData(Server server) {
        server.enviarDataClientes(getData());
    }

    //Aquí se agregan los datos con coma para separar en paquetes y despues hacer el split
    
    @Override
    public byte[] getData() {
        return ("02" + this.username+","+this.x+","+this.y+","+this.dir).getBytes();
    }
    
}
