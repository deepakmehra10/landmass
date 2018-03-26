package com.rccl;

import com.rccl.landmassboundaries.LandGeography;
import com.rccl.regioncodeboundaries.RegionGeography;

import java.util.List;
import java.util.stream.Collectors;

public class VoyageGeographyImpl {
    
    public static void insertVoyageGeography(RegionGeography regionGeography, LandGeography landGeography) {
        
        regionGeography.getFeatures().forEach(result -> {
            //System.out.println(result.getGeometry().getCoordinates());
            // System.out.println(result);
          /*  System.out.println("Region Code - " + result.getProperties().getRegionCode() + "\n" + "Coordinates --"
                    + result.getGeometry().getCoordinates());*/
            List<List<List<Float>>> regionCoordinates = result.getGeometry().getCoordinates();
            List<Float> regionCodeCoordinates = regionCoordinates.stream().flatMap(List::stream)
                    .flatMap(List::stream).collect(Collectors.toList());
            
            float[][] landmassPoints = new float[11000][2];
            
            //landmassPoints[][] =
          /*  landmassPoints[0][0] = 56.36525013685606f;
            landmassPoints[0][1] = -133.9453125f;
            landmassPoints[1][0] = 50.736455137010665f;
            landmassPoints[1][1] = -136.40625f;
            landmassPoints[2][0] = 48.45835188280866f;
            landmassPoints[2][1] = -123.3984375f;
            landmassPoints[3][0] = 56.07203547180089f;
            landmassPoints[3][1] = -120.9375f;
            */
            
            // Region Code Points
            
            float[][] regionCodePoints = {{regionCodeCoordinates.get(1), regionCodeCoordinates.get(0)},
                    {regionCodeCoordinates.get(3), regionCodeCoordinates.get(2)},
                    {regionCodeCoordinates.get(5), regionCodeCoordinates.get(4)},
                    {regionCodeCoordinates.get(7), regionCodeCoordinates.get(6)}
            };
            
            insertLandMassCoordBasedOnRegion(regionCodePoints, landGeography);
            
        });
        
    }
    
    public static void insertLandMassCoordBasedOnRegion(float[][] regionCodePoints, LandGeography landGeography) {
        landGeography.getFeatures().forEach(result -> {
            List<List<List<Float>>> coordinates = result.getGeometry().getCoordinates();
            List<Float> landCoordinates = coordinates.stream().flatMap(List::stream).flatMap(List::stream).collect(Collectors.toList());
            System.out.println(landCoordinates.size());
            
            
    
            float[][] landmassPoints = new float[11000][2];
            int rowIndex = 0;
            int coordinateCounter = 0;
            for (rowIndex = 0; rowIndex < landCoordinates.size()/2 ; rowIndex++){
                    landmassPoints[rowIndex][1] = landCoordinates.get(coordinateCounter++);
                    landmassPoints[rowIndex][0] = landCoordinates.get(coordinateCounter++);
                System.out.println("x =" +landmassPoints[rowIndex][0] + "y=" + landmassPoints[rowIndex][1] );
                
    
            }
            
    
        });
    
    }
    
}
