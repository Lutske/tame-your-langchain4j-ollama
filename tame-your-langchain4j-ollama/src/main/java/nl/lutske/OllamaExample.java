package nl.lutske;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class OllamaExample {

    public static void main(String[] args) {
        // Connect to your locally running Ollama model
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // default Ollama endpoint
                .modelName("mistral")               // or "llama2", "tinydolphin", etc.
                .build();

        // Ask it something!
        String response = model.chat("Explain dependency injection in simple Java terms in only 2 sentences");
        System.out.println("🤖 " + response);
    }
}
