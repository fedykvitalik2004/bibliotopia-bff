package org.vitalii.fedyk.bibliotopiabff.infrastructure.aop;

import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect for monitoring the performance of methods annotated with {@link TrackTime}. Measures
 * execution time and logs it.
 */
@Slf4j
@Aspect
@Component
public class TrackTimeAspect {
  @Around(value = "@annotation(trackTime)")
  @SneakyThrows
  public Object profileMethod(
      final ProceedingJoinPoint proceedingJoinPoint, final TrackTime trackTime) {
    final String methodName = proceedingJoinPoint.getSignature().toShortString();
    final long start = System.nanoTime();

    try {
      return proceedingJoinPoint.proceed();
    } catch (Throwable ex) {
      log.error("Method [{}] failed", methodName, ex);
      throw ex;
    } finally {
      final long duration = System.nanoTime() - start;
      final String formattedTime = this.formatDuration(Duration.ofNanos(duration));

      log.info("Method [{}] executed in {}", methodName, formattedTime);
    }
  }

  private String formatDuration(final Duration duration) {
    if (duration.toMillis() < 1) {
      return duration.toNanos() + " ns";
    } else if (duration.toMillis() < 1000) {
      return duration.toMillis() + " ms";
    } else {
      return String.format("%.2f s", duration.toMillis() / 1000.0);
    }
  }
}
