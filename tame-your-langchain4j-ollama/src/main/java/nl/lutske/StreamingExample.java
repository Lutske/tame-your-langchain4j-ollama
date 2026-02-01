package nl.lutske;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.output.TokenUsage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class StreamingExample {

    static void main() throws InterruptedException {
        String modelName = "qwen2.5:7b";

        StreamingChatModel model = OllamaStreamingChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName(modelName)
                .temperature(0.7)
                .logRequests(true)
                .logResponses(true)
                .seed(1)
                .build();

        String prompt = "Write a short funny poem about JFokus, use max 40 lines";

        IO.println("Nr of chars: " + prompt.length());

        ChatRequest request = ChatRequest.builder()
                .messages(UserMessage.from(prompt))
                .build();

        CountDownLatch done = new CountDownLatch(1);

        model.chat(request, new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                System.out.print(partialResponse); // stream naar console
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                IO.println("\n\n--- done ---");

                TokenUsage usage = completeResponse.tokenUsage();
                if (usage != null) {
                    IO.println("Prompt tokens: " + usage.inputTokenCount());
                    IO.println("Completion tokens: " + usage.outputTokenCount());
                    IO.println("Total tokens: " + usage.totalTokenCount());
                } else {
                    IO.println("Token usage not available (depends on model/provider response).");
                }

                done.countDown();
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("\nError: " + error.getMessage());
                done.countDown();
            }
        });

        // voorkom dat main eindigt voordat streaming klaar is
        if (!done.await(60, TimeUnit.SECONDS)) {
            System.err.println("Timed out waiting for streaming response.");
        }
    }
//    
//        ChatResponse response = model.chat(request);
//
//
//        IO.println("\n" + response.aiMessage().text() + "\n");
//
//        TokenUsage usage = response.tokenUsage();
//        if (usage != null) {
//            IO.println("Prompt tokens: " + usage.inputTokenCount());
//            IO.println("Completion tokens: " + usage.outputTokenCount());
//            IO.println("Total tokens: " + usage.totalTokenCount());
//        } else {
//            IO.println("Token usage not available for this response/model.");
//        }
//    }
}