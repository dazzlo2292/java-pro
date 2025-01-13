package ru.otus.java.pro.serialization.processors;

import lombok.NoArgsConstructor;
import ru.otus.java.pro.serialization.deserialization.JsonSmsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class DataProcessor {
    public Map<String, List<MessageInfo>> process(JsonSmsData jsonData) {
        Map<String, List<MessageInfo>> statistic = new HashMap<>();

        String chatIdentifier;
        String last;
        String sendDate;
        String text;
        for (JsonSmsData.ChatSession c : jsonData.getChatSessions()) {
            chatIdentifier = c.getChatIdentifier();
            last = c.getMembers().get(0).getLast();
            for (JsonSmsData.ChatSession.Message mes : c.getMessages()) {
                sendDate = mes.getSendDate();
                text = mes.getText();
                if (!statistic.containsKey(mes.getBelongNumber())) {
                    statistic.put(mes.getBelongNumber(), new ArrayList<>());
                }
                statistic.get(mes.getBelongNumber()).add(new MessageInfo(chatIdentifier, last, sendDate, text));
            }
        }

        return statistic;
    }
}
