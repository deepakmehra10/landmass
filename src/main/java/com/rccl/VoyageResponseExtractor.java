package com.rccl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccl.constants.Constants;
import com.rccl.voyagegeography.model.Geography;

import java.io.File;

public class VoyageResponseExtractor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public void getVoyageResponse() throws Exception {

        Geography voyageGeography = MAPPER.readValue(new File(Constants.VOYAGE_GEOGRAPHY_FILE_PATH),
                Geography.class);
        System.out.println(voyageGeography);
    }
}