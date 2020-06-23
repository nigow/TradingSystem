package entities;

import java.time.*;

public class TimePlace {
    /**
     * The ID of this meetup.
     */
    final private int id;
    /**
     * The time of this meetup.
     */
    private LocalDateTime time;
    /**
     * The location of this meetup.
     */
    private String place;

    /**
     * @param id The ID of this meetup.
     * @param time The time of this meetup.
     * @param place The location of this meetup.
     */
    public TimePlace(int id, LocalDateTime time, String place) {
        this.id = id;
        this.place = place;
        this.time = time;
    }

    /**
     * @return The ID of this meetup.
     */
    public int getId() {
        return id;
    }

    /**
     * @return The location of this meetup.
     */
    public String getPlace() {
        return place;
    }

    /**
     * @return The time of this meetup.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @param place The location of this meetup.
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @param time The time of this meetup.
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * @return A string representation of this meetup.
     */
    public String toString() {
        return "id: " + id + ", time: " + time.toString() + ", place: " + place;
    }
}