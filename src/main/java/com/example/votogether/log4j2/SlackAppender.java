package com.example.votogether.log4j2;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.io.Serializable;
import java.util.List;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Plugin(name = "Slack", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class SlackAppender extends AbstractAppender {

    private static final String ICON = ":votogether:";
    private static final String COLOR = "#FF4444";
    private static final String ERROR_TEXT = "서버 에러 발생";

    private final WebClient webClient;
    private final String url;
    private final String channel;
    private final String username;

    public SlackAppender(
            String name,
            Filter filter,
            Layout<? extends Serializable> layout,
            boolean ignoreExceptions,
            Property[] properties,
            WebClient webClient,
            String url,
            String channel,
            String username
    ) {
        super(name, filter, layout, ignoreExceptions, properties);
        this.webClient = webClient;
        this.url = url;
        this.channel = channel;
        this.username = username;
    }

    @PluginFactory
    public static SlackAppender createAppender(
            @PluginAttribute("name") @Required(message = "이름이 제공되지 않았습니다.") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") Filter filter,
            @PluginAttribute("url") @Required(message = "웹훅 url이 제공되지 않았습니다.") String url,
            @PluginAttribute("channel") @Required(message = "채널이 제공되지 않았습니다.") String channel,
            @PluginAttribute("username") @Required(message = "유저이름이 제공되지 않았습니다.") String username
    ) {
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .doOnConnected(connection ->
                        connection.addHandlerFirst(new ReadTimeoutHandler(3))
                                .addHandlerLast(new WriteTimeoutHandler(3))
                )
                .compress(true);

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return new SlackAppender(name, filter, layout, false, Property.EMPTY_ARRAY, webClient, url, channel, username);
    }

    @Override
    public void append(final LogEvent event) {
        if (event.getMessage() != null) {
            String logStatement = event.getMessage().getFormattedMessage();

            Attachment attachment = Attachment.builder()
                    .fallback(logStatement)
                    .text(getLayout().toSerializable(event).toString())
                    .color(COLOR)
                    .build();

            SlackMessage slackMessage = SlackMessage.builder()
                    .channel(channel)
                    .text(ERROR_TEXT)
                    .attachments(List.of(attachment))
                    .iconEmoji(ICON)
                    .username(username)
                    .build();

            webClient.post()
                    .uri(url)
                    .bodyValue(slackMessage)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe(null, error -> {
                        System.out.println(error);
                    });
        }
    }
}
