package it.polimi.db2.gamifiedmarketing.application.entity;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.SubStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "submissions")
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
    @NotNull
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "submission")
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
