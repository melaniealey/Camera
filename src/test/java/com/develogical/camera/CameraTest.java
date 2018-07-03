package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);
        Camera myCamera = new Camera(sensor, null);
        myCamera.powerOn();
        verify(sensor).powerUp();
    }

    @Test
    public void switchingOffPowersDownSensor() {
        Sensor sensor = mock(Sensor.class);
        Camera myCamera = new Camera(sensor, null);
        myCamera.powerOff();
        verify(sensor).powerDown();
    }

    @Test
    public void pressingShutterWhenPowerOnCopiesDataFromSensorToMemory() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);
        Camera myCamera = new Camera(sensor, memoryCard);
        myCamera.powerOn(); // TODO: ask if there is a "verify not called" function if camera isn't on
        myCamera.pressShutter();
        verify(sensor).readData();
        verify(memoryCard).write(any(), any());  //verify that is told to write but don't care which paramater
    }

    @Test
    public void pressingShutterWhenPowerOffDoesNothing() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);
        Camera myCamera = new Camera(sensor, memoryCard);
        myCamera.pressShutter();
        verify(sensor, never()).readData();
        verify(memoryCard, never()).write(any(), any());  //verify that is told to write but don't care which paramater
    }

    @Test
    public void cameraNotPoweredOffWhenDataBeingWritten() {
        Sensor sensor = mock(Sensor.class);
        MemoryCard memoryCard = mock(MemoryCard.class);
        ArgumentCaptor<WriteCompleteListener> writeCompleteListener = ArgumentCaptor.forClass(WriteCompleteListener.class);

        Camera myCamera = new Camera(sensor, memoryCard);

        myCamera.powerOn();
        myCamera.pressShutter();
        myCamera.powerOff();
        verify(memoryCard).write(any(), writeCompleteListener.capture());
        verify(sensor, never()).powerDown();

        writeCompleteListener.getValue().writeComplete();

        verify(sensor).powerDown();
    }
}

