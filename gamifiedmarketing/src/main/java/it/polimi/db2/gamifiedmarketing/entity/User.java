package it.polimi.db2.gamifiedmarketing.entity;

import com.sun.istack.NotNull;
import it.polimi.db2.gamifiedmarketing.entity.enums.UserRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated
    private UserRole role;

    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    private Boolean banned;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Submission> submissions;

    public User() {
    }

    public User(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
        this.role = UserRole.CUSTOMER;
    }

    public User(String first, String last, String email, String password, UserRole role) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
