package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFile {
    public static void writeWithAppend(List<String> content, String outputFile) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("src/main/resources/output/" + outputFile, true));

            for (String s: content) {
                out.write(s);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
