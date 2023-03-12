package com.varukha.webproject.util.calculator.util;

public enum CityCoordinates {

    SUMY(50.9077, 34.7981),
    CHARKIV(49.9935, 36.2304),
    LVIV(49.8397, 24.0297),
    ODESSA(46.4694, 30.7409);

    private final double latitude;
    private final double longitude;

    CityCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
}
