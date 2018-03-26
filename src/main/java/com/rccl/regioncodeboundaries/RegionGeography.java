package com.rccl.regioncodeboundaries;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class RegionGeography {
    
    String type;
    List<Feature> features;
    
}
