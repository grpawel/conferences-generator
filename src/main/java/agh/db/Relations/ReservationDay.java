package agh.db.Relations;

/**
 * Created by Paweł Grochola on 31.01.2017.
 */
public class ReservationDay {
    public ReservationDay(Attendee attendee, Integer id) {
        this.attendee = attendee;
        this.id = id;
    }

    public final Attendee attendee;
    public final Integer id;
}
