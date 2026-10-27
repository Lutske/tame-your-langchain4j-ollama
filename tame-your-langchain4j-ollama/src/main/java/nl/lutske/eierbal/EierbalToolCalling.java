package nl.lutske.eierbal;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

import java.time.Duration;

public class EierbalToolCalling {

    interface EierbalAssistant {

        @SystemMessage("""
                You are a Groningen eierbal expert.
                Use the available tools for calculations.
                Reply in one short sentence.
                Do not explain your calculations.
                """)
        String chat(String message);
    }

    public static void main(String[] args) {

        // Connect to local Ollama
        var model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen3:4b")
                .think(false)
                .temperature(0.0)
                .timeout(Duration.ofSeconds(120))
                .build();

        // Register Java tools
        var assistant = AiServices.builder(EierbalAssistant.class)
                .chatModel(model)
                .tools(new EierbalTools())
                .build();

        String prompt = """
                I'm organizing a Java meetup for 40 developers.
                Everyone wants 2 eierballen.
                Each eierbal costs 3.50 euros.
                
                How many eierballen do I need,
                and what is the total budget?
                """;

        // Let the AI select and execute tools
        IO.println("🤖 Asking LangChain4j...");

        String response = assistant.chat(prompt);

        IO.println("\n🤖 LLM RESPONSE:");
        IO.println(response);
    }
}
