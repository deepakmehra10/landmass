package com.rccl.voyagegeography.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class VoyageGeography {
    String type;
    List<Features> features;

}
