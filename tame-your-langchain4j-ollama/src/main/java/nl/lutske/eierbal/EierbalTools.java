package nl.lutske.eierbal;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class EierbalTools {

    private static final int CALORIES_PER_EIERBAL = 250;
    private static final int WALKING_CALORIES_PER_HOUR = 250;

    @Tool("Calculate how many Groningen eierballen are needed for a group")
    public int calculateEierballen(
            @P("Number of people") int people,
            @P("Eierballen per person") int perPerson) {

        IO.println("🥚 TOOL CALLED: calculateEierballen");
        return people * perPerson;
    }

    @Tool("Calculate the total cost of eierballen in euros")
    public double calculateBudget(
            @P("Number of eierballen") int amount,
            @P("Price per eierbal in euros") double price) {

        IO.println("💰 TOOL CALLED: calculateBudget");
        return Math.round(amount * price * 100) / 100.0;
    }

    @Tool("Estimate how many minutes of walking are needed to burn the calories from eierballen")
    public int calculateBurnTime(
            @P("Number of eierballen eaten") int amount) {

        IO.println("🏃 TOOL CALLED: calculateBurnTime");

        int calories = amount * CALORIES_PER_EIERBAL;
        return (int) Math.ceil(
                calories * 60.0 / WALKING_CALORIES_PER_HOUR
        );
    }

    @Tool("Calculate ingredients for a simplified Groningen eierbal recipe")
    public String calculateRecipe(
            @P("Number of eierballen to prepare") int amount) {

        IO.println("👩‍🍳 TOOL CALLED: calculateRecipe");

        return """
                Ingredients for %d eierballen:
                Eggs: %d
                Flour: %d grams
                Butter: %d grams
                Milk: %d ml
                Breadcrumbs: %d grams
                Curry powder: %d grams
                """.formatted(
                amount,
                amount,
                amount * 20,
                amount * 15,
                amount * 50,
                amount * 30,
                amount * 2
        );
    }
}
