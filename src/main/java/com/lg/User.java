package com.lg;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
// aby zoptymalizowac wyszukiwanie w bazie danych, nalezy utworzyc indeks na polu login.
@Table(name="users", indexes = @Index(columnList = "login"))
public class User {
    /**
     * utworzenie klucza głównego dla tabeli w bazie danych oraz generowanie identyfikatorów
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String login;

    private String firstName, lastName;

    @Enumerated(EnumType.STRING)
    private SEX Sex;

//   4.4
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final List<Role> userRoles = new ArrayList<>();

    public User(String login, String password, String firstName, String lastName, SEX Sex) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Sex = Sex;
    }

    public User() {}

    public void addRole(Role role){
        this.userRoles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Sex=" + Sex +
                '}';
    }
    public SEX getSex() {
        return Sex;
    }

    public void setSex(SEX sex) { Sex = sex; }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
