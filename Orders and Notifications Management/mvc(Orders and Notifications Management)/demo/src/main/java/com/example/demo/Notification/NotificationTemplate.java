package com.example.demo.Notification;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationTemplate {
    @JsonProperty("content")
    private String content;

    @JsonProperty("placeholders")
    private List<String> placeholders;

    @JsonProperty("channels")
    private NotificationType channels;

    public NotificationTemplate(List<String> placeholders, NotificationType channels) {
        this.placeholders = placeholders;
        this.channels = channels;
        generateContent();
    }

    private void generateContent() {
        content = "Dear ";

        content += placeholders.get(0) + ", ";

        content += "your booking of the ";

        for (String productPlaceholder : placeholders.subList(1, placeholders.size() - 1)) {
            content += (productPlaceholder + ", ");
        }

        // Remove trailing comma and add the last product placeholder
        content = content.replaceAll(", $", ", ") + placeholders.get(placeholders.size() - 1);

        content += " is confirmed by "+ channels + " thanks for using our store :)";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(List<String> placeholders) {
        this.placeholders = placeholders;
    }

    public NotificationType getChannels() {
        return channels;
    }

    public void setChannels(NotificationType channels) {
        this.channels = channels;
    }
}