package com.kam.andromate.model;

import com.kam.andromate.IConstants;

public class Link {

    public final static String JSON_TAG_NAME = "Links";

    public static final String TAG_FROM= "from";
    public static final String TAG_TO= "to";

    public static final String DEFAULT_FROM= IConstants.EMPTY_STRING;;
    public static final String DEFAULT_TO= IConstants.EMPTY_STRING;

    private String from;
    private String to;

    public Link(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Link{from='" + from + "', to='" + to + "'}";
    }
}

