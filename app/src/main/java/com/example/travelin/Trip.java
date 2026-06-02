package com.example.travelin;

public class Trip {
    private final String section;
    private final String name;
    private final String dates;
    private final String locations;
    private final int imageResId;

    public Trip(String section, String name, String dates, String locations, int imageResId) {
        this.section = section;
        this.name = name;
        this.dates = dates;
        this.locations = locations;
        this.imageResId = imageResId;
    }

    public String getSection() {
        return section;
    }

    public String getName() {
        return name;
    }

    public String getDates() {
        return dates;
    }

    public String getLocations() {
        return locations;
    }

    public int getImageResId() {
        return imageResId;
    }
}
