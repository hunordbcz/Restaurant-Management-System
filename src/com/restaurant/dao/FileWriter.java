package com.restaurant.dao;

import com.restaurant.bll.MenuItem;
import com.restaurant.bll.Restaurant;
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
        this.outputObj.writeObject((Restaurant)obj);
        outputObj.flush();
    }

    public void close() {
        try {
            outputObj.close();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
