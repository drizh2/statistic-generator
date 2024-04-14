package edu.profitsoft.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import edu.profitsoft.models.Field;
import edu.profitsoft.models.Statistic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Utility class for generating statistics from JSON files and writing them to XML.
 */
public final class StatisticGenerator {
    private static final Logger LOGGER = Logger.getLogger(StatisticGenerator.class.getName());
    private static final int THREAD_COUNT = 8;

    private final Map<String, Integer> globalStatisticsMap = new HashMap<>();

    /**
     * Generates statistics from JSON files based on the specified field and writes them to XML.
     *
     * @param files      The list of JSON files to generate statistics from.
     * @param field      The field to generate statistics for.
     * @param folderPath The folder path where the output XML file will be written.
     */
    public void generateStatistic(List<File> files, Field field, String folderPath) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(files.size());

        for (File file : files) {
            executorService.execute(() -> {
                try {
                    Map<String, Integer> fileStatisticsMap = JsonUtils.parseJson(file, field);
                    putFileStatisticsToGlobalMap(fileStatisticsMap);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        } finally {
            executorService.shutdown();
        }

        if (!globalStatisticsMap.isEmpty()) {
            List<Statistic> statisticList = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : globalStatisticsMap.entrySet()) {
                statisticList.add(new Statistic(entry.getKey(), entry.getValue()));
            }

            writeValuesToXML(statisticList, field.getFieldName(), folderPath);
        } else {
            LOGGER.info("Could not find statistic!");
        }
    }

    /**
     * Adds file statistics to the global statistics map.
     *
     * @param fileStatisticsMap The statistics map for a single file.
     */
    private synchronized void putFileStatisticsToGlobalMap(Map<String, Integer> fileStatisticsMap) {
        for (String attribute : fileStatisticsMap.keySet()) {
            Integer value = fileStatisticsMap.get(attribute);
            globalStatisticsMap.merge(attribute, value, Integer::sum);
        }
    }

    /**
     * Writes statistic values to an XML file.
     *
     * @param statisticList The list of statistics to write.
     * @param attributeName The name of the attribute for which statistics are generated.
     * @param folderPath    The folder path where the output XML file will be written.
     */
    private void writeValuesToXML(List<Statistic> statisticList, String attributeName, String folderPath) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            File file = new File(folderPath + "/out/statistic_by_" + attributeName + ".xml");

            xmlMapper.writerWithDefaultPrettyPrinter().withRootName("Statistics").writeValue(file, statisticList);
        } catch (IOException e) {
            LOGGER.info("Error writing statistics file: " + e.getMessage());
        }
    }
}
