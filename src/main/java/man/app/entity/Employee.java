package man.app.entity;

public class Employee {

    // Properties

    private int dui;
    private int nit;
    private float salary;
    private String name;
    private String department;
    private MaritalStatus maritalStatus = MaritalStatus.Free;

    // Constructor

    public Employee(int _dui, String _name, float _salary) {
        this.dui = _dui;
        this.name = _name;
        this.salary = _salary;
    }

    // Methods

    public String getJSON() {
        return String.format("{\"dui\":%d, \"nit\":%d, \"salary\":%f, \"name\":\"%s\", \"department\":\"%s\", \"marital-status\":\"%s\"},",
                dui, nit, salary, name, department, maritalStatus.name());
    }

    // Getters and Setters

    public int getDui() {
        return dui;
    }

    public void setDui(int dui) {
        this.dui = dui;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
