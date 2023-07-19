package javapuffs.journeo;

import java.util.ArrayList;
import java.util.List;

public class ChatRequestDTO {

    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequestDTO(String model, String country) {
        this.model = model;
        String messageText = "I want you to act as a navigation system that gives me coordinates of the " +
                "capital of " + country + " in  format {'latitude': 30.999, 'longitude': 30.999} put latitude and longitude in double quotes instead of single without any additional text from you.";
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", messageText));
        this.n = 1;
        this.temperature = 0.5;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // getters and setters
}

