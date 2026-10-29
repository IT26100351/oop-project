package com.shaahid.portal.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// A helper used by EVERY manager. It does the boring file work in one place:
// reading all lines, writing all lines, and making the next id (H001, H002 ...).
public class FileHelper {

    // Resolve file path so it works whether run from root or backend directory
    public static File resolveFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            File backendFile = new File("backend", path);
            if (backendFile.exists()) {
                return backendFile;
            }
        }
        return file;
    }

    // Read every line of a text file into a list. If the file does not exist yet, return an empty list.
    public static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        File file = resolveFile(path);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {   // loop until the file ends
                if (!line.trim().isEmpty()) {              // skip empty lines
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + path + " : " + e.getMessage());
        }
        return lines;
    }

    // Replace the whole file with the given lines.
    public static void writeLines(String path, List<String> lines) {
        File file = resolveFile(path);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();                 // create the data folder if missing
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Could not write " + path + " : " + e.getMessage());
        }
    }

    // Make the next id. Example: prefix "H" and lines starting H001|.. H002|.. gives "H003".
    public static String nextId(String prefix, List<String> lines) {
        int highest = 0;
        for (String line : lines) {
            String id = line.split("\\|")[0];              // the id is the first part of the line
            try {
                int number = Integer.parseInt(id.substring(prefix.length()));
                if (number > highest) {
                    highest = number;
                }
            } catch (NumberFormatException e) {
                // ignore lines with a strange id
            }
        }
        return prefix + String.format("%03d", highest + 1);
    }

    // Our files use "|" to separate fields, so a "|" typed by a user would break the line.
    // This swaps "|" and new lines for safe characters before saving.
    public static String clean(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}
