package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

import static com.vovangames.coin.net.Net.*;

public class ClientProgram {

    Client client;

    public void init() throws IOException {
        client = new Client();
        client.start();
        client.addListener(new ServerListener());
        client.connect(5000, "127.0.0.1", TCPPort, UDPPort);
    }

}
