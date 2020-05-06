package com.restaurant.dao;

import com.restaurant.util.Constants;

import java.io.*;

public class FileReader<T> {
    private final FileInputStream inputFile;
    private final ObjectInputStream inputObj;

    public FileReader(String input) throws IOException {
        this.inputFile = new FileInputStream(input);
        inputObj = new ObjectInputStream(this.inputFile);
    }

    public T in() throws IOException, ClassNotFoundException {
        return (T)inputObj.readObject();
    }

    public void close() {
        try {
            inputFile.close();
            inputObj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
