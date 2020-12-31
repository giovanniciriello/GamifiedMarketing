package it.polimi.db2.gamifiedmarketing.application.entity.views;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequest{
    public String title;
    public String subtitle;
}