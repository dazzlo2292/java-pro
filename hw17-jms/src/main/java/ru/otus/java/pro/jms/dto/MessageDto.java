package ru.otus.java.pro.jms.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@ToString
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8102699536467887386L;

    private UUID uuid;
    private String text;
}
