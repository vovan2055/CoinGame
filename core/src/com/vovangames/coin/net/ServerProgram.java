package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import static com.vovangames.coin.net.Net.*;

public class ServerProgram {
    Server server;

    public void init() throws IOException {
        server = new Server();
        server.bind(TCPPort, UDPPort);
        server.start();
        server.addListener(new ServerListener());
    }
}
