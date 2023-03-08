package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import static com.vovangames.coin.net.Net.*;

public class ServerProgram {
    public Server server;

    public void init() throws IOException {
        server = new Server();
        ServerListener s = new ServerListener();
        s.handler = handler;
        Net.registerClasses(server);
        server.bind(TCPPort, UDPPort);
        server.start();
        server.addListener(s);
    }
}
