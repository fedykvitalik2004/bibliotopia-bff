package org.vitalii.fedyk.bibliotopiabff.infrastructure.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to track the execution time of methods. When placed on a method, the {@link
 * TrackTimeAspect} measures its duration and logs the result.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {}
