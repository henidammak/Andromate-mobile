package com.kam.andromate.model;

import org.json.JSONObject;

public abstract class PipelineTask {

    abstract public PipelineTask jsonToPipeLine(JSONObject jo);

}
