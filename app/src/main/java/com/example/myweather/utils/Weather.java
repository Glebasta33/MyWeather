package com.example.myweather.utils;

public class Weather {
    private double lon;
    private double lat;
    private String description;
    private double temp;
    private double tempFeelsLike;
    private double pressure;
    private double humidity;
    private double speedOfWind;
    private double directionOfWind;
    private String nameOfCity;
    private String icon;

    public Weather(){}

    public Weather(double lon, double lat, String description, double temp, double tempFeelsLike, double pressure, double humidity, double speedOfWind, double directionOfWind, String nameOfCity, String icon) {
        this.lon = lon;
        this.lat = lat;
        this.description = description;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speedOfWind = speedOfWind;
        this.directionOfWind = directionOfWind;
        this.nameOfCity = nameOfCity;
        this.icon = icon;
    }

    public Weather(double temp) {
        this.temp = temp;
    }

    public Weather(double temp, String icon) {
        this.temp = temp;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempFeelsLike() {
        return tempFeelsLike;
    }

    public void setTempFeelsLike(double tempFeelsLike) {
        this.tempFeelsLike = tempFeelsLike;
    }

    public double getPressure() {
        return pressure * 0.75;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSpeedOfWind() {
        return speedOfWind;
    }

    public void setSpeedOfWind(double speedOfWind) {
        this.speedOfWind = speedOfWind;
    }

    public double getDirectionOfWind() {
        return directionOfWind;
    }

    public String getStringDirectionOfWind() {
        String[] directions = new String[]{"↑ С", "↗ СВ", "→ В", "↘ ЮВ", "↓ Ю", "↙ ЮЗ", "← З", "↖ СЗ"};
        return directions[(int) (Math.round(directionOfWind / 45) % 8)];
    }

    public void setDirectionOfWind(double directionOfWind) {
        this.directionOfWind = directionOfWind;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getNameOfCity() {
        return nameOfCity;
    }

    public void setNameOfCity(String nameOfCity) {
        this.nameOfCity = nameOfCity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconPath() {
        return NetworkUtils.BASE_URL_IMAGE + icon + NetworkUtils.URL_PNG;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                ", temp=" + temp +
                ", tempFeelsLike=" + tempFeelsLike +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", speedOfWind=" + speedOfWind +
                ", directionOfWind=" + directionOfWind +
                '}';
    }
}
