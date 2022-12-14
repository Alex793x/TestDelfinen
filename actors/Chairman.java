package actors;

import database.Database;
import utility.UI;

public class Chairman extends Employee {


    // Constructor ---------------------------------------
    public Chairman(RoleType role, PrivilegeType privilege) {
        setName("Administrator");
        setRole(role);
        setPrivilege(privilege);
        setUsername("rootAdmin");
    }


    // Behaviors (Methods) ---------------------------------
    public Member createMember(UI ui) {
        ui.print("Tast venligst 1 - for motionist eller 2 - for Konkurrence Svømmer: ");
        return (ui.readInt()) == 1 ? new LeisureSwimmer(ui) : new CompetitiveSwimmer(ui);
    }


    public void addMember(UI ui, Member newMember, Database database) {
        database.getMemberList().add(newMember);

        if (newMember instanceof CompetitiveSwimmer) {
            ui.print("Please enter how many swimming disciplines " + newMember.getName() + " is practising: ");
            int disciplineAmount = ui.readInt();
            for (int i = 0; i < disciplineAmount; i++) {
                ((CompetitiveSwimmer) newMember).getSwimmingDisciplineList().add(new SwimmingDiscipline(ui));
            }
            database.getSwimmersCoachAssociationList().
                    put(newMember, chooseCoach(ui, database));

            ui.printLn(newMember.getName() + " er blevet tilføjet som medlem med " + disciplineAmount +
                    " aktive svømme discipliner");

        }
    }

    // This method iterates through the Coach list after
    public Coach chooseCoach(UI ui, Database swimmerCoachDatabase) {
        for (Coach coach : swimmerCoachDatabase.getCoachList()) {
            ui.printLn("Træner: " + coach.getName());
        }

        ui.print("Hvilken Træner skal medlemmet have: ");
        while (true) {
            String coachName = ui.readLine();
            for (Coach coach : swimmerCoachDatabase.getCoachList()) {
                if (coach.getName().equalsIgnoreCase(coachName)) {
                    return coach;
                }
            }
            ui.printLn("Træner eksisterer ikke, prøv venligst igen");
        }
    }

    public void printMembers(Database swimmerCoachDatabase) {
        for (Member member : swimmerCoachDatabase.getMemberList()) {
            System.out.println(member.getName());
        }
    }

}