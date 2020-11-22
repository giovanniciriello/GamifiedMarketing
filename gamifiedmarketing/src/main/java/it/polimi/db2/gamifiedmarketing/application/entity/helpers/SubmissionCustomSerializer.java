package it.polimi.db2.gamifiedmarketing.application.entity.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.polimi.db2.gamifiedmarketing.application.entity.Submission;

import java.io.IOException;

public class SubmissionCustomSerializer extends StdSerializer<Submission> {

    public SubmissionCustomSerializer() {
        this(null);
    }

    public SubmissionCustomSerializer(Class<Submission> t) {
        super(t);
    }

    @Override
    public void serialize(Submission sub, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
            jgen.writeNumberField("submission_id", sub.getId());
            jgen.writeNumberField("user_id", sub.getUser().getId());
            jgen.writeStringField("is_submitted", sub.getSubmissionStatus().toString());
            jgen.writeStringField("first_name", sub.getUser().getFirstName());
            jgen.writeStringField("last_name", sub.getUser().getLastName());
            jgen.writeFieldName("responses");
                jgen.writeStartArray();
                    for (Integer i = 0; i < sub.getResponses().size(); i++){
                        jgen.writeStartObject();
                        jgen.writeNumberField("question_id", sub.getResponses().get(i).getQuestion().getId());
                        jgen.writeStringField("body", sub.getResponses().get(i).getBody());
                        jgen.writeEndObject();}
                 jgen.writeEndArray();
        jgen.writeEndObject();
    }
}
