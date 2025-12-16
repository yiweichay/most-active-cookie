package org.quantcast.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MostActiveCookieServiceTest {

    private List<SimpleEntry<String, String>> cookieTimestampPair = new ArrayList<>();
    private MostActiveCookieService mostActiveCookieService;
    private Set<String> expectedMostActiveCookies;

    @BeforeEach
    void setUp() {
        cookieTimestampPair = new ArrayList<>();
        mostActiveCookieService = new MostActiveCookieService();
    }

    @Test
    void shouldFindSingleMostActiveCookie() {
        givenSetUp(Arrays.asList(
                new SimpleEntry<>("AtY0laUfhglK3lC7", "2018-12-12T23:50:00+00:00"),
                new SimpleEntry<>("SAZuXPGUrfbcn5UA", "2018-12-12T22:45:00+00:00"),
                new SimpleEntry<>("5UAVanZf6UtGyKVS", "2018-12-12T21:30:00+00:00"),
                new SimpleEntry<>("AtY0laUfhglK3lC7", "2018-12-12T20:15:00+00:00")
        ));
        whenFindMostActiveCookiesIsRan();
        thenSingleMostActiveCookieIsReturned();
    }

    @Test
    void shouldFindTwoMostActiveCookies() {
        givenSetUp(Arrays.asList(
                new SimpleEntry<>("AtY0laUfhglK3lC7", "2018-12-12T23:50:00+00:00"),
                new SimpleEntry<>("SAZuXPGUrfbcn5UA", "2018-12-12T23:50:00+00:00"),
                new SimpleEntry<>("5UAVanZf6UtGyKVS", "2018-12-12T21:30:00+00:00"),
                new SimpleEntry<>("AtY0laUfhglK3lC7", "2018-12-12T20:15:00+00:00"),
                new SimpleEntry<>("SAZuXPGUrfbcn5UA", "2018-12-12T20:15:00+00:00")
        ));
        whenFindMostActiveCookiesIsRan();
        thenTwoMostActiveCookiesAreReturned();
    }

    @Test
    void shouldFindStartIndexCorrectly() {
        givenSetUp(Arrays.asList(
                new SimpleEntry<>("cookie1", "2018-12-14T10:00:00+00:00"),
                new SimpleEntry<>("cookie2", "2018-12-13T10:00:00+00:00"),
                new SimpleEntry<>("cookie3", "2018-12-12T12:00:00+00:00"),
                new SimpleEntry<>("cookie3", "2018-12-12T10:00:00+00:00"),
                new SimpleEntry<>("cookie4", "2018-12-11T10:00:00+00:00")
        ));
        int startIndex = mostActiveCookieService.findStartIndex(cookieTimestampPair, "2018-12-12");
        assertEquals(2, startIndex);
    }

    @Test
    void shouldFindEndIndexCorrectly() {
        givenSetUp(Arrays.asList(
                new SimpleEntry<>("cookie1", "2018-12-13T10:00:00+00:00"),
                new SimpleEntry<>("cookie2", "2018-12-12T12:00:00+00:00"),
                new SimpleEntry<>("cookie3", "2018-12-12T11:00:00+00:00"),
                new SimpleEntry<>("cookie3", "2018-12-12T10:00:00+00:00"),
                new SimpleEntry<>("cookie4", "2018-12-11T10:00:00+00:00")
        ));
        int endIndex = mostActiveCookieService.findEndIndex(cookieTimestampPair, "2018-12-12");
        assertEquals(4, endIndex);
    }

    private void givenSetUp(List<SimpleEntry<String, String>> cookies) {
        cookieTimestampPair.clear();
        cookieTimestampPair.addAll(cookies);
    }

    private void whenFindMostActiveCookiesIsRan() {
        expectedMostActiveCookies = mostActiveCookieService.findMostActiveCookies(cookieTimestampPair, "2018-12-12");
    }

    private void thenSingleMostActiveCookieIsReturned() {
        assertEquals(1, expectedMostActiveCookies.size());
    }

    private void thenTwoMostActiveCookiesAreReturned() {
        assertEquals(2, expectedMostActiveCookies.size());
    }
}
