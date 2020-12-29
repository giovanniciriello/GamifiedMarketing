package it.polimi.db2.gamifiedmarketing.application.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import it.polimi.db2.gamifiedmarketing.application.entity.helpers.SubmissionCustomSerializer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "submissions")
//@JsonSerialize(using = SubmissionCustomSerializer.class)
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer points;

    private Integer age;

    private Sex sex;

    private ExpertiseLevel expertiseLevel;

    private SubStatus submissionStatus;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*
     * CascadeType Policies:
     *  --> PERSIST     If Submission is persisted, then also the related responses
     *  --> REMOVE      If Submission is removed, then also the related responses
     *  --> MERGE       Similar reason to persist
     *  --> REFRESH     Not needed
     *  --> DETACH      Not needed
     *
     *
     * Fetch Policies:
     *  ---> FetchType.EAGER        1. When getting to /home we need submissions and relative responses too
     *                              3. When Admin goes to the inspection page and choose a product, we must show the responses of each user
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "submission", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Response> responses;

    // Methods for the Bi-directional relationship ( Submission 1:N Response )

    public List<Response> getResponses() {
        return responses;
    }

    public void addResponse(Response response){
        getResponses().add(response);

        // Here we must align both sides of the relationship
        // If @submission is new, then invoking persist() on @user cascades also to @submission
        response.setSubmission(this);
    }

    public void removeResponses(Response response){
        getResponses().remove(responses);
    }
}
