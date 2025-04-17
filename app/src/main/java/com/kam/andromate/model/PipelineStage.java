package com.kam.andromate.model;

import org.json.JSONObject;

public abstract class PipelineStage {

    abstract public PipelineStage jsonToPipeLine(JSONObject jo);

}
