package com.rccl.landmassboundaries;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class LandGeography {

    String type;
    List<Feature> features;

}
