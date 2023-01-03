package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Creates a new file if not created or adds lines to an existing file.
 */
public class WriteFile {
    public static void writeWithAppend(List<String> content, String outputFile) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter("src/main/resources/output/" + outputFile, true);
            bw = new BufferedWriter(fw);

            for (String s : content) {
                System.out.print(s);
                bw.append(s);
            }
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
