package actors;

import utility.UI;

import java.lang.module.Configuration;

public class SwimmingResult {
    // Attributes ---------------------------------------
    private final int distance;
    private final String date;
    private final String swimTime;
    private final boolean isCompetitive;
    private int rank;


    // Constructor --------------------------------------
    public SwimmingResult(UI ui) {
        ui.print("Please enter distance: ");
        this.distance = ui.readInt();

        this.date = ui.setDate();

        ui.print("Please enter the swim time - ");
        this.swimTime = ui.setTime();

        ui.print("Please enter was this in a competition - true/false: ");
        this.isCompetitive = ui.readBoolean();

        if (isCompetitive) {
            ui.print("Please enter rank placement: " );
            this.rank = ui.readInt();
        }
    }

    // Getters ------------------------------------------
    public int getDistance() {
        return distance;
    }

    public String getDate() {
        return date;
    }

    public String getSwimTime() {
        return swimTime;
    }

    public boolean isCompetitive() {
        return isCompetitive;
    }

    public int getRank() {
        return rank;
    }


    @Override
    public String toString() {
        return "SwimmingResult{" +
                "distance=" + distance +
                ", date='" + date + '\'' +
                ", swimTime='" + swimTime + '\'' +
                ", isCompetitive=" + isCompetitive +
                ", rank=" + rank +
                '}';
    }
}
