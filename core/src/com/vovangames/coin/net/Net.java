package com.vovangames.coin.net;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.io.IOException;

public class Net {

    public static int TCPPort = 36625;
    public static int UDPPort = 36630;
    public static String address = "127.0.0.1";
    public static ServerProgram server;
    public static ClientProgram client;
    public static NetHandler handler;
    public static void registerClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(NewPlayer.class);
        kryo.register(UpdatePlayer.class);
        kryo.register(DeletePlayer.class);
    }

    public static void initClient() {
        client = new ClientProgram();
        try {
            client.init(address);
        } catch (IOException e) {
            throw new GdxRuntimeException(e);
        }

    }

    public static void initServer() {
        server = new ServerProgram();
        try {
            server.init();
        } catch (IOException e) {
            throw new GdxRuntimeException(e);
        }

    }

    public static class NewPlayer {
        public int id;
    }

    public static class UpdatePlayer {
        public int id;
        public float x;
        public float y;
    }

    public static class DeletePlayer {
        public int id;
    }

}
