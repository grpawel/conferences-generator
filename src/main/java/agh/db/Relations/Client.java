package agh.db.Relations;

import java.util.List;

public class Client {
    private final Integer clientID;
    private final String name;
    private final String login;
    private final String password;
    private final Boolean isCompany;
    private final String email;
    private final String phone;
    public final List<Attendee> attendeeList;

    public Client(Integer clientID, String name, String login, String password, Boolean isCompany, String email, String phone, List<Attendee> attendeeList) {
        this.clientID = clientID;
        this.name = name;
        this.login = login;
        this.password = password;
        this.isCompany = isCompany;
        this.email = email;
        this.phone = phone;
        this.attendeeList = attendeeList;
    }
}
