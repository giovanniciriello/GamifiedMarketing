package it.polimi.db2.gamifiedmarketing.application.entity.views;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequest{
    public String new_question_title;
    public String new_question_subtitle;
}