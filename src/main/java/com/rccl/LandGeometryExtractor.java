package com.rccl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccl.constants.Constants;
import com.rccl.landmassboundaries.LandGeography;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class LandGeometryExtractor {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //public static int max = 0;
   // public static int i = 0;
    public LandGeography getLandGeometry() throws Exception {

        LandGeography landGeography = MAPPER.readValue(new File(Constants.LAND_CODE_FILE_PATH),
                LandGeography.class);
        return landGeography;
    }
    
/*    public static void getLengthForPolygonWithMaxCoordinate() throws Exception {
    
        LandGeography geography = MAPPER.readValue(new File(Constants.LAND_CODE_FILE_PATH), LandGeography.class);
    
        geography.getFeatures().forEach(length -> {
            //int maxCount = 0;
            List<List<List<Float>>> coordinates = length.getGeometry().getCoordinates();
            List<List<Float>> collect = coordinates.stream().flatMap(List::stream).collect(Collectors.toList());
            int coordinateLength = collect.size();
            *//*if (maxCount < coordinateLength) {
                maxCount = coordinateLength;
            }
            *//*
            System.out.println(coordinateLength+"coordinatelength      " + "i: " +i);
            i++;
            getCoordinateLength(coordinateLength);
            
            //System.out.println(maxCount);
        });
    
        System.out.println(max + "Max value");
        }*/
    
        
    /*private static void getCoordinateLength(int size) {
       
        if(max < size){
          *//*  System.out.println(max+"max");
            System.out.println(size+"size");*//*
            max = size;
        }
        
    }*/
}
