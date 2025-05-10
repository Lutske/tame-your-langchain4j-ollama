package nl.lutske;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class FinancialDataExample {

    public static void main(String[] args) {
        // Simulated private financial data
        String financialReport = """
            Company: ACME Corp
            Q1 Revenue: $1,200,000
            Q1 Expenses: $800,000
            Q1 Net Profit: $400,000
            Main Cost Drivers: Advertising, Salaries
            Notes: Revenue increased by 20% compared to Q4 due to new product launch.
            """;

        // Connect to your local Ollama LLM (e.g., qwen2:7b)
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .temperature(0.2)
                .build();

        // Ask a question with the private data embedded in the prompt
        String question = "Based on this financial report:\n\n" + financialReport + "\n\nWhat were the main reasons for the profit this quarter?";
        String response = model.chat(question);
        System.out.println("🤖 " + response);
    }
}
