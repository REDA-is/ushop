
package org.reda.ushop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;


@Entity public class WebhookEventLog {
    @Id private String id;
    public WebhookEventLog() {}
    public WebhookEventLog(String id){ this.id = id; }
}
