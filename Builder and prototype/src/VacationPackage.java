package model;

import java.util.ArrayList;
import java.util.List;

public class VacationPackage extends TravelPackage {

    private final String destination;
    private final int days;
    private final String hotel;
    private final boolean flightIncluded;
    private final List<String> activities;
    private final String imagePath; // nueva ruta opcional a imagen

    // private constructor
    private VacationPackage(Builder builder) {
        this.destination = builder.destination;
        this.days = builder.days;
        this.hotel = builder.hotel;
        this.flightIncluded = builder.flightIncluded;
        this.activities = new ArrayList<>(builder.activities);
        this.imagePath = builder.imagePath;
    }

    // Getters
    public String getDestination() { return destination; }
    public int getDays() { return days; }
    public String getHotel() { return hotel; }
    public boolean isFlightIncluded() { return flightIncluded; }
    public List<String> getActivities() { return new ArrayList<>(activities); }
    public String getImagePath() { return imagePath; }

    // PROTOTYPE
    @Override
    public TravelPackage clone() {
        return new Builder()
                .setDestination(this.destination)
                .setDays(this.days)
                .setHotel(this.hotel)
                .setFlightIncluded(this.flightIncluded)
                .addActivities(this.activities)
                .setImagePath(this.imagePath)
                .build();
    }

    @Override
    public String toString() {
        return "VacationPackage {" +
                "destination='" + destination + '\'' +
                ", days=" + days +
                ", hotel='" + hotel + '\'' +
                ", flightIncluded=" + flightIncluded +
                ", activities=" + activities +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    // BUILDER
    public static class Builder {
        private String destination;
        private int days;
        private String hotel;
        private boolean flightIncluded;
        private List<String> activities = new ArrayList<>();
        private String imagePath = null;

        public Builder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder setDays(int days) {
            this.days = days;
            return this;
        }

        public Builder setHotel(String hotel) {
            this.hotel = hotel;
            return this;
        }

        public Builder setFlightIncluded(boolean included) {
            this.flightIncluded = included;
            return this;
        }

        public Builder addActivity(String activity) {
            this.activities.add(activity);
            return this;
        }

        public Builder addActivities(List<String> activities) {
            this.activities.addAll(activities);
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public VacationPackage build() {
            if (destination == null || days <= 0) {
                throw new IllegalStateException("Destino y días son obligatorios");
            }
            return new VacationPackage(this);
        }
    }
}