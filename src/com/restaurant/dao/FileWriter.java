package com.restaurant.dao;

import com.restaurant.util.Constants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileWriter {

    private final FileOutputStream outputFile;
    private final ObjectOutputStream outputObj;

    public FileWriter(String output) throws IOException {
        this.outputFile = new FileOutputStream(output);
        outputObj = new ObjectOutputStream(this.outputFile);
    }

    public void out(Object obj) throws IOException {
        this.outputObj.writeObject(obj);
    }

    public void close() {
        try {
            outputFile.close();
            outputObj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
