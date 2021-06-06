package uiText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Třída obsahující logiku zaznamenávání uživ. vstupu a výstupu
 */
public class FileManipulator {

    private File file;
    public boolean created;
    private FileWriter fw;

    public FileManipulator() {
        this.created = false;
    }

    /**
     * Inicializuje path souboru a vytváří File a FileWriter
     */
    public void init() {
        System.out.println("Zadejte path pro soubor, který bude zaznamenávat input a output:");
        try {
            this.file = new File(new Scanner(System.in).nextLine());
            if (file.createNewFile()) {
                System.out.println("Úspěšně vytvořen soubor na adrese: " + file.getAbsolutePath());
                this.created = true;
            }
            else {
                System.out.println("Tento soubor již existuje");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.fw = new FileWriter(this.file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro zápis do souboru
     * @param toWrite String, který se propíše do souboru
     */
    public void write(String toWrite) {
        try {
            this.fw.write(toWrite + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uzavření FileWriteru
     */
    public void close() {
        try {
            this.fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
