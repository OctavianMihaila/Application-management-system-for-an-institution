package Utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.StreamHandler;

public class WriteFile {
    public static void writeWithAppend(List<String> content, String outputFile) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter("src/main/resources/output/" + outputFile, true);
            bw = new BufferedWriter(fw);

//            BufferedReader in = new BufferedReader(new FileReader("src/main/resources/output/" + outputFile));
//            System.out.println(in.readLine());

            for (String s: content) {
                System.out.print(s);
                bw.append(s);
            }
//            out.flush();
//            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
