package it.polimi.db2.gamifiedmarketing.application.entity;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
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
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Enumerated
    private UserRole role;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime bannedAt;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "admin", cascade = {CascadeType.PERSIST})
    private List<Product> productsCreated;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<Submission> submissions;

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
