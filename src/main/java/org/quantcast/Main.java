package org.quantcast;

import org.quantcast.entity.FileDate;
import org.quantcast.exception.InvalidArgumentsException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import static org.quantcast.service.MostActiveCookieService.findMostActiveCookies;

public class Main {
    public static void main(String[] args) throws Exception {
        FileDate fileDate = getFileAndDate(args);
        List<SimpleEntry<String, String>> cookieTimeStampPair = readCookies(fileDate.getFile());
        final Set<String> result = findMostActiveCookies(cookieTimeStampPair, fileDate.getDate());
        printResult(result);
    }

    private static FileDate getFileAndDate(final String[] args) {
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

    private static List<SimpleEntry<String, String>> readCookies(final String file) throws Exception {
        List<SimpleEntry<String, String>> cookieTimestampPair = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                cookieTimestampPair.add(new SimpleEntry<>(values[0], values[1]));
            }
        }
        return cookieTimestampPair;
    }

    private static void printResult(final Set<String> result) {
        for (final String s : result) {
            System.out.println(s);
        }
    }
}