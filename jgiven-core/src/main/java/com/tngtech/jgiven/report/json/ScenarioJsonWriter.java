package com.tngtech.jgiven.report.json;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tngtech.jgiven.impl.Config;
import com.tngtech.jgiven.report.model.ReportModel;

public class ScenarioJsonWriter {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger log = LoggerFactory.getLogger( ScenarioJsonWriter.class );
    private final ReportModel model;

    public ScenarioJsonWriter( ReportModel model ) {
        this.model = model;
    }

    public void write( File file ) {
        String json = toString();
        try {
        	if( Config.config().isAppendReportEnabled() && file.exists() ) {
            	ReportModel fromJson = gson.fromJson(json, ReportModel.class);
            	model.getScenarios().stream().forEach(scenario -> fromJson.addScenarioModel(scenario));
            	Files.asCharSink(file, Charsets.UTF_8).write(fromJson.toString());
            } else {
        		Files.asCharSink(file, Charsets.UTF_8).write(json);
            }
            log.debug( "Written JSON to file {}, {}", file, json );
        } catch( IOException e ) {
        	throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return gson.toJson( model );
    }
}
