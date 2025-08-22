// src/main/java/org/reda/ushop/config/StripeConfig.java
package org.reda.ushop.configu;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    public StripeConfig(@Value("${stripe.secret-key}") String key) {
        Stripe.apiKey = key;
    }
}
