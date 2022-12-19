package com.example.semestrovka2.server.logic;

import com.example.semestrovka2.packetsss.ClientRequestPacket;
import com.example.semestrovka2.packetsss.ServerResponsePacket;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

@Data
public class ServerBase implements Runnable {

    private ServerSocket server;
    private Socket client;
    private final ThreadPoolExecutor threadPoolExecutor;
    private int port;

    @SneakyThrows
    public ServerBase(ThreadPoolExecutor threadPoolExecutor, int port) {
        this.server = new ServerSocket(port);
        this.threadPoolExecutor = threadPoolExecutor;
        this.port = port;
    }

    @SneakyThrows
    @Override
    public void run() {
        this.client = server.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        while (true){
            synchronized (threadPoolExecutor){
                ClientRequestPacket requestPacket = (ClientRequestPacket) objectInputStream.readObject();
                String code = requestPacket.getCode();
                if (Objects.equals(code, "UP")){
                    objectOutputStream.writeObject(ServerResponsePacket.builder().content("UP").build());
                } else if (Objects.equals(code, "DOWN")) {
                    objectOutputStream.writeObject(ServerResponsePacket.builder().content("DOWN").build());
                }else if (Objects.equals(code, "RIGHT")){
                    objectOutputStream.writeObject(ServerResponsePacket.builder().content("RIGHT").build());
                } else if (Objects.equals(code, "LEFT")) {
                    objectOutputStream.writeObject(ServerResponsePacket.builder().content("LEFT").build());
                }else{
                    Thread.sleep(10);
                }
            }
        }
    }
}
