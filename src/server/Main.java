package server;

import java.io.IOException;

import manager.http.HttpTaskServer;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        new KVServer().start();
        new HttpTaskServer().start();
    }
}