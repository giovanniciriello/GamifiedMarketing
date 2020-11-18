package it.polimi.db2.gamifiedmarketing.application.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Temporal(TemporalType.DATE)
    @Column(unique=true)
    @NotNull
    private Date date;

    @Column(length=1024)
    private String imageUrl;

    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Any product is instantiated by only one administrator
    @ManyToOne
    @JoinColumn(name="admin_id")
    private User admin;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST})
    private List<Question> questions;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST})
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
