package entities;

import java.time.*;

/**
 * Represents the time and place of a meetup.
 * @author Maryam
 */
public class TimePlace {
    /**
     * The ID of this meetup.
     */
    private final int id;
    /**
     * The time of this meetup.
     */
    private LocalDateTime time;
    /**
     * The location of this meetup.
     */
    private String place;

    /**
     * Creates a TimePlace object based on an ID, the time of the meetup, and the location of the meetup.
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
     * Returns the ID of this meetup.
     * @return The ID of this meetup.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the location of this meetup.
     * @return The location of this meetup.
     */
    public String getPlace() {
        return place;
    }

    /**
     * Returns the time of this meetup.
     * @return The time of this meetup.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets the location of this meetup.
     * @param place The location of this meetup.
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Sets the time of this meetup.
     * @param time The time of this meetup.
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Creates a string representation of this meetup.
     * @return A string representation of this meetup.
     */
    public String toString() {
        return "id: " + id + ", time: " + time.toString() + ", place: " + place;
    }
}