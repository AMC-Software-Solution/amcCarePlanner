package com.amc.careplanner.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Care Planner.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	private final AuthServer authServer = new AuthServer();
	public static class AuthServer {

        private String registerUrl = com.amc.careplanner.utils.Constants.AUTH_SERVER_DEFAULT_REGISTER_URL;


        public String getRegisterUrl() {
            return registerUrl;
        }

        public void setRegisterUrl(String registerUrl) {
            this.registerUrl = registerUrl;
        }
    }
	public AuthServer getAuthServer() {
		return authServer;
	}
}
