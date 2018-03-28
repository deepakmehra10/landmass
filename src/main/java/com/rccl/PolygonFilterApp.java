package com.rccl;

public class PolygonFilterApp {
    
    public static void main(String[] args) throws Exception {
        
        ClippedPolygon clippedPolygon = new ClippedPolygon();
        
        new VoyageGeographyImpl(clippedPolygon).insertVoyageGeography(new RegionCodeExtractor().getRegionCode(),
                new LandGeometryExtractor().getLandGeometry());
    }
}
