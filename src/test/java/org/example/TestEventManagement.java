package org.example;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEventManagement {

    String antetOutput = "src/main/resources/output/";
    String antetRef = "src/main/resources/references/";

    @Test
    public void emptyOutput() {
        File filesList[] = new File(antetOutput).listFiles();
        for(File file : filesList) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                file.delete();
            }
        }
        assertTrue(true);
    }

    @Test
    public void eventManagement1() throws IOException, ParseException {
        String file = "17_event_management_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }

    @Test
    public void eventManagement2() throws IOException, ParseException {
        String file = "18_event_management_1.txt";
        ManagementPrimarie.main(new String[]{file});
        Reader out = new BufferedReader(new FileReader(antetOutput + file));
        Reader ref = new BufferedReader(new FileReader(antetRef + file));
        assertTrue(IOUtils.contentEqualsIgnoreEOL(out, ref));
    }
}
