package nl.lutske;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class OllamaExample {

    static void main() {
        // Connect to your locally running Ollama model
        ChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // default Ollama endpoint
                .modelName("mistral")               // or "llama2", "tinydolphin", etc.
                .build();

        // Ask it something!
        String question = "Explain dependency injection in simple Java terms in only 2 sentences";
        IO.println("User: " + question);

        String response = model.chat(question);
        IO.println("🤖: " + response);
    }
}
