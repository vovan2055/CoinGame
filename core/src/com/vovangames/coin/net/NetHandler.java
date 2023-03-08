package com.vovangames.coin.net;

import com.esotericsoftware.kryonet.Connection;

public interface NetHandler {
    void receive(Object o);
    void connect(Connection c);
    void disconnect(Connection c);
}
