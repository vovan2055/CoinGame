package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ServerListener extends Listener {

    public NetHandler handler;

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println(connection.getID() + " connected");
        handler.connect(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        System.out.println(connection.getID() + " disconnected");
        handler.disconnect(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        System.out.println(o.toString() + " received from " + connection.getID());
        handler.receive(o);
    }
}
