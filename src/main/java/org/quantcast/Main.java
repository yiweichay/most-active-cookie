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

    public Main(FileParserService fileParserService, MostActiveCookieService mostActiveCookieService) {
        this.fileParserService = fileParserService;
        this.mostActiveCookieService = mostActiveCookieService;
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main(new FileParserService(), new MostActiveCookieService());
        main.run(args);
    }

    public void run(String[] args) throws Exception {
        FileDate fileDate = fileParserService.parseArgs(args);
        List<SimpleEntry<String, String>> cookieTimeStampPair = fileParserService.readCookies(fileDate.getFile());
        final Set<String> result = mostActiveCookieService.findMostActiveCookies(cookieTimeStampPair, fileDate.getDate());
        printResult(result);
    }

    private void printResult(final Set<String> result) {
        for (final String s : result) {
            System.out.println(s);
        }
    }
}