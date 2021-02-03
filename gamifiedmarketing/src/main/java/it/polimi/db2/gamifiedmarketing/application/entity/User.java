package it.polimi.db2.gamifiedmarketing.application.entity;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @Column(unique = true, length = 128)
    @Email
    /* https://stackoverflow.com/questions/4459474/hibernate-validator-email-accepts-askstackoverflow-as-valid
       We know about intranet addresses, but we leave it in that way because we considered it not relevant.
       The alternative would have been to create a new Custom Annotation for Email validation and then a custom
       Email Validator.
     */
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime bannedAt;

    /*
     * CascadeType Policies:
     *  --> PERSIST     If Admin is persisted, then also the products he created
     *  --> REMOVE      If Admin is removed, then also the products he created
     *  --> MERGE       If Admin is merged (saved but yet exists), then also the products he created
     *  --> REFRESH     Not needed
     *  --> DETACH      Not needed
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "admin", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Product> productsCreated;

    /*
     * CascadeType Policies:
     *  --> PERSIST     If User is persisted, then also the related submissions
     *  --> REMOVE      If User is removed, then also the related submissions
     *  --> MERGE       If User is merged, then also the related submissions
     *  --> REFRESH     Not needed
     *  --> DETACH      Not needed
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Submission> submissions;

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

    public String getFullName(){
        return this.getLastName()+" "+this.getFirstName();
    }
}
