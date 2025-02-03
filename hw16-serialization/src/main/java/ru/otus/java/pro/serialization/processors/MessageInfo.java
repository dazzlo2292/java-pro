package ru.otus.java.pro.serialization.processors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfo {
    @JsonProperty("chat_id")
    private String chat_identifier;
    @JsonProperty("last")
    private String last;
    @JsonProperty("send_date")
    private String sendDate;
    @JsonProperty("text")
    private String text;
}
