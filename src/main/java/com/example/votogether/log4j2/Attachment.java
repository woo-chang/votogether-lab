package com.example.votogether.log4j2;

import lombok.Builder;

@Builder
public record Attachment(
        String fallback,
        String text,
        String color
) {
}
