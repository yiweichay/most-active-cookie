package org.quantcast.service;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class MostActiveCookieService {

    public Set<String> findMostActiveCookies(final List<SimpleEntry<String, String>> tuples, final String UTCDate) {

        final String dayStart = UTCDate + "T23:59:59+00:00";
        final String dayEnd   = UTCDate + "T00:00:00+00:00";

        final int startIndex = lowerBound(tuples, dayStart);
        final int endIndex = upperBound(tuples, dayEnd);
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
        return mostActiveCookies;
    }

    private int lowerBound(final List<SimpleEntry<String, String>> tuples, final String UTCDate) {
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

    private int upperBound(final List<SimpleEntry<String, String>> tuples, final String UTCDate) {
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
