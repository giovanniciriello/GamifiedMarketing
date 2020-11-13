package it.polimi.db2.gamifiedmarketing.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

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
