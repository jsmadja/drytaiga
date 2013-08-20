package misc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrettyStacktrace {

    private static final String NEW_LINE = System.getProperty("line.separator");

    private String stacktrace;

    public PrettyStacktrace(Throwable exception) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(os));
        this.stacktrace = new String(os.toByteArray());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<pre>");
        Scanner scanner = new Scanner(stacktrace);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern p = Pattern.compile("Caused by: (.*)");
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String group = m.group();
                line = "<span style='color:red;font-weight:bold;'>" + group + "</span>";
            } else {
                line = "<span style='margin-left:5px'>" + line + "</span>";
            }
            sb.append(line).append(NEW_LINE);
        }
        return sb.append("</pre>").toString().trim();
    }

}
