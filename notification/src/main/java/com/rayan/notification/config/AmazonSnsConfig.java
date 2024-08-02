package com.rayan.notification.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Amazon SNS client.
 */
@Configuration
public class AmazonSnsConfig {

    private static final Logger logger = LoggerFactory.getLogger(AmazonSnsConfig.class);

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;

    public AWSCredentials awsCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    private AWSCredentials createAwsCredentials() {
        logger.debug("Creating AWS credentials with access key: {}", accessKey);
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonSNS amazonSNSClient() {
        logger.info("Configuring Amazon SNS client");
        return AmazonSNSClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(createAwsCredentials()))
            .withRegion(Regions.US_EAST_1)
            .build();
    }
}
