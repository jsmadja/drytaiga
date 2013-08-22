package controllers;

import misc.table.ColumnValueResolver;
import misc.table.Html;
import models.Account;
import play.mvc.Result;
import views.html.accounts.index;

import static formatters.BytesFormatter.format;
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
                long usedSpace = account.getUsedSpace();
                long totalSpace = account.getTotalSpace();
                String tooltip = format(usedSpace) + " / " + format(totalSpace);
                return Html.progressbar(usedSpace, totalSpace, tooltip);
            }

            @Override
            public Comparable value(Account account) {
                return account.getUsedSpace();
            }
        };
    }

    private static ColumnValueResolver<Account> applicants() {
        return new ColumnValueResolver<Account>() {
            @Override
            public String label(Account account) {
                return labelOrProgressbar(account.getApplicantCount().longValue(), account.getAccountType().getMaxApplicants().longValue());
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
                return labelOrProgressbar(account.getOpeningCount().longValue(), account.getAccountType().getMaxOpenings().longValue());
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
                return labelOrProgressbar(account.getMemberCount().longValue(), account.getAccountType().getMaxUsers().longValue());
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
