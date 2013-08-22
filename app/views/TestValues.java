package views;

import com.avaje.ebean.Ebean;
import com.google.common.net.MediaType;
import misc.Unity;
import models.*;
import play.Logger;

import static com.google.common.net.MediaType.ZIP;
import static java.util.Arrays.asList;
import static models.AccountType.EstablishedCompany;
import static models.Profile.Administrator;
import static models.Profile.Customer;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class TestValues {

    public static void createValues() {
        if (Ebean.find(Account.class).findRowCount() == 0) {
            createAdministrator();
            for (Integer accountNumber : asList(1, 2, 3, 4, 5, 6, 7, 8)) {
                createAccount(accountNumber);
            }
        }
    }

    private static void createAdministrator() {
        Member user = new Member("Julien", "Smadja", "julien.smadja@gmail.com", "password", Administrator);
        Ebean.save(user);
    }

    private static void createAccount(int accountNumber) {
        Account account = new Account(randomAccountType());
        account.setCompany(new Company("My Company #" + accountNumber, account));
        account.save();

        Member user = new Member("John", "Doe", "jdoe+" + accountNumber + "@mycompany.com", "password", Customer);
        account.setOwner(user);
        user.save();

        createMembers(account);
        createOpenings(account, user);
        createApplicants(accountNumber, account, user, account.getOpenings().get(0));
        account.update();
        Logger.debug("Account #" + account.getId() + " " + account.getAccountType().name() + " created successfully");
        Logger.debug("---");
    }

    private static AccountType randomAccountType() {
        return AccountType.values()[(accountTypeIndex++) % AccountType.values().length];
    }

    private static int accountTypeIndex = 0;

    private static void createApplicants(int accountNumber, Account account, Member user, Opening opening) {
        int applicantCount = applicantCount(account);
        for (int i = 0; i < applicantCount; i++) {
            int applicantId = i;
            Applicant applicant = new Applicant("Bob", "Lennon #" + applicantId, "blennon" + applicantId + "@gmail.com", account);
            applicant.addComment(new Comment("First comment on applicant", user));
            addDocument(account, i, applicant);
            int index = Integer.parseInt(randomNumeric(1)) % ApplianceStatus.values().length;
            ApplianceStatus randomApplianceStatus = ApplianceStatus.values()[index];
            applicant.updateStatus(randomApplianceStatus);
            opening.addApplicant(applicant);
        }
        Logger.debug("Create " + applicantCount + " applicants for account #" + accountNumber);
    }

    private static void addDocument(Account account, int i, Applicant applicant) {
        Document document = new Document("doc#" + i, "http://", Unity.MEGA.toBytes(3), ZIP.toString());
        if(account.accept(document)) {
            applicant.addDocument(document);
        }
    }

    private static void createMembers(Account account) {
        int memberCount = memberCount(account);
        for (int i = 0; i < memberCount; i++) {
            Member member = new Member("John", "Doe", "jdoe+" + account.getId() + "_" + i + "@mycompany.com", "password", Customer);
            account.addMember(member);
        }
        Logger.debug("Create " + memberCount + " members for account #" + account.getId());
    }

    private static void createOpenings(Account account, Member user) {
        int openingCount = openingCount(account);
        for (int i = 0; i < openingCount; i++) {
            Opening opening = new Opening("Programmer #" + account.getId() + "_" + i);
            opening.addComment(new Comment("First comment on opening", user));
            account.addOpening(opening);
        }
        Logger.debug("Create " + openingCount + " openings for account #" + account.getId());
    }

    private static int memberCount(Account account) {
        return Math.min(EstablishedCompany.getMaxUsers() * 100, misc.Math.randomInt(account.getAccountType().getMaxUsers() - 1));
    }

    private static int applicantCount(Account account) {
        return Math.min(EstablishedCompany.getMaxApplicants(), misc.Math.randomInt(account.getAccountType().getMaxApplicants()));
    }

    private static int openingCount(Account account) {
        int count = misc.Math.randomInt(account.getAccountType().getMaxOpenings());
        if (count == 0) {
            count = 1;
        }
        return Math.min(EstablishedCompany.getMaxOpenings() * 100, count);
    }

}
