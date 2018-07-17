package com.rccl.landmassboundaries;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Geometry {
    
    String type;
    List<List<List<Float>>> coordinates;
}
