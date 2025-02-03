package ru.otus.java.pro.serialization.deserialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonSmsData {
    @JsonProperty("chat_sessions")
    private List<ChatSession> chatSessions;

    @Data
    public static class ChatSession {
        @JsonProperty("chat_id")
        private Integer chatId;
        @JsonProperty("chat_identifier")
        private String chatIdentifier;
        @JsonProperty("display_name")
        private String displayName;
        @JsonProperty("is_deleted")
        private Integer isDeleted;
        @JsonProperty("members")
        private List<Member> members;
        @JsonProperty("messages")
        private List<Message> messages;

        @Data
        public static class Member {
            @JsonProperty("first")
            private String first;
            @JsonProperty("handle_id")
            private Integer handleId;
            @JsonProperty("image_path")
            private String imagePath;
            @JsonProperty("last")
            private String last;
            @JsonProperty("middle")
            private String middle;
            @JsonProperty("phone_number")
            private String phoneNumber;
            @JsonProperty("service")
            private String service;
            @JsonProperty("thumb_path")
            private String thumbPath;
        }

        @Data
        public static class Message {
            @JsonProperty("ROWID")
            private Integer rowId;
            @JsonProperty("attributedBody")
            private String attributedBody;
            @JsonProperty("belong_number")
            private String belongNumber;
            @JsonProperty("date")
            private Long date;
            @JsonProperty("date_read")
            private Long dateRead;
            @JsonProperty("guid")
            private String guid;
            @JsonProperty("handle_id")
            private Integer handleId;
            @JsonProperty("has_dd_results")
            private Integer hasDdResults;
            @JsonProperty("is_deleted")
            private Integer isDeleted;
            @JsonProperty("is_from_me")
            private Integer isFromMe;
            @JsonProperty("send_date")
            private String sendDate;
            @JsonProperty("send_status")
            private Integer sendStatus;
            @JsonProperty("service")
            private String service;
            @JsonProperty("text")
            private String text;
        }
    }
}
