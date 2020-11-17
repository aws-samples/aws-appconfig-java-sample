package com.amazonaws.samples.appconfig.model;

import java.util.Objects;

public class ConfigurationKey {
    private final String compositeKey;

    private final String application;
    private final String environment;
    private final String configuration;

    /**
     * Tuple that serves as the primary key for retrieving configurations from AppConfig.
     *
     * @param application   Name or Id of the Application that the configuration belongs to. Corresponds to the 'application' field on
     *                      {@link software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest}
     * @param environment   Name or Id of the Environment that the configuration belongs to. Corresponds to the 'environment' field on
     *                      {@link software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest}
     * @param configuration Name or Id of the ConfigurationProfile that the configuration is associated with. Corresponds to the 'configuration'
     *                      field on {@link software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest}
     */
    public ConfigurationKey(final String application,
                            final String environment,
                            final String configuration) {

        this.application = application;
        this.environment = environment;
        this.configuration = configuration;
        this.compositeKey = String.join("::", application, environment, configuration);
    }

    public String getApplication() {
        return this.application;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    @Override
    public String toString() {
        return compositeKey;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final ConfigurationKey that = (ConfigurationKey) object;
        return Objects.equals(getApplication(), that.getApplication()) &&
                Objects.equals(getEnvironment(), that.getEnvironment()) &&
                Objects.equals(getConfiguration(), that.getConfiguration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApplication(), getEnvironment(), getConfiguration());
    }
}
