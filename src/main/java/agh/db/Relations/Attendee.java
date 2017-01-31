package agh.db.Relations;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class Attendee {
    private final String name;
    private final Integer attendeeID;
    public final String studentCard;

    public Attendee(String name, Integer attendeeID, String studentCard) {
        this.name = name;
        this.attendeeID = attendeeID;
        this.studentCard = studentCard;
    }
}
