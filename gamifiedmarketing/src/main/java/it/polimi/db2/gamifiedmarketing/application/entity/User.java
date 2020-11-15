package it.polimi.db2.gamifiedmarketing.application.entity;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(unique = true, length = 128)
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated
    private UserRole role;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    private Boolean banned;

    @OneToMany(mappedBy = "admin", cascade = {CascadeType.PERSIST})
    private List<Product> productsCreated;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<Submission> submissions;

    public User() {
    }

    public User(String first, String last, String email, String password) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.password = password;
        this.banned = false;
        this.role = UserRole.CUSTOMER;
    }

    public User(String first, String last, String email, String password, UserRole role) {
        this.firstName = first;
        this.lastName = last;
        this.email = email;
        this.banned = false;
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

    // Methods for the Bi-directional relationship ( User (admin) 1:N Product )
    public List<Product> getProducts() {
        return productsCreated;
    }

    public void addProduct(Product product){
        getProducts().add(product);

        // Here we must align both sides of the relationship
        // If @product is new, then invoking persist() on @user cascades also to @product
        product.setAdmin(this);
    }

    public void removeProduct(Product product){
        getProducts().remove(product);
    }

    // Methods for the Bi-directional relationship ( User 1:N Submission )
    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void addSubmission(Submission submission){
        getSubmissions().add(submission);

        // Here we must align both sides of the relationship
        // If @submission is new, then invoking persist() on @user cascades also to @submission
        submission.setUser(this);
    }

    public void removeSubmission(Submission submission){
        getSubmissions().remove(submission);
    }
}
