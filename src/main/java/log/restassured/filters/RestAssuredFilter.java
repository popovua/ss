package log.restassured.filters;

import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestAssuredFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        final Prettifier prettifier = new Prettifier();

        final HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder.create("Request", requestSpec.getURI())
                .withMethod(requestSpec.getMethod())
                .withHeaders(toMapConverter(requestSpec.getHeaders()))
                .withCookies(toMapConverter(requestSpec.getCookies()));

        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachmentBuilder.withBody(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }

        final HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();

        new DefaultAttachmentProcessor().addAttachment(
                requestAttachment,
                new FreemarkerAttachmentRenderer("http-request.ftl")
        );

        final Response response = filterContext.next(requestSpec, responseSpec);

        final HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder.create(response.getStatusLine())
                .withResponseCode(response.getStatusCode())
                .withHeaders(toMapConverter(response.getHeaders()))
                .withBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody()))
                .build();

        new DefaultAttachmentProcessor().addAttachment(
                responseAttachment,
                new FreemarkerAttachmentRenderer("http-response.ftl"));


        return response;
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    private static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
        final Map<String, String> result = new HashMap<>();
        items.forEach(h -> result.put(h.getName(), h.getValue()));
        return result;
    }
}
