package assn07;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> passwordManager = new PasswordManager<>();

        boolean correct = false;
        // originally has !(correct)
        while (correct == false) {
            System.out.println("Enter Master Password");
            String input = scanner.nextLine();
            correct = passwordManager.checkMasterPassword(input);
        }

        boolean cont = true;
        while (cont) {
            String input = scanner.nextLine();

            if (input.equals("New password")) {
                String website = scanner.nextLine();
                String password = scanner.nextLine();
                passwordManager.put(website, password);
                System.out.println("New password added");
            } else if (input.equals("Get password")) {
                String website = scanner.nextLine();
                if (passwordManager.get(website) == null) {
                    System.out.println("Account does not exist");
                } else {
                    System.out.println(passwordManager.get(website));
                }
            } else if (input.equals("Delete account")) {
                String website = scanner.nextLine();
                if (passwordManager.remove(website) == null) {
                    System.out.println("Account does not exist");
                } else {
                    System.out.println("Account deleted");
                }
            } else if (input.equals("Check duplicate password")) {
                String password = scanner.nextLine();
                ArrayList<String> websites = (ArrayList<String>) passwordManager.checkDuplicate(password);

                if (websites.size() == 0) {
                    System.out.println("No account uses that password");
                } else {
                    System.out.println("Websites using that password:");
                    for (int i = 0; i < websites.size(); i++) {
                        System.out.println(websites.get(i));
                    }
                }
            } else if (input.equals("Get accounts")) {
                Set<String> listy = passwordManager.keySet();
                System.out.println("Your accounts:");
                for (String item : listy) {
                    System.out.println(item);
                }

            } else if (input.equals("Generate random password")) {
                int length = scanner.nextInt();
                String pass = passwordManager.generatesafeRandomPassword(length);
                System.out.println(pass);
            } else if (input.equals("Exit")) {
                cont = false;
            } else {
                System.out.println("Command not found");
            }
        }
    }
}
