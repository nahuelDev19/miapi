package com.api.rest.miapi.securitys;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGratedAuthoritiJsonCreator {


    @JsonCreator
    public SimpleGratedAuthoritiJsonCreator(@JsonProperty("authority") String role){}

}
