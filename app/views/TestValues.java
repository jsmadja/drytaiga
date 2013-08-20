package views;

import com.avaje.ebean.Ebean;
import models.*;
import play.Logger;

import static models.Profile.Administrator;
import static models.Profile.Customer;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class TestValues {

    public static void createAdministrator() {
        Member user = new Member("Julien", "Smadja", "julien.smadja@gmail.com", "password", Administrator);
        Ebean.save(user);
    }

    public static void createAccount(int accountNumber) {
        Account account = new Account(AccountType.values()[accountNumber % AccountType.values().length]);
        Ebean.save(account);

        Member user = new Member("John", "Doe", "jdoe+" + accountNumber + "@mycompany.com", "password", Customer);
        Ebean.save(user);

        account.setOwner(user);
        account.setCompany(new Company("My Company #" + accountNumber, account));
        account.addMember(user);

        Opening opening = new Opening("Programmer #" + accountNumber);
        opening.addComment(new Comment("First comment on opening", user));
        account.addOpening(opening);

        int max = fibo(accountNumber);
        for (int i = 0; i < max; i++) {
            int applicantId = (accountNumber * max) + i;
            Applicant applicant = new Applicant("Bob", "Lennon #" + applicantId, "blennon" + applicantId + "@gmail.com", account);
            applicant.addComment(new Comment("First comment on applicant", user));
            int index = Integer.parseInt(randomNumeric(1)) % ApplianceStatus.values().length;
            ApplianceStatus randomApplianceStatus = ApplianceStatus.values()[index];
            applicant.updateStatus(randomApplianceStatus);
            opening.addApplicant(applicant);
        }
        Logger.debug("Create " + max + " applicants for account #" + accountNumber);

        Ebean.update(account);
    }

    private static int fibo(int n) {
        if (n <= 1) {
            return n;
        }
        return fibo(n - 1) + fibo(n - 2);
    }

}
