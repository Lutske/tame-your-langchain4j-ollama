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

        // specialize

        String question = "Tell me why you should use local LLM's in Java";
        String answer = model.chat(question);
        System.out.println("🤖 " + answer);
    }
}
