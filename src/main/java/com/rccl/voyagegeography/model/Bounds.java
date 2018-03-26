package com.rccl.voyagegeography.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class Bounds {

    List<Float> SW;
    List<Float> NE;
}
