package com.rccl.landmassboundaries;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Properties {
    
    int scalerank;
    String featureclass;
    
}
