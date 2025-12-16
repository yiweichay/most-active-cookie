package org.quantcast.service;

import org.quantcast.entity.FileDate;
import org.quantcast.exception.InvalidArgumentsException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class FileParserService {

    public FileDate parseArgs(final String[] args) {
        String file = null;
        String UTCDate = null;

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
            throw new InvalidArgumentsException("Please re-enter argument as such: java Main -f <file> -d <date>");
        }
        return new FileDate(file, UTCDate);
    }

    public List<AbstractMap.SimpleEntry<String, String>> readCookies(final String file) throws Exception {
        List<AbstractMap.SimpleEntry<String, String>> cookieTimestampPair = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                cookieTimestampPair.add(new AbstractMap.SimpleEntry<>(values[0], values[1]));
            }
        }
        return cookieTimestampPair;
    }
}
