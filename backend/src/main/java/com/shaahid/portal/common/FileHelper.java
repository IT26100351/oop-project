package com.shaahid.portal.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * UTILITY & DATA PERSISTENCE HELPER
 * Group: SE1020 Group WD04
 * Component: Shared FileHelper
 * ============================================================================
 * 
 * Provides centralized flat-file persistence logic used by all Managers:
 * 1. Multi-path path resolution (resolves whether run from root or backend directory)
 * 2. Safe file reading and writing with auto-directory creation
 * 3. Auto-increment ID generation (e.g., H001 -> H002, U001 -> U002)
 * 4. Input sanitization to prevent delimiter injection (|) in .txt files
 */
public class FileHelper {

    /**
     * Resolves the target file path across different execution contexts:
     * - From root directory (e.g., "data/houses.txt")
     * - From backend directory (e.g., "backend/data/houses.txt")
     * - From sub-directories (e.g., "../data/houses.txt")
     *
     * @param path Relative path to the file.
     * @return Resolved File object pointing to the existing or expected path.
     */
    public static File resolveFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        File backendFile = new File("backend", path);
        if (backendFile.exists()) {
            return backendFile;
        }
        File parentFile = new File("..", path);
        if (parentFile.exists()) {
            return parentFile;
        }
        return file;
    }

    /**
     * Reads all non-empty lines from the given text file.
     * Returns an empty list if the file does not exist yet.
     *
     * @param path File path.
     * @return List of strings representing lines in the file.
     */
    public static List<String> readLines(String path) {
        List<String> lines = new ArrayList<>();
        File file = resolveFile(path);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + path + ": " + e.getMessage());
        }
        return lines;
    }

    /**
     * Overwrites the specified file with the provided lines.
     * Automatically creates parent directories if they do not exist.
     *
     * @param path Destination file path.
     * @param lines Lines of text to write.
     */
    public static void writeLines(String path, List<String> lines) {
        File file = resolveFile(path);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error writing " + path + ": " + e.getMessage());
        }
    }

    /**
     * Automatically generates the next formatted ID in sequence.
     * Example: given prefix "H" and lines ["H001|...", "H002|..."], returns "H003".
     *
     * @param prefix Identifier prefix (e.g., "H", "A", "L", "U", "O", "R").
     * @param lines Existing data lines containing IDs as the first token.
     * @return Formatted next ID string (e.g., "H003").
     */
    public static String nextId(String prefix, List<String> lines) {
        int highest = 0;
        for (String line : lines) {
            String id = line.split("\\|")[0];
            try {
                int number = Integer.parseInt(id.substring(prefix.length()));
                if (number > highest) {
                    highest = number;
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // Ignore improperly formatted IDs
            }
        }
        return prefix + String.format("%03d", highest + 1);
    }

    /**
     * Sanitizes input text to prevent pipe (|) and newline injection into flat-file records.
     *
     * @param text Raw user input string.
     * @return Sanitized text safe for pipe-delimited storage.
     */
    public static String clean(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("|", "/")
                   .replace("\n", " ")
                   .replace("\r", " ")
                   .trim();
    }
}
