package com.example.semestrovka2.server;

import com.example.semestrovka2.server.logic.ServerBase;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class StartServer {

    public static void main(String[] args) {
        ThreadPoolExecutor serverPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        ServerBase base = new ServerBase(serverPool, 5555);
        while (true){
            synchronized (serverPool){
                serverPool.execute(base);
            }
        }
    }

}
