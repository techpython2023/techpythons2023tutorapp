package com.example.techpythons2023.Model;

public class StaffItem {
    private String email, studentnumber, password, role;

    public StaffItem()
    {

    }

    public StaffItem(String email, String studentnumber, String password, String role) {
        this.email = email;
        this.studentnumber = studentnumber;
        this.password = password;
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
