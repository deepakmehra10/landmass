package com.rccl.landmassboundaries;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Properties {
    
    @JsonProperty("scalerank")
    int scaleRank;
    
    @JsonProperty("featureclass")
    String featureClass;
    
}
