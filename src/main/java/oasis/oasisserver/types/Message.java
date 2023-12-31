package oasis.oasisserver.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Direct message sent from one person to another.
 * @param id ID of this message
 * @param sender Sender of this message
 * @param recipient Recipient of this message
 * @param content Content
 * @param time Time
 */
public record Message(
        @JsonProperty @NonNull UUID id,
        @JsonProperty @NonNull UUID sender,
        @JsonProperty @NonNull UUID recipient,
        @JsonProperty @NonNull String content,
        @JsonProperty @NonNull DateTime time
) {}
