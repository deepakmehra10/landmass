package com.rccl.landmassboundaries;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Feature {

    String type;
    Properties properties;
    Geometry geometry;

}
