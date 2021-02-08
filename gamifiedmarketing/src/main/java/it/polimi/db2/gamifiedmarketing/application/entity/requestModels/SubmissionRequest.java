package it.polimi.db2.gamifiedmarketing.application.entity.requestModels;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmissionRequest {

    private Sex sex;

    private Integer age;

    private ExpertiseLevel expertiseLevel;

    /*  Question Id     ---> Integer
     *  Response body   ---> String
     */
    private List<ResponseRequest> responses;

}