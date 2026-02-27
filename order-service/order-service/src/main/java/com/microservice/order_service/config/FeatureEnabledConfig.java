package com.microservice.order_service.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Data
public class FeatureEnabledConfig {

    @Value("${features.user-tracking-enabled}")
    private boolean isUserTrackingEnabled;
}
