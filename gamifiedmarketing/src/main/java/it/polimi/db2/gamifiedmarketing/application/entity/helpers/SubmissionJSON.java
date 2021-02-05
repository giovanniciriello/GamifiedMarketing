package it.polimi.db2.gamifiedmarketing.application.entity.helpers;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmissionJSON {

    private Sex sex;

    private String age;

    private ExpertiseLevel expertiseLevel;

    /*  Question Id     ---> Integer
     *  Response body   ---> String
     */
    private List<ResponseJSON> responses;

}