package com.example.whetherapp;
import java.util.ArrayList;
import java.util.List;
public class RecommendationHandler {

    private final List<Recommendation> recommendations = new ArrayList<>();

    public RecommendationHandler() {
        recommendations.add(new Recommendation("Do not water your lawn and plants today. The rain does it for you and you save water.",
                0.5));
        recommendations.add(new Recommendation("Water your lawn and plants so they will grow and thrive.", 0.0));
    }

    /*private String recommendation_today;

    RecommendationHandler()
    {
        this.recommendation_today = recommendation_today;
    }

    public String getRecommendation_today() {
        return recommendation_today;
    }


    /**takes wheter data and sends a recommendation to the user
     *
     * @param wheterinput
     */
    /*public void wheter(String wheterinput, String wheterinputToMorov)
    {
        String temp = wheterinput;
        String temp2 = wheterinputToMorov;

        //rägn i dag
        if(temp.equals("Rain"))
        {
           this.recommendation_today = "Du bör inte vatna blommorna i dag";
        }
        //rägn i mon
        else if(temp2.equals("Rain"))
        {
            this.recommendation_today = "Du bör inte vatna blomorna i dag";
        }
        //sol i dag och sol i morgon
        else if((temp.equals("Sol")) && (temp2.equals("Sol")))
        {
            this.recommendation_today = "Det är en bra dag att vatna blomorna i dag";
        }
        else
        {
            this.recommendation_today = "gör vad du vill";
        }
    }*/
}
