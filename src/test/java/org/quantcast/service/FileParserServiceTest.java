package org.quantcast.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quantcast.entity.FileDate;

import java.io.File;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileParserServiceTest {

    private final FileParserService fileParserService = new FileParserService();
    private String[] args;
    private FileDate fileDate;
    private File file;
    private List<AbstractMap.SimpleEntry<String, String>> cookieTimeStampPair;

    @Test
    void shouldParseArgumentsCorrectly() {
        givenArguments(new String[]{"-f", "cookie.csv", "-d", "2025-12-16"});
        whenParseArgsIsCalled();
        thenFileDateIsReturnedCorrectly();
    }

    @Test
    void shouldReadCookiesFromFile() throws Exception {
        givenFile("cookies.csv");
        whenReadCookiesIsCalled();
        thenCookieTimestampPairIsReturnedCorrectly();
    }

    private void givenArguments(final String[] arguments) {
        args = arguments;
    }

    private void givenFile(final String fileName) throws Exception {
        URL resource = getClass().getClassLoader().getResource(fileName);
        Assertions.assertNotNull(resource);
        file = new File(resource.toURI());
    }

    private void whenParseArgsIsCalled() {
        fileDate = fileParserService.parseArgs(args);
    }

    private void whenReadCookiesIsCalled() throws Exception {
        cookieTimeStampPair = fileParserService.readCookies(file.getPath());
    }

    private void thenFileDateIsReturnedCorrectly() {
        assertEquals("cookie.csv", fileDate.getFile());
        assertEquals("2025-12-16", fileDate.getDate());
    }

    private void thenCookieTimestampPairIsReturnedCorrectly() {
        assertEquals(8, cookieTimeStampPair.size());
        assertEquals("AtY0laUfhglK3lC7", cookieTimeStampPair.get(0).getKey());
        assertEquals("2018-12-09T14:19:00+00:00", cookieTimeStampPair.get(0).getValue());
        assertEquals("SAZuXPGUrfbcn5UA", cookieTimeStampPair.get(1).getKey());
        assertEquals("2018-12-09T10:13:00+00:00", cookieTimeStampPair.get(1).getValue());
    }
}
