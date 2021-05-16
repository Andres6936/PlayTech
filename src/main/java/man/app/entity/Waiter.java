package man.app.entity;

public class Waiter {
    public int identification;
    public int table;
    public String firstName;
    public String lastName;

    public Waiter(int identification, String firstName, String lastName) {
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getJSON() {
        return String.format("{\"identification\":%d, \"firstName\":\"%s\", \"lastName\":\"%s\"},",
                identification, firstName, lastName);
    }
}
