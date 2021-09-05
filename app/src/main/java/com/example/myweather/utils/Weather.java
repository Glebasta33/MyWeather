package com.example.myweather.utils;

public class Weather {
    private String description;
    private double temp;
    private double tempFeelsLike;
    private double pressure;
    private double humidity;
    private double speedOfWind;
    private double directionOfWind;

    public Weather(String description, double temp, double tempFeelsLike, double pressure, double humidity, double speedOfWind, double directionOfWind) {
        this.description = description;
        this.temp = temp;
        this.tempFeelsLike = tempFeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speedOfWind = speedOfWind;
        this.directionOfWind = directionOfWind;
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
        return pressure;
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

    // TODO: написать метод, возвращающий название направления ветра
    public double getDirectionOfWind() {
        return directionOfWind;
    }

    public void setDirectionOfWind(double directionOfWind) {
        this.directionOfWind = directionOfWind;
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
