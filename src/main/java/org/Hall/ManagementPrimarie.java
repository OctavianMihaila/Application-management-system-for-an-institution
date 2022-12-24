package org.Hall;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class ManagementPrimarie {

    public static void main(String[] args) throws IOException, ParseException {
        String inputFile = args[0];

        try {
            String line;
            BufferedReader reader =
                    new BufferedReader(new FileReader("src/main/resources/input/" + inputFile));

            while ((line = reader.readLine()) != null) {
                String request = line.substring(0, line.indexOf(";"));

                switch (request) {
                    case "adauga_functionar":
                        // TO DO
                        break;

                    case "adauga_utilizator":
                        // TO DO
                        break;

                    case "cerere_noua":
                        // TO DO
                        break;

                    case "retrage_cerere":
                        // TO DO
                        break;

                    case "rezolva_cerere":
                        // TO DO
                        break;

                    case "afiseaza_cereri_in_asteptare":
                        // TO DO
                        break;

                    case "afiseaza_cereri_finalizate":
                        // TO DO
                        break;

                    default:
                        throw new ParseException("Invalid request", 0);
                }
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}