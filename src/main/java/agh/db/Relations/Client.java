package agh.db.Relations;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Client {
    public final Integer clientID;
    public final String name;
    public final String login;
    public final String password;
    public final String email;
    public final String phone;
    public final List<Attendee> attendeeList;

    public Client(Integer clientID, String name, String login, String password, String email, String phone, List<Attendee> attendeeList) {
        this.clientID = clientID;
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.attendeeList = Collections.unmodifiableList(attendeeList);
    }

    public String toSQL() {
        String result = "";
        result += "PRINT 'Inserting client #" + clientID + "'\n";
        result += "SET IDENTITY_INSERT Clients ON\n";
        result += "INSERT INTO Clients (ClientID, Name, Login, Password, IsCompany, Email, Phone)\n";
        result += String.format(Locale.US,
                "VALUES\n\t(%d, '%s', '%s', '%s', %d, '%s', '%s')",
                clientID, name, login, password, 1, email, phone);
        result += "\nSET IDENTITY_INSERT Clients OFF\n";

        if (!attendeeList.isEmpty()) {
            result += "SET IDENTITY_INSERT Attendees ON\n";
            result += "INSERT INTO Attendees (AttendeeID, Name)\nVALUES\n";
            result += attendeeList.stream()
                    .map(attendee -> String.format(
                            "\t(%d, '%s')",
                            attendee.attendeeID, attendee.name))
                    .collect(Collectors.joining(",\n"));
            result += "\nSET IDENTITY_INSERT Attendees OFF\n";
        }
        return result;
    }
}
