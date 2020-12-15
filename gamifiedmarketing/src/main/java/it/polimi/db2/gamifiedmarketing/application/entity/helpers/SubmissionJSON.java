package it.polimi.db2.gamifiedmarketing.application.entity.helpers;

import it.polimi.db2.gamifiedmarketing.application.entity.enums.ExpertiseLevel;
import it.polimi.db2.gamifiedmarketing.application.entity.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SubmissionJSON {

    private Sex sex;

    private Integer age;

    private ExpertiseLevel expertiseLevel;

    /*  Question Id     ---> Integer
     *  Response body   ---> String
     */
    private List<Response> responses;

}

class Response{
    private Number question_id;
    private String body;
}