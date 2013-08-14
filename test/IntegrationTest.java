import org.junit.Ignore;
import org.junit.Test;
import play.libs.F.Callback;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class IntegrationTest {

    @Test
    public void should_display_login_form() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource()).contains("Login");
            }
        });
    }

    @Ignore
    @Test
    public void should_log_user() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                browser.fill("#email").with("jdoe@mycompany.com");
                browser.fill("#password").with("password");
                System.err.println("before submit");
                browser.click("#submit");
                System.err.println("after submit");
                assertThat(browser.pageSource()).contains("Dashboard");
            }
        });
    }

}
