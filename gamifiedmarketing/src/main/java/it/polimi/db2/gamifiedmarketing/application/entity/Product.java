package it.polimi.db2.gamifiedmarketing.application.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
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
     * Relationship needed in order to show reviews of the product of the day in home page.
     *
     * Fetch Policy
     *  --> FetchType.LAZY        default
     *
     * CascadeType Policies:
     *  --> PERSIST     If Product is persisted, then also the related reviews
     *  --> REMOVE      If Product is removed, then also the related reviews
     *  --> MERGE       If Product is merged (saved but yet exists), then also the related reviews
     *  --> REFRESH     Not needed
     *  --> DETACH      Not needed
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<Review> reviews;

    /*
     * Relationship needed in order to create a new submission. Used by admin when creating the questionnaire.
     *
     * Fetch Policy
     *  --> FetchType.LAZY        default
     *
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
     * Relationship needed when showing all the submissions for a related product by admin.
     *
     * Fetch Policies:
     *  ---> FetchType.EAGER        1. When getting to /leaderboard we need all submissions of the product of the day
     *                              2. When Admin goes to the inspection page and choose a product, we must show the responses of each user
     * CascadeType Policies:
     *  --> REMOVE      If Product is removed, then also the related submissions
     *  --> PERSIST     Not needed because when creating Product we do not have submissions yet to be persisted.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @OrderBy("submission_status DESC")
    private List<Submission> submissions;

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review){
        getReviews().add(review);
        review.setProduct(this);
    }

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

    // Friendly format for Thymeleaf template
    public String getFormattedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.date.format(formatter);
    }
}
