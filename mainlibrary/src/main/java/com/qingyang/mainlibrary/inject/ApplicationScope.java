package com.qingyang.mainlibrary.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by Jack Yang (jackyang@zepplabs.com) on 2/25/15.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {

}
