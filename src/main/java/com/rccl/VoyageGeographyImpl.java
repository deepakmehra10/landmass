package com.rccl;

import com.rccl.landmassboundaries.LandGeography;
import com.rccl.regioncodeboundaries.RegionGeography;

import java.util.List;
import java.util.stream.Collectors;

public class VoyageGeographyImpl {

   public static ClippedPolygon clippedPolygon = new ClippedPolygon();
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
         
            
            // Region Code Points
            
            float[][] regionCodePoints = {{regionCodeCoordinates.get(1), regionCodeCoordinates.get(0)},
                    {regionCodeCoordinates.get(3), regionCodeCoordinates.get(2)},
                    {regionCodeCoordinates.get(5), regionCodeCoordinates.get(4)},
                    {regionCodeCoordinates.get(7), regionCodeCoordinates.get(6)}
            };
            System.out.println("region code: "+ result.getProperties().getRegionCode() + "\n\n");
            
            
            insertLandMassCordBasedOnRegion(regionCodePoints, landGeography);
            
        });
        
    }
    
    public static void insertLandMassCordBasedOnRegion(float[][] regionCodePoints, LandGeography landGeography) {
        landGeography.getFeatures().forEach(result -> {
            List<List<List<Float>>> coordinates = result.getGeometry().getCoordinates();
            List<Float> landCoordinates = coordinates.stream().flatMap(List::stream).flatMap(List::stream).collect(Collectors.toList());
           // System.out.println(landCoordinates.size());
            
            int landCoordinatesSize = landCoordinates.size()/2;
    
            float[][] landmassPoints = new float[11000][2];
            int rowIndex = 0;
            int coordinateCounter = 0;
            for (rowIndex = 0; rowIndex < landCoordinatesSize ; rowIndex++){
                    landmassPoints[rowIndex][1] = landCoordinates.get(coordinateCounter++);
                    landmassPoints[rowIndex][0] = landCoordinates.get(coordinateCounter++);
                //System.out.println("x =" +landmassPoints[rowIndex][0] + "y=" + landmassPoints[rowIndex][1] );
                

            }
            
            List<List<Float>> clippedLandmassGeometry = clippedPolygon.getLandmassGeometry(landmassPoints,landCoordinatesSize , regionCodePoints,
                    4);
            System.out.println("corresponding clipped polygon coordinates: "+clippedLandmassGeometry+"\n\n");
           // System.exit(0);

        });
    
    }
    
}
