package controllers;

import formatters.BytesFormatter;
import misc.table.ColumnValueResolver;
import models.Account;
import play.mvc.Result;
import views.html.accounts.index;

import static misc.table.Html.labelOrProgressbar;
import static misc.table.TableFilter.createNode;

public class Accounts extends AjaxController {

    public static Result list() {
        return ok(index.render(user()));
    }

    public static Result all() {
        return ok(createNode(request(), Account.find.query(), resolvers()));
    }

    private static ColumnValueResolver[] resolvers() {
        return new ColumnValueResolver[]{
                companyName(),
                plan(),
                users(),
                openings(),
                applicants(),
                space()
        };
    }

    private static ColumnValueResolver<Account> space() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return account.getUsedSpace() + " / " + BytesFormatter.format(account.getAccountType().getTotalSpace());
            }

            @Override
            public Comparable value(Account account) {
                return account.getUsedSpaceSize();
            }
        };
    }

    private static ColumnValueResolver<Account> applicants() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return labelOrProgressbar(account.getApplicantCount(), account.getAccountType().getMaxApplicants());
            }

            @Override
            public Comparable value(Account account) {
                return account.getApplicantCount();
            }
        };
    }

    private static ColumnValueResolver<Account> openings() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return labelOrProgressbar(account.getOpeningCount(), account.getAccountType().getMaxOpenings());
            }

            @Override
            public Comparable value(Account account) {
                return account.getOpeningCount();
            }
        };
    }

    private static ColumnValueResolver<Account> users() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return labelOrProgressbar(account.getMemberCount(), account.getAccountType().getMaxUsers());
            }

            @Override
            public Comparable value(Account account) {
                return account.getMemberCount();
            }
        };
    }

    private static ColumnValueResolver<Account> plan() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return value(account).toString();
            }

            @Override
            public Comparable value(Account account) {
                return account.getAccountType().name();
            }
        };
    }

    private static ColumnValueResolver<Account> companyName() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return value(account).toString();
            }

            @Override
            public Comparable value(Account account) {
                return account.getCompany().getName();
            }
        };
    }

}
