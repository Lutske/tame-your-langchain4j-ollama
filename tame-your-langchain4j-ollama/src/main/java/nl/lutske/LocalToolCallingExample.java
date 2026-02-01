package nl.lutske;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.time.LocalDate;
import java.util.Locale;

public class LocalToolCallingExample {

    interface FinanceAssistant {

        @SystemMessage("""
                You are a helpful finance assistant.
                When an expense needs categorization, call the categorizeExpense tool.
                Always return:
                - category
                - confidence (0–1)
                - short explanation
                """)
        String handle(@UserMessage String message);
    }

    static class FinanceTools {

        @Tool("Categorize a single expense into a finance category.")
        public String categorizeExpense(
                @P("Expense description, e.g. 'Train ticket NS'") String description,
                @P("Amount in EUR, e.g. 18.40") double amount,
                @P("Date in ISO format, e.g. 2026-02-01") String date
        ) {

            String d = description.toLowerCase(Locale.ROOT);

            String category;
            double confidence;

            if (d.contains("ns") || d.contains("train") || d.contains("uber")) {
                category = "Transport";
                confidence = 0.9;
            } else if (d.contains("ah") || d.contains("aldi") || d.contains("jumbo")) {
                category = "Groceries";
                confidence = 0.9;
            } else if (d.contains("aws") || d.contains("azure") || d.contains("gcp")) {
                category = "Cloud / Software";
                confidence = 0.85;
            } else if (d.contains("lunch") || d.contains("cafe") || d.contains("restaurant")) {
                category = "Food & Drinks";
                confidence = 0.8;
            } else {
                category = "Other";
                confidence = 0.6;
            }

            LocalDate parsedDate = LocalDate.parse(date);

            return """
                    {
                      "date": "%s",
                      "description": "%s",
                      "amount": %.2f,
                      "category": "%s",
                      "confidence": %.2f
                    }
                    """.formatted(parsedDate, escape(description), amount, category, confidence);
        }

        private String escape(String value) {
            return value.replace("\\", "\\\\").replace("\"", "\\\"");
        }
    }

    static void main() {

        ChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen2.5:7b")
                .temperature(0.2) // important for reliable tool calling
                .build();

        FinanceAssistant assistant = AiServices.builder(FinanceAssistant.class)
                .chatModel(model)
                .tools(new FinanceTools())
                .build();

        String input = """
                Categorize these expenses:
                - 2026-02-01 | Train ticket NS | 18.40
                - 2026-02-01 | AWS invoice | 42.12
                - 2026-02-01 | Lunch at cafe | 11.80
                """;

        IO.println(input);
        String result = assistant.handle(input);
        IO.println(result);
    }
}
