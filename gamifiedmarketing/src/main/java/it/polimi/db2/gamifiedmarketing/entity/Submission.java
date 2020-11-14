package it.polimi.db2.gamifiedmarketing.entity;

import com.sun.istack.NotNull;
import it.polimi.db2.gamifiedmarketing.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.entity.enums.Sex;
import it.polimi.db2.gamifiedmarketing.entity.enums.SubStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToMany(mappedBy = "submission")
    private List<Response> responses;

    public Submission() {
    }

    public Submission(Integer age, Sex sex, ExpertiseLevel expertiseLevel, SubStatus submissionStatus) {
        this.age = age;
        this.sex = sex;
        this.expertiseLevel = expertiseLevel;
        this.submissionStatus = submissionStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ExpertiseLevel getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(ExpertiseLevel expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }

    public SubStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
}
