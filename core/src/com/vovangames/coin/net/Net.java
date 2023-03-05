package com.vovangames.coin.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Net {

    public static int TCPPort = 36625;
    public static int UDPPort = 36630;
    public static void registerClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(String.class);
        kryo.register(Integer.class);
        kryo.register(Float.class);
    }
}
