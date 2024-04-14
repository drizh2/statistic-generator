package edu.profitsoft;

import edu.profitsoft.models.Field;
import edu.profitsoft.service.FileUtils;
import edu.profitsoft.service.StatisticGenerator;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Started writing statistic...");
        String folderPath = args[0];
        String attribute = args[1];

        Field field = Field.namesMap.get(attribute);
        if (Objects.isNull(field)) {
            LOGGER.info("There are no field with name " + attribute + " in path " + folderPath);
            return;
        }
        List<File> files = FileUtils.getFiles(folderPath);
        if (files.isEmpty()) {
            LOGGER.info("There are no files to process in folder " + folderPath + " !");
            return;
        }

        StatisticGenerator statisticGenerator = new StatisticGenerator();
        long start = System.nanoTime();
        statisticGenerator.generateStatistic(files, field, folderPath);
        long end = System.nanoTime();

        long duration = end - start;
        LOGGER.info("Generation lasts " + duration + " milliseconds");

        LOGGER.info("Finished!");
    }
}