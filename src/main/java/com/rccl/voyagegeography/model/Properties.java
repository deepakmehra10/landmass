package com.rccl.voyagegeography.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Properties {
  
    @JsonProperty("prop0")
    Prop prop;
}
