package com.example.swcho.smartroomproject;

public class RoomContextState {

    private String room;
    private String status;
    private int light;
    private int hum;
    private int temp;
    private String re;

    public RoomContextState(String room, String re, String status, int light, int hum, int temp) {
        super();
        this.room = room;
        this.status = status;
        this.light = light;
        this.hum = hum;
        this.temp = temp;
        this.re = re ;
    }

    public int getHum() {
        return hum;
    }

    public int getTemp() {
        return temp;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getRoom() {
        return this.room;
    }

    public String getRe() {
        return re;
    }

    public String getStatus() {
        return this.status;
    }

    public int getLight() {
        return this.light;
    }

}