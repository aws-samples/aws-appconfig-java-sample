package com.amazonaws.samples.appconfig.cache;

import software.amazon.awssdk.services.appconfig.model.BadRequestException;
import software.amazon.awssdk.services.appconfig.model.ResourceNotFoundException;

import java.time.Clock;
import java.time.Duration;
import java.util.Objects;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConfigurationCacheItem<T> {
    private final Duration ttl;
    private final Clock systemClock;
    private T value;
    private RuntimeException exception;
    private long refreshTime;

    public ConfigurationCacheItem(final Duration ttl) {
        this.ttl = ttl;
        this.systemClock = Clock.systemDefaultZone();
        this.refreshTime = this.systemClock.millis() + ttl.toMillis();
    }

    public static boolean isCacheableExceptionType(final Throwable ex) {
        return (ex instanceof ResourceNotFoundException || ex instanceof BadRequestException);
    }

    public Duration getTtl() {
        return ttl;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(final RuntimeException exception) {
        this.exception = exception;
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(final long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public boolean isRefreshNeeded() {
        return systemClock.millis() >= refreshTime;
    }

    public void calculateAndSetRefreshTime() {
        if (this.exception != null && !isCacheableExceptionType(exception)) {
            this.setRefreshTime(this.systemClock.millis());
        } else {
            this.setRefreshTime(this.systemClock.millis() + this.ttl.toMillis());
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final ConfigurationCacheItem<?> that = (ConfigurationCacheItem<?>) other;
        return getTtl() == that.getTtl() &&
                getRefreshTime() == that.getRefreshTime() &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getException(), that.getException());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTtl(), getValue(), getException(), getRefreshTime());
    }

    @Override
    public String toString() {
        return "ConfigurationCacheItem{" +
                "ttlInSeconds=" + ttl +
                ", value=" + value +
                ", exception=" + exception +
                ", refreshTime=" + refreshTime +
                '}';
    }
}
