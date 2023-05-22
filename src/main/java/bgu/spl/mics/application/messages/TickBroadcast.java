package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private int x;

    public TickBroadcast(int x) {
        this.x=x;
    }

    public int getX() {
        return x;
    }
}
