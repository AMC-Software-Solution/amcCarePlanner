package com.amc.careplanner.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.amc.careplanner.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.amc.careplanner.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.amc.careplanner.domain.User.class.getName());
            createCache(cm, com.amc.careplanner.domain.Authority.class.getName());
            createCache(cm, com.amc.careplanner.domain.User.class.getName() + ".authorities");
            createCache(cm, com.amc.careplanner.domain.Client.class.getName());
            createCache(cm, com.amc.careplanner.domain.Branch.class.getName());
            createCache(cm, com.amc.careplanner.domain.ServiceUser.class.getName());
            createCache(cm, com.amc.careplanner.domain.MedicalContact.class.getName());
            createCache(cm, com.amc.careplanner.domain.ServiceUserContact.class.getName());
            createCache(cm, com.amc.careplanner.domain.EmergencyContact.class.getName());
            createCache(cm, com.amc.careplanner.domain.ServiceUserLocation.class.getName());
            createCache(cm, com.amc.careplanner.domain.PowerOfAttorney.class.getName());
            createCache(cm, com.amc.careplanner.domain.Consent.class.getName());
            createCache(cm, com.amc.careplanner.domain.Access.class.getName());
            createCache(cm, com.amc.careplanner.domain.Equality.class.getName());
            createCache(cm, com.amc.careplanner.domain.QuestionType.class.getName());
            createCache(cm, com.amc.careplanner.domain.Question.class.getName());
            createCache(cm, com.amc.careplanner.domain.Answer.class.getName());
            createCache(cm, com.amc.careplanner.domain.EmployeeDesignation.class.getName());
            createCache(cm, com.amc.careplanner.domain.Employee.class.getName());
            createCache(cm, com.amc.careplanner.domain.EmployeeLocation.class.getName());
            createCache(cm, com.amc.careplanner.domain.EmployeeAvailability.class.getName());
            createCache(cm, com.amc.careplanner.domain.RelationshipType.class.getName());
            createCache(cm, com.amc.careplanner.domain.CarerServiceUserRelation.class.getName());
            createCache(cm, com.amc.careplanner.domain.DisabilityType.class.getName());
            createCache(cm, com.amc.careplanner.domain.Disability.class.getName());
            createCache(cm, com.amc.careplanner.domain.EligibilityType.class.getName());
            createCache(cm, com.amc.careplanner.domain.Eligibility.class.getName());
            createCache(cm, com.amc.careplanner.domain.Travel.class.getName());
            createCache(cm, com.amc.careplanner.domain.Payroll.class.getName());
            createCache(cm, com.amc.careplanner.domain.EmployeeHoliday.class.getName());
            createCache(cm, com.amc.careplanner.domain.ServceUserDocument.class.getName());
            createCache(cm, com.amc.careplanner.domain.TerminalDevice.class.getName());
            createCache(cm, com.amc.careplanner.domain.EmployeeDocument.class.getName());
            createCache(cm, com.amc.careplanner.domain.Communication.class.getName());
            createCache(cm, com.amc.careplanner.domain.DocumentType.class.getName());
            createCache(cm, com.amc.careplanner.domain.Notification.class.getName());
            createCache(cm, com.amc.careplanner.domain.Country.class.getName());
            createCache(cm, com.amc.careplanner.domain.Task.class.getName());
            createCache(cm, com.amc.careplanner.domain.ServiceUserEvent.class.getName());
            createCache(cm, com.amc.careplanner.domain.Currency.class.getName());
            createCache(cm, com.amc.careplanner.domain.ServiceOrder.class.getName());
            createCache(cm, com.amc.careplanner.domain.Invoice.class.getName());
            createCache(cm, com.amc.careplanner.domain.Timesheet.class.getName());
            createCache(cm, com.amc.careplanner.domain.ClientDocument.class.getName());
            createCache(cm, com.amc.careplanner.domain.SystemEventsHistory.class.getName());
            createCache(cm, com.amc.careplanner.domain.SystemSetting.class.getName());
            createCache(cm, com.amc.careplanner.domain.ExtraData.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
