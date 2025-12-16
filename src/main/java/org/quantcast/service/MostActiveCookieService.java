package org.quantcast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class MostActiveCookieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MostActiveCookieService.class);

    public Set<String> findMostActiveCookies(final List<SimpleEntry<String, String>> tuples, final String UTCDate) {
        LOGGER.info("Finding most active cookies for date: {}", UTCDate);

        final int startIndex = findStartIndex(tuples, UTCDate);
        final int endIndex = findEndIndex(tuples, UTCDate);

        LOGGER.debug("Processing indices from {} to {}", startIndex, endIndex);

        Map<String, Integer> cookieMap = new HashMap<>();
        Set<String> mostActiveCookies = new HashSet<>();
        int maxCount = 0;

        for (int i = startIndex; i < endIndex; i++) {
            final String cookie = tuples.get(i).getKey();
            cookieMap.put(cookie, cookieMap.getOrDefault(cookie, 0) + 1);
            if (cookieMap.get(cookie) == maxCount) {
                mostActiveCookies.add(cookie);
            } else if (cookieMap.get(cookie) > maxCount) {
                mostActiveCookies.clear();
                mostActiveCookies.add(cookie);
                maxCount = cookieMap.get(cookie);
            }
        }
        LOGGER.info("Most active cookies: {} with count {}", mostActiveCookies, maxCount);
        return mostActiveCookies;
    }

    private int findStartIndex(final List<SimpleEntry<String, String>> tuples, final String UTCDate) {
        int left = 0;
        int right = tuples.size();

        while (left < right) {
            final int mid = (left + right) / 2;
            final String midDate = tuples.get(mid).getValue();

            if (midDate.compareTo(UTCDate) > 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int findEndIndex(final List<SimpleEntry<String, String>> tuples, final String UTCDate) {
        int left = 0;
        int right = tuples.size();

        while (left < right) {
            final int mid = (left + right) / 2;
            final String midDate = tuples.get(mid).getValue();

            if (midDate.compareTo(UTCDate) >= 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
