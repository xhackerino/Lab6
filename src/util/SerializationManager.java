package util;

import java.io.*;

public class SerializationManager<T> {
    public T readObject(byte[] data) throws IOException, ClassNotFoundException, ClassCastException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream obs = new ObjectInputStream(byteStream);
//        obs.close();
        return (T) obs.readObject();
    }

    public byte[] writeObject(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
//        objectOutputStream.close();
        return outputStream.toByteArray();
    }
}

