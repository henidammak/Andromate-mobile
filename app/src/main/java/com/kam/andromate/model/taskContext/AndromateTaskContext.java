package com.kam.andromate.model.taskContext;

import java.util.HashMap;
import java.util.Map;

public class AndromateTaskContext {

    private final Map<String, Object> input;

    public AndromateTaskContext() {
        this.input = new HashMap<>();
    }

    public void setInput(String key, Object value) {
        this.input.put(key, value);
    }

    public Object getInput(String key) throws AndromateContextException {
        Object obj = this.input.getOrDefault(key, null);
        if (obj == null) {
            throw new AndromateContextException();
        }
        return obj;
    }

}
