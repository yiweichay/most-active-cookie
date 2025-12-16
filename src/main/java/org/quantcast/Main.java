package org.quantcast;

import org.quantcast.entity.FileDate;
import org.quantcast.service.FileParserService;
import org.quantcast.service.MostActiveCookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    private final FileParserService fileParserService;
    private final MostActiveCookieService mostActiveCookieService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public Main(final FileParserService fileParserService,
                final MostActiveCookieService mostActiveCookieService) {
        this.fileParserService = fileParserService;
        this.mostActiveCookieService = mostActiveCookieService;
    }

    public static void main(final String[] args) throws Exception {
        Main main = new Main(new FileParserService(), new MostActiveCookieService());
        main.run(args);
    }

    public void run(final String[] args) throws Exception {
        LOGGER.info("Application started with arguments: {}", Arrays.toString(args));
        final FileDate fileDate = fileParserService.parseArgs(args);
        final List<SimpleEntry<String, String>> cookieTimeStampPair = fileParserService.readCookies(fileDate.getFile());
        final Set<String> result = mostActiveCookieService.findMostActiveCookies(cookieTimeStampPair, fileDate.getDate());
        printResult(result);
        LOGGER.info("Application finished successfully");
    }

    private void printResult(final Set<String> result) {
        LOGGER.info("Printing results:");
        for (final String s : result) {
            System.out.println(s);
        }
    }
}