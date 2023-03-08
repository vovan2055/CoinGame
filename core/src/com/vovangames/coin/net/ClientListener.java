package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {

    public NetHandler handler;
    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("connected to server with connection ID " + connection.getID());
        handler.connect(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        System.out.println("disconnected from server");
        handler.disconnect(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        System.out.println("got data from server");
        handler.receive(o);
    }
}
