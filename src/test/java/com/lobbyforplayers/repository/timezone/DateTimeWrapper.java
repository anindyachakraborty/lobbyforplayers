package com.lobbyforplayers.repository.timezone;

import java.io.Serializable;
import java.time.*;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DATETIMEWRAPPER")
public class DateTimeWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Instant instant;

    private LocalDateTime localDateTime;

    private OffsetDateTime offsetDateTime;

    private ZonedDateTime zonedDateTime;

    private LocalTime localTime;

    private OffsetTime offsetTime;

    private LocalDate localDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public OffsetTime getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(OffsetTime offsetTime) {
        this.offsetTime = offsetTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateTimeWrapper dateTimeWrapper = (DateTimeWrapper) o;
        return !(dateTimeWrapper.getId() == null || getId() == null) && Objects.equals(getId(), dateTimeWrapper.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeZoneTest{" +
            "id=" + id +
            ", instant=" + instant +
            ", localDateTime=" + localDateTime +
            ", offsetDateTime=" + offsetDateTime +
            ", zonedDateTime=" + zonedDateTime +
            '}';
    }
}
