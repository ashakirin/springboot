package com.example.orderservice.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.modulith.events.FailedEventPublications;
import org.springframework.modulith.events.ResubmissionOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Outbox housekeeping.
 *
 * <p>Spring Modulith does not retry failed listener invocations on its own; this component is
 * what turns the registry into a self-healing outbox. Two scheduled jobs:
 * <ol>
 *   <li><b>Resubmit failed publications.</b> Runs every minute. Picks up any rows in
 *       {@code FAILED} state that are at least one minute old and asks Spring Modulith to retry
 *       them. The age filter prevents a tight loop while a downstream system is briefly down.</li>
 *   <li><b>Purge old archived publications.</b> Runs hourly. With
 *       {@code spring.modulith.events.completion-mode: archive}, completed rows are moved to
 *       {@code event_publication_archive} rather than deleted. Without periodic cleanup that
 *       table grows unbounded.</li>
 * </ol>
 */
@Component
class OutboxHousekeeping {

    private static final Logger log = LoggerFactory.getLogger(OutboxHousekeeping.class);

    private final FailedEventPublications failed;
    private final CompletedEventPublications completed;

    OutboxHousekeeping(FailedEventPublications failed, CompletedEventPublications completed) {
        this.failed = failed;
        this.completed = completed;
    }

    @Scheduled(fixedDelayString = "PT1M")
    void resubmitFailed() {
        ResubmissionOptions options = ResubmissionOptions.defaults()
                .withMinAge(Duration.ofMinutes(1))
                .withBatchSize(50);
        log.debug("Resubmitting failed event publications");
        failed.resubmit(options);
    }

    @Scheduled(fixedDelayString = "PT1H")
    void purgeArchive() {
        log.debug("Purging archived event publications older than 7 days");
        completed.deletePublicationsOlderThan(Duration.ofDays(7));
    }
}
