package com.rccl.voyagegeography.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Features {
    String type;
    Geometry geometry;
    Properties properties;
}
