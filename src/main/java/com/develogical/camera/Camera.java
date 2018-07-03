package com.develogical.camera;

public class Camera {

    Sensor sensor;
    MemoryCard card;

    private boolean state; //state of true = on; false = off;
    private boolean readState; //true = actively reading, false = done


    public Camera(Sensor sensor, MemoryCard card) {
        this.sensor = sensor;
        this.card = card;
        state = false;
        readState = false;
    }

    public void pressShutter() {
        if (state) {
            readState = true;
            card.write(sensor.readData(), () -> {
                readState = false;
            });
        }
    }

    public void powerOn() {
        sensor.powerUp();
        state = true;
    }

    public void powerOff() {
        if (!readState) {
            sensor.powerDown();
            state = false;
        }
    }
}

