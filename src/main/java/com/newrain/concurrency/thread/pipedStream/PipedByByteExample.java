package com.newrain.concurrency.thread.pipedStream;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 线程管道通信 字节流
 * PipedInputStream
 * PipedOutputStream
 *
 * @author newRain
 * @description 线程管道通信 字节流
 */

@Slf4j
public class PipedByByteExample {

    public void writeMethod(PipedOutputStream outputStream) {
        try {
            log.debug("outputStream write start...");
            for (int i = 0; i < 300; i++) {
                String outData = "" + (i + 1);
                outputStream.write(outData.getBytes());
                log.debug("outputStream write:{}", outData.getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PipedByByteExample pipedByByteExample = new PipedByByteExample();
        ReadData readData = new ReadData();

        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();
        try {
            outputStream.connect(inputStream);
//            inputStream.connect(outputStream);
            ReadThread readThread = new ReadThread(readData, inputStream);
            readThread.start();
            Thread.sleep(2000);
            WriteThread writeThread = new WriteThread(pipedByByteExample, outputStream);
            writeThread.start();
        } catch (IOException | InterruptedException e) {
            log.error("error:", e);
        }

    }

}

@Slf4j
class ReadData {
    public void readMethod(PipedInputStream inputStream) {
        try {
            byte[] bytes = new byte[20];
            //当没有数据写入时 线程会阻塞在此处
            int readLen = inputStream.read(bytes);
            while (readLen != -1) {
                String newData = new String(bytes, 0, readLen);
                log.debug("readData:{}", newData);
                readLen = inputStream.read(bytes);
            }
            inputStream.close();
        } catch (IOException e) {
            log.error("readMethod error:", e);
        }
    }
}

class WriteThread extends Thread {
    private final PipedByByteExample pipedByByteExample;
    private final PipedOutputStream outputStream;

    public WriteThread(PipedByByteExample pipedByByteExample, PipedOutputStream outputStream) {
        this.pipedByByteExample = pipedByByteExample;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        pipedByByteExample.writeMethod(outputStream);
    }
}

class ReadThread extends Thread {
    private final ReadData readData;
    private final PipedInputStream inputStream;

    public ReadThread(ReadData readData, PipedInputStream inputStream) {
        this.inputStream = inputStream;
        this.readData = readData;
    }

    @Override
    public void run() {
        readData.readMethod(inputStream);
    }
}