package com.rccl;

import java.util.List;

public class PolygonFilterApp {
    
    public static void main(String[] args) throws Exception {
        //:TODO
        // Implementation goes here
        List<Float> regionCodeCoordinates = new RegionCodeExtractor().getRegionCode();
        
        ClippedPolygon clippedPolygon = new ClippedPolygon();
        
        int poly_size = 4;
        float[][] landmassPoints = new float[20][2];
        
        landmassPoints[0][0] = 56.36525013685606f;
        landmassPoints[0][1] = -133.9453125f;
        landmassPoints[1][0] = 50.736455137010665f;
        landmassPoints[1][1] = -136.40625f;
        landmassPoints[2][0] = 48.45835188280866f;
        landmassPoints[2][1] = -123.3984375f;
        landmassPoints[3][0] = 56.07203547180089f;
        landmassPoints[3][1] = -120.9375f;
        
        
        int clipper_size = 4;
        float[][] regionCodePoints = {{regionCodeCoordinates.get(1), regionCodeCoordinates.get(0)},
                {regionCodeCoordinates.get(3), regionCodeCoordinates.get(2)},
                {regionCodeCoordinates.get(5), regionCodeCoordinates.get(4)},
                {regionCodeCoordinates.get(7), regionCodeCoordinates.get(6)}
        };
        
        //Calling the clipping function
        clippedPolygon.getLandmassGeometry(landmassPoints, poly_size, regionCodePoints,
                clipper_size);
        
    }
}
