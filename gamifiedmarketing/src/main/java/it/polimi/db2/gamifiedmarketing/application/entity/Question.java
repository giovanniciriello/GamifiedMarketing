package it.polimi.db2.gamifiedmarketing.application.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String subtitle;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /*
     * Relationship needed when retrieving questions/responses on the inspection page. Used when creating a new submission.
     *
     * Fetch Policy
     *  --> FetchType.LAZY        default
     *
     * CascadeType Policies:
     *  --> PERSIST     Not needed, responses come after the question creation!
     *  --> REMOVE      If Question is removed, then also the related responses
     *  --> MERGE       Not needed
     *  --> REFRESH     Not needed
     *  --> DETACH      Not needed
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Response> responses;

    // Methods for the Bi-directional relationship ( Question 1:N Response )
    public List<Response> getResponses() {
        return responses;
    }

    public void addResponse(Response response){
        getResponses().add(response);

        // Here we must align both sides of the relationship
        // If @submission is new, then invoking persist() on @user cascades also to @submission
        response.setQuestion(this);
    }

    public void removeResponse(Response response){
        getResponses().remove(response);
    }
}
