package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferExample {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(5);
//        buffer.put((byte) 65);
//        buffer.put((byte) 66);
//        buffer.put((byte) 67);
        buffer.putInt(12425);
        buffer.flip();

//        while (buffer.hasRemaining()){
//            System.out.println(buffer.get());
//        }

        RandomAccessFile raf = new RandomAccessFile("server/serverFiles/test.txt", "rw");
        FileChannel channel = raf.getChannel();
        channel.write(buffer);

        channel.position(0);

        buffer.clear();
        channel.read(buffer);
        buffer.rewind();


            System.out.println(buffer.getInt());




//        buffer.rewind();
//        while (buffer.hasRemaining()){
//            System.out.println((char) buffer.get());
//        }
//        buffer.clear();
//        while (buffer.hasRemaining()){
//            System.out.println((char) buffer.get());
//        }
    }
}
