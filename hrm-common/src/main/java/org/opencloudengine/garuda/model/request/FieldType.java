package org.opencloudengine.garuda.model.request;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by uengine on 2016. 9. 6..
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldType {

    String type();

    String description();
}
