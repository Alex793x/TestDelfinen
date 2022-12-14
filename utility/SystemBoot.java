package utility;

import actors.*;
import database.Database;

import java.util.ArrayList;

public class SystemBoot {

    // Utility / Controller ------------------
    UI ui = new UI();
    public FileHandler fileHandler = new FileHandler();
    private Employee currentUser;
    ArrayList<Employee> enigmaUsers = new ArrayList<>();
    Database swimmerCoachDatabase = new Database();

    // Setter -----------------------------------
    private void setRoleAndPrivilege(String username) {
        // Switch statement set role and privilege based on correct username
        for (Employee user : enigmaUsers) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
            }
        }
    }

    private void loadAndSetUsers() {
        // Staff -----------------
        enigmaUsers.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        enigmaUsers.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT));
        enigmaUsers.add(new Coach("Thomas", "+45 01 23 58 13", "thomas123"));
        enigmaUsers.add(new Coach("Marry", "+45 01 23 58 13", "Marry123"));
        enigmaUsers.add(new Coach("Jen", "+45 01 23 58 13", "Jen123"));

        // Add coaches to coach list -------------------------------------------
        for (Employee user : enigmaUsers) {
            if (user instanceof Coach) {
                swimmerCoachDatabase.getCoachList().add((Coach) user);
            }
        }
    }

    private void loginSystem() {
        String user = null;
        do {
            user = isLoggedIn();
            if (!user.equals("0")) {
                setRoleAndPrivilege(user);

                System.out.println(currentUser.getUsername());
                System.out.println(currentUser.getRole());
                System.out.println(currentUser.getPrivilege());
            }
        } while (user.equals("0"));
    }


    private void startSystem() {
        swimmerCoachDatabase.setMemberList(fileHandler.loadMemberList(swimmerCoachDatabase.getMemberList()));
        loadAndSetUsers();
        for (int i = 0; i < swimmerCoachDatabase.getMemberList().size(); i++) {

            if (swimmerCoachDatabase.getMemberList().get(i) instanceof CompetitiveSwimmer) {
                System.out.printf("ID: %-5d Name: %-10s Phone Number: %-10s Age: %-15s State: %-5b Discipline: ",
                        swimmerCoachDatabase.getMemberList().get(i).getUniqueID(),
                        swimmerCoachDatabase.getMemberList().get(i).getName(),
                        swimmerCoachDatabase.getMemberList().get(i).getPhoneNumber(),
                        swimmerCoachDatabase.getMemberList().get(i).getAge(),
                        swimmerCoachDatabase.getMemberList().get(i).isIsMembershipActive());
                ((CompetitiveSwimmer) swimmerCoachDatabase.getMemberList().get(i)).printSwimDisciplineList();

            }
            else {
                System.out.printf("ID: %-5d Name: %-10s Phone Number: %-10s Age: %-15s State: %-5b",
                        swimmerCoachDatabase.getMemberList().get(i).getUniqueID(),
                        swimmerCoachDatabase.getMemberList().get(i).getName(),
                        swimmerCoachDatabase.getMemberList().get(i).getPhoneNumber(),
                        swimmerCoachDatabase.getMemberList().get(i).getAge(),
                        swimmerCoachDatabase.getMemberList().get(i).isIsMembershipActive());
                        System.out.println();
            }

        }
        while (true) {
            loginSystem();

            new MenuRun(">>>ENIGMA SOLUTION<<<", "V??lg en af nedenst??ende muligheder", new String[]{
                    "1. Tilj??j et nyt medlem.",
                    // Slet en burger
                    "2. Udprint af alle eksisterende medlemmer.",
                    "3. Oversigt over medlemmer i restance.",
                    // Betalings??ndringer
                    //??rets resultat - m??ske ogs?? noget med at se kun for junior, senior...
                    "4. Tilf??j nyt sv??mmeresultat.",
                    "5. Se sv??mme resultater", // v??lge om se alle eller en enkeltsv??mmer
                    "6. Oversigt over top 5 konkurrerende sv??mmere for en given sv??mmedisciplin.", // Forskellige sort typer,
                    "8. Oversigt over alle members for en coach",
                    "9. Log ud."
            }, currentUser, swimmerCoachDatabase);

        }
    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }


    // loginStuff ----------------------------------
    public String isLoggedIn() {
        String username = fileHandler.checkUsername(getUsername());

        if (!username.equals("0")) {
            for (int i = 1; i < 4; i++) {

                if (isPasswordCorrect(getPassword())) {

                    System.out.println("You're signed in");
                    return username;
                } else if (i != 3) {
                    System.out.println("you have " + (3 - i) + ((3 - i > 1) ? " tries left\n" : " try left\n"));
                }
            }
        }
        return "0";
    }

    private String getUsername() {
        System.out.print("Please enter your username: ");
        return ui.readLine();
    }

    private String getPassword() {
        System.out.print("Please enter your password: ");
        return ui.readLine();
    }

    private boolean isPasswordCorrect(String password) {
        return !fileHandler.checkPassword(password).equals("0");
    }
}
