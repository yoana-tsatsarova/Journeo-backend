package javapuffs.journeo;

import java.util.ArrayList;
import java.util.List;

public class ChatResponseDTO {

    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

    public ChatResponseDTO() {
        this.choices = new ArrayList<>();
    }

    public static class Choice {

        private int index;
        private Message message;


        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public Choice(int index, Message message) {
            this.index = index;
            this.message = message;
        }
    }
}