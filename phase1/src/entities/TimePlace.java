package entities;

import java.time.*;

/**
 * Represents the time and place of a meetup.
 *
 * @author Maryam
 */
public class TimePlace implements Comparable<TimePlace> {

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
     *
     * @param id    ID of this meetup
     * @param time  Time of this meetup
     * @param place Location of this meetup
     */
    public TimePlace(int id, LocalDateTime time, String place) {
        this.id = id;
        this.place = place;
        this.time = time;
    }

    /**
     * Returns the ID of this meetup.
     *
     * @return ID of this meetup
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the location of this meetup.
     *
     * @return Location of this meetup
     */
    public String getPlace() {
        return place;
    }

    /**
     * Returns the time of this meetup.
     *
     * @return Time of this meetup
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Sets the location of this meetup.
     *
     * @param place Location of this meetup
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Sets the time of this meetup.
     *
     * @param time Time of this meetup
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Creates a string representation of this meetup.
     *
     * @return String representation of this TimePlace object
     */
    @Override
    public String toString() {
        return "TimePlace{" +
                "id=" + id +
                ", time=" + time +
                ", place='" + place + '\'' +
                '}';
    }

    /**
     * Compares two TimePlace objects by their date.
     * An object with a later date is considered 'smaller'.
     *
     * @param other A TimePlace object
     * @return -1 if this TimePlace has a more recent date, 1 if the other has a more recent date, 0 otherwise
     */
    @Override
    public int compareTo(TimePlace other) {
        return -1 * time.compareTo(other.time);
    }
}