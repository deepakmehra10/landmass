package com.rccl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccl.constants.Constants;
import com.rccl.regioncodeboundaries.Geography;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class RegionCodeExtractor {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    ClassLoader classLoader = getClass().getClassLoader();
    
    public List<Float> getRegionCode() throws Exception {
        
        Geography geography = MAPPER.readValue(new File(classLoader.getResource(Constants.REGIONCODE_BOUNDARY).getFile()),
                Geography.class);
        List<List<List<Float>>> coordinates = geography.getFeatures().get(0).getGeometry().getCoordinates();
        List<Float> collect = coordinates.stream().flatMap(List::stream).flatMap(List::stream).collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }
}
