package org.quantcast.entity;

public class FileDate {
    private final String file;
    private final String date;

    public FileDate(final String file, final String date) {
        this.file = file;
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public String getDate() {
        return date;
    }
}
