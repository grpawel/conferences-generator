package agh.db;

public class ApiData {
    public final String first_name;
    public final String last_name;
    public final String email;
    public final String company_name;
    public final String login1;
    public final String phone;
    public final String student_card;
    public final String conf_name1;
    public final String conf_name2;
    public final String conf_name3;

    public ApiData(String first_name, String last_name, String email, String company_name, String login1, String phone,
                   String student_card, String conf_name1, String conf_name2, String conf_name3) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.company_name = company_name;
        this.login1 = login1;
        this.phone = phone;
        this.student_card = student_card;
        this.conf_name1 = conf_name1;
        this.conf_name2 = conf_name2;
        this.conf_name3 = conf_name3;
    }
}
