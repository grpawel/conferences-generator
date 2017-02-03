package agh.db.Relations;

public class Attendee {
    public final String name;
    public final Integer attendeeID;
    public final String studentCard;
    public final Integer clientID;

    public Attendee(String name, Integer attendeeID, String studentCard, Integer clientID) {
        this.name = name;
        this.attendeeID = attendeeID;
        this.studentCard = studentCard;
        this.clientID = clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attendee attendee = (Attendee) o;

        if (!name.equals(attendee.name)) return false;
        if (!attendeeID.equals(attendee.attendeeID)) return false;
        if (studentCard != null ? !studentCard.equals(attendee.studentCard) : attendee.studentCard != null)
            return false;
        return clientID.equals(attendee.clientID);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + attendeeID.hashCode();
        result = 31 * result + (studentCard != null ? studentCard.hashCode() : 0);
        result = 31 * result + clientID.hashCode();
        return result;
    }
}
