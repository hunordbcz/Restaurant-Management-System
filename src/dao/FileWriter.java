package dao;

import bll.Restaurant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class FileWriter {

    private final PrintWriter outputText;
    private final FileOutputStream outputFile;
    private final ObjectOutputStream outputObj;

    public FileWriter(String output) throws IOException {
        outputText = new PrintWriter(output);
        outputFile = new FileOutputStream(output);
        outputObj = new ObjectOutputStream(this.outputFile);
    }

    public void outText(String obj) {
        outputText.println(obj);
    }

    public void out(Object obj) throws IOException {
        this.outputObj.writeObject((Restaurant) obj);
        outputObj.flush();
    }

    public void close() {
        try {
            outputText.close();
            outputObj.close();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
