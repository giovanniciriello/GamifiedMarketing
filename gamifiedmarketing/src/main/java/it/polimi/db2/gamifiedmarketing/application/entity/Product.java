package it.polimi.db2.gamifiedmarketing.application.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    @Column(unique=true)
    @NotNull
    private LocalDate date;

    @Column(length=1024)
    private String imageUrl;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Any product is instantiated by only one administrator
    @ManyToOne
    @JoinColumn(name="admin_id")
    private User admin;

    /*
     * CascadeType Policies:
     *  --> PERSIST     If Product is persisted, then also the related questions
     *  --> REMOVE      If Product is removed, then also the related questions
     *  --> MERGE       If Product is merged (saved but yet exists), then also the related questions
     *  --> REFRESH     Not needed
     *  --> DETACH      Not needed
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Question> questions;

    /*
     * Fetch Policies:
     *  ---> FetchType.EAGER        1. When getting to /home we need submissions to show reviews of all users
     *                              2. When getting to /leaderboard we need all submissions of the product of the day
     *                              3. When Admin goes to the inspection page and choose a product, we must show the responses of each user
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Submission> submissions;

    // Methods for the Bi-directional relationship ( Product 1:N Question )
    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question){
        getQuestions().add(question);

        // Here we must align both sides of the relationship
        // If @question is new, then invoking persist() on @product cascades also to @question
        question.setProduct(this);
    }

    public void removeQuestion(Question question){
        getQuestions().remove(question);
    }

    // Methods for the Bi-directional relationship ( Product 1:N Submission )
    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void addSubmission(Submission submission){
        getSubmissions().add(submission);

        // Here we must align both sides of the relationship
        // If @submission is new, then invoking persist() on @product cascades also to @submission
        submission.setProduct(this);
    }

    public void removeSubmission(Submission submission){
        getSubmissions().remove(submission);
    }
}
