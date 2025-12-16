package org.quantcast.service;

import org.quantcast.entity.FileDate;
import org.quantcast.exception.InvalidArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileParserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParserService.class);

    public FileDate parseArgs(final String[] args) {
        String file = null;
        String UTCDate = null;
        LOGGER.info("Parsing arguments: {}", Arrays.toString(args));

        for (int i = 0; i < args.length; i++) {
            if ("-f".equals(args[i]) && i + 1 < args.length) {
                file = args[i + 1];
                i++;
            } else if ("-d".equals(args[i]) && i + 1 < args.length) {
                UTCDate = args[i + 1];
                i++;
            }
        }

        if (file == null || UTCDate == null) {
            LOGGER.error("Invalid arguments. file: {}, date: {}", file, UTCDate);
            throw new InvalidArgumentsException("Please re-enter argument as such: java Main -f <file> -d <date>");
        }
        return new FileDate(file, UTCDate);
    }

    public List<AbstractMap.SimpleEntry<String, String>> readCookies(final String file) throws Exception {
        List<AbstractMap.SimpleEntry<String, String>> cookieTimestampPair = new ArrayList<>();
        LOGGER.info("Reading cookies from file: {}", file);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                cookieTimestampPair.add(new AbstractMap.SimpleEntry<>(values[0], values[1]));
            }
        }
        LOGGER.info("Total cookies read: {}", cookieTimestampPair.size());
        return cookieTimestampPair;
    }
}
