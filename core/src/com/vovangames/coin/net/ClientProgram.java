package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

import static com.vovangames.coin.net.Net.*;

public class ClientProgram {

    public Client client;

    public void init(String address) throws IOException {
        ServerListener l = new ServerListener();
        l.handler = Net.handler;
        client = new Client();
        Net.registerClasses(client.getEndPoint());
        client.start();
        client.addListener(l);

        client.connect(5000, address, TCPPort, UDPPort);
    }

}
