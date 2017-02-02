package agh.db.Relations;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class Attendee {
    public final String name;
    public final Integer attendeeID;
    public final String studentCard;
    public final transient Integer clientID;

    public Attendee(String name, Integer attendeeID, String studentCard, Integer clientID) {
        this.name = name;
        this.attendeeID = attendeeID;
        this.studentCard = studentCard;
        this.clientID = clientID;
    }
}
