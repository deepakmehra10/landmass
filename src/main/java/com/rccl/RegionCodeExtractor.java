package com.rccl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccl.constants.Constants;
import com.rccl.regioncodeboundaries.RegionGeography;

import java.io.File;

public class RegionCodeExtractor {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public RegionGeography getRegionCode() throws Exception {
        
        RegionGeography regionGeography = MAPPER.readValue(new File(Constants.REGION_CODE_FILE_PATH),
                RegionGeography.class);
        
        return regionGeography;
    }
}
