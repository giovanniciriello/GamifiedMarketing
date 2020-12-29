package it.polimi.db2.gamifiedmarketing.application.entity.views;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductRequest {
    public String name;
    public String description;
    public String image_url;
    public List<QuestionRequest> questions;
}


