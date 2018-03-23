package com.rccl.regioncodeboundaries;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Geography {
    
    String type;
    List<Feature> features;
    
}
