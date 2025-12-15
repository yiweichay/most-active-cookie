package org.quantcast.entity;

public class FileDate {
    private final String date;
    private final String file;

    public FileDate(final String date, final String file) {
        this.date = date;
        this.file = file;
    }

    public String getDate() {
        return date;
    }

    public String getFile() {
        return file;
    }
}
