package com.rccl;

import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccl.exception.MappingException;
import com.rccl.voyagegeography.model.Bounds;
import com.rccl.voyagegeography.model.VoyageGeography;

import java.io.IOException;

import static com.rccl.constants.QueryConstants.INSERT_VOYAGE_GEOGRAPHY;

public class VoyageGeographyRepositoryImpl {
    
    
    private static ObjectMapper MAPPER = new ObjectMapper();
    
    private static <T> String convertObjectToString(T data) {
        
        try {
            return MAPPER.writeValueAsString(data);
        } catch (IOException ex) {
            System.out.println((ex.getMessage()));
            
            throw new MappingException("Unable to convert object to json", ex);
        }
    }
    
    
    public static void insertVoyageGeography(String regionCode, Bounds bounds, VoyageGeography voyageGeography, Session session) {
        
        String voyageGeographyData = convertObjectToString(voyageGeography);
        String boundsData = convertObjectToString(bounds);
        session.execute(INSERT_VOYAGE_GEOGRAPHY, regionCode, boundsData, voyageGeographyData);
    }
}
