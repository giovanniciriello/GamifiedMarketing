package it.polimi.db2.gamifiedmarketing.application.entity.requestModels;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    public String name;
    public String description;
    public String image_url;
    public LocalDate date;
    public List<QuestionRequest> questions;
}


