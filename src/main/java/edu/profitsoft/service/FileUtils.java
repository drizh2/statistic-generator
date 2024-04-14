package edu.profitsoft.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility class for working with files.
 */
public class FileUtils {

    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    private FileUtils() {
        // Private constructor to prevent instantiation of this utility class.
    }

    /**
     * Retrieves a list of files from the specified folder path.
     *
     * @param folderPath The path to the folder from which files are to be retrieved.
     * @return A list of File objects representing the files in the folder.
     */
    public static List<File> getFiles(String folderPath) {
        List<Path> filePaths = new ArrayList<>();

        try {
            // List all files in the specified folder path and filter out regular files directly in the folder
            filePaths = Files.list(Paths.get(folderPath))
                    .filter(path -> Files.isRegularFile(path) && path.getParent().equals(Paths.get(folderPath)))
                    .toList();
        } catch (IOException e) {
            // Log an informative message if an IOException occurs while listing files
            LOGGER.info("Cannot get files from folder: " + folderPath);
        }

        // Convert the list of Path objects to a list of File objects and return
        return filePaths.stream()
                .map(Path::toFile)
                .toList();
    }
}
