package com.rccl.voyagegeography.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Geography {
    String regionCode;
    Bounds bounds;
    VoyageGeography voyageGeography;
}
