package com.tngtech.jgiven.impl;

import com.google.common.base.Optional;
import com.tngtech.jgiven.config.ConfigValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Helper class to access all system properties to configure JGiven.
 */
public class Config {
    private static final Logger log = LoggerFactory.getLogger( Config.class );
    private static final Config INSTANCE = new Config();

    private static final String AUTO = "auto";
    private static final String JGIVEN_REPORT_ENABLED = "jgiven.report.enabled";
    private static final String JGIVEN_APPEND_REPORT_ENABLED = "jgiven.append.report.enabled";
    public static final String JGIVEN_REPORT_DIR = "jgiven.report.dir";
    private static final String JGIVEN_REPORT_TEXT = "jgiven.report.text";
    private static final String JGIVEN_REPORT_TEXT_COLOR = "jgiven.report.text.color";
    private static final String JGIVEN_FILTER_STACK_TRACE = "jgiven.report.filterStackTrace";

    public static Config config() {
        return INSTANCE;
    }

    public Optional<File> getReportDir() {
        String reportDirName = System.getProperty( JGIVEN_REPORT_DIR );
        if( reportDirName == null ) {
        	reportDirName = "target/jgiven-reports/json";
//            if( System.getProperty( "surefire.test.class.path" ) != null ) {
//                reportDirName = "target/jgiven-reports/json";
//                log.info( JGIVEN_REPORT_DIR + " not set, but detected surefire plugin, generating reports to " + reportDirName );
//            } else {
//                reportDirName = "jgiven-reports";
//                log.debug( JGIVEN_REPORT_DIR + " not set, using default value jgiven-reports" );
//            }
        }

        File reportDir = new File( reportDirName );
        if( reportDir.exists() && !reportDir.isDirectory() ) {
            log.warn( reportDirName + " exists but is not a directory. Will not generate JGiven reports." );
            return Optional.absent();
        }

        log.debug( "Using folder " + reportDirName + " to store JGiven reports" );

        return Optional.of( reportDir );
    }

    public boolean isReportEnabled() {
        return Boolean.valueOf(System.getProperty( JGIVEN_REPORT_ENABLED, Boolean.TRUE.toString() ) );
    }

    public void setReportEnabled( boolean enabled ) {
        System.setProperty( JGIVEN_REPORT_ENABLED, "" + enabled );
    }

    public boolean isAppendReportEnabled() {
        return Boolean.valueOf(System.getProperty( JGIVEN_APPEND_REPORT_ENABLED, Boolean.TRUE.toString() ) );
    }

    public void setAppendReportEnabled( boolean enabled ) {
        System.setProperty( JGIVEN_APPEND_REPORT_ENABLED, "" + enabled );
    }
    
    public ConfigValue textColorEnabled() {
        return ConfigValue.fromString( System.getProperty( JGIVEN_REPORT_TEXT_COLOR, AUTO ) );
    }

    public boolean textReport() {
        return Boolean.valueOf( System.getProperty( JGIVEN_REPORT_TEXT, Boolean.TRUE.toString() ) );
    }

    public void setTextReport( boolean b ) {
        System.setProperty( JGIVEN_REPORT_TEXT, "" + b );
    }

    public boolean filterStackTrace() {
        return Boolean.valueOf( System.getProperty( JGIVEN_FILTER_STACK_TRACE, Boolean.TRUE.toString() ) );
    }

    public void setReportDir(File reportDir) {
        System.setProperty( JGIVEN_REPORT_DIR, reportDir.getAbsolutePath() );
    }
}
