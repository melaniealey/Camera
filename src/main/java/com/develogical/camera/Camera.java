package com.develogical.camera;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard card;

    private boolean isOn; //state of true = on; false = off;
    private boolean isWriting; //true = actively reading, false = done


    public Camera(Sensor sensor, MemoryCard card) {
        this.sensor = sensor;
        this.card = card;
        isOn = false;
        isWriting = false;
    }

    public void pressShutter() {
        if (isOn) {
            isWriting = true;
            card.write(sensor.readData(), () -> {
                isWriting = false;
                if (!isOn)
                    sensor.powerDown();
            });
        }
    }

    public void powerOn() {
        sensor.powerUp();
        isOn = true;
    }

    public void powerOff() {
        if (!isWriting) {
            sensor.powerDown();
        }
            isOn = false;
    }
}

