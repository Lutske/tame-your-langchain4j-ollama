package nl.lutske;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class QwenExample {

    static void main() {
        ChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .temperature(0.7) // a bit more creativity
                .logRequests(true)
                .logResponses(true)
                .seed(1)
                .build();

        String question = "Tell me why you should use local LLM's in Java";
        IO.println("User: " + question);

        String answer = model.chat(question);
        IO.println("🤖 " + answer);
    }
}
