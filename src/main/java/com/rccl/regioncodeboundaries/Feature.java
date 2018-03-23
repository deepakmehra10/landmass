package com.rccl.regioncodeboundaries;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Feature {
    String type;
    Properties properties;
    Geometry geometry;
}
