package log.restassured.filters;

import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.PrintStream;

public class CustomRequestLoggingFilter extends RequestLoggingFilter {

    private final PrintStream printStream;

    public CustomRequestLoggingFilter(LogDetail logDetail) {
        super(logDetail);
        this.printStream = System.out;
    }

    public CustomRequestLoggingFilter(LogDetail logDetail, PrintStream stream) {
        super(logDetail, stream);
        this.printStream = stream;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        String name = "";

        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (stackTraceElement.getClassName().startsWith("kdt")) {
                name = stackTraceElement.getMethodName();
            }
        }

        String title = "*** [REQUEST] {" + name + "} ***";
        printRequestTitle(title);

        return super.filter(requestSpec, responseSpec, ctx);
    }

    private void printRequestTitle(String title) {
        for (int i = 0; i < title.length(); ++i) {
            printStream.print("-");
        }
        printStream.println();
        printStream.println(title);
        for (int i = 0; i < title.length(); ++i) {
            printStream.print("-");
        }
        printStream.println();
    }
}
