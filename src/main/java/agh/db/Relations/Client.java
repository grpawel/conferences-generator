package agh.db.Relations;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public String toSQL() {
        String result = "";
        result += "PRINT 'Inserting client #" + clientID + "'\n";
        result += "SET IDENTITY_INSERT Clients ON\n";
        result += String.format(Locale.US, "INSERT INTO Clients (" +
                        "ClientID, Name, Login, Password, IsCompany, Email, Phone" +
                        ")\nVALUES (%d, '%s', '%s', '%s', %d, '%s', '%s')\n",
                clientID, name, login, password, 0, email, phone);
        result += "SET IDENTITY_INSERT Clients OFF\n";
        if(!attendeeList.isEmpty()) {
            result += "SET IDENTITY_INSERT Attendees ON\n";
            result += "INSERT INTO Attendees (AttendeeID, Name)\nVALUES\n";
            result += attendeeList.stream()
                    .map(attendee -> String.format("\t(%d, '%s')",
                            attendee.attendeeID, attendee.name))
                    .collect(Collectors.joining(",\n"));
            result += "\nSET IDENTITY_INSERT Attendees OFF\n";
        }
        return result;
    }
}
