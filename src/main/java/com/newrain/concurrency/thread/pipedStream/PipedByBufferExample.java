package com.newrain.concurrency.thread.pipedStream;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 线程间通信 管道字符流
 * PipedReader
 * PipedWriter
 *
 * @author newRain
 * @description 线程间通信 管道字符流
 */
@Slf4j
public class PipedByBufferExample {

    public static void main(String[] args) {
        WriterBuffer writerBuffer = new WriterBuffer();
        ReadBuffer readBuffer = new ReadBuffer();

        PipedReader pipedReader = new PipedReader();
        PipedWriter pipedWriter = new PipedWriter();
        try {
            pipedReader.connect(pipedWriter);
            ReadBufferThread readBufferThread = new ReadBufferThread(readBuffer, pipedReader);
            readBufferThread.start();
            //此处可调整 调整时长
            Thread.sleep(100);
            WriteBufferThread writeBufferThread = new WriteBufferThread(writerBuffer, pipedWriter);
            writeBufferThread.start();
        } catch (IOException | InterruptedException e) {
            log.error("error", e);
        }
    }
}

@Slf4j
class WriterBuffer {
    public void writeMethod(PipedWriter writer) {
        try {
            log.debug("PipedWriter writer start ");
            for (int i = 0; i < 300; i++) {
                String outData = "" + (i + 1);
                writer.write(outData);
                log.debug("write:{}", outData);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@Slf4j
class ReadBuffer {
    public void readMethod(PipedReader pipedReader) {
        try {
            log.debug("ReadBuffer start read");
            char[] bytes = new char[3];
            //当没有数据写入时 线程会阻塞在此处
            int readLen = pipedReader.read(bytes);
            while (readLen != -1) {
                String newData = new String(bytes, 0, readLen);
                System.out.println("readData:" + newData);
                log.debug("readData:{}", newData);
                readLen = pipedReader.read(bytes);
            }
            System.out.println();
            pipedReader.close();
        } catch (IOException e) {
            log.error("readMethod error:", e);
        }
    }
}

class WriteBufferThread extends Thread {
    private final WriterBuffer readBuffer;
    private final PipedWriter outputStream;

    public WriteBufferThread(WriterBuffer writeData, PipedWriter outputStream) {
        this.readBuffer = writeData;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        readBuffer.writeMethod(outputStream);
    }
}

class ReadBufferThread extends Thread {
    private final ReadBuffer readData;
    private final PipedReader pipedReader;

    public ReadBufferThread(ReadBuffer readData, PipedReader pipedReader) {
        this.pipedReader = pipedReader;
        this.readData = readData;
    }

    @Override
    public void run() {
        readData.readMethod(pipedReader);
    }
}