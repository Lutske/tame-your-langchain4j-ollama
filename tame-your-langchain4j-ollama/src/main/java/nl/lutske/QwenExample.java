package nl.lutske;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class QwenExample {

    public static void main(String[] args) {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .temperature(0.7) // a bit more creativity
                .logRequests(true)
                .logResponses(true)
                .build();

        String question = "Summarize the concept of Java Streams in 2 sentences.";
        String answer = model.chat(question);
        System.out.println("🤖 " + answer);
    }
}
