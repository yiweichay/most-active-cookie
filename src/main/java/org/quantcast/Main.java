package org.quantcast;

import org.quantcast.entity.FileDate;
import org.quantcast.service.FileParserService;
import org.quantcast.service.MostActiveCookieService;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Set;

public class Main {
    private final FileParserService fileParserService;
    private final MostActiveCookieService mostActiveCookieService;

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
        final FileDate fileDate = fileParserService.parseArgs(args);
        final List<SimpleEntry<String, String>> cookieTimeStampPair = fileParserService.readCookies(fileDate.getFile());
        final Set<String> result = mostActiveCookieService.findMostActiveCookies(cookieTimeStampPair, fileDate.getDate());
        printResult(result);
    }

    private void printResult(final Set<String> result) {
        for (final String s : result) {
            System.out.println(s);
        }
    }
}