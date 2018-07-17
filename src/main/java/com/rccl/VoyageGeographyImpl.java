package com.rccl;

import com.datastax.driver.core.Session;
import com.google.inject.Inject;
import com.rccl.landmassboundaries.LandGeography;
import com.rccl.regioncodeboundaries.RegionGeography;
import com.rccl.voyagegeography.model.Bounds;
import com.rccl.voyagegeography.model.Features;
import com.rccl.voyagegeography.model.Geography;
import com.rccl.voyagegeography.model.Geometry;
import com.rccl.voyagegeography.model.Prop;
import com.rccl.voyagegeography.model.Properties;
import com.rccl.voyagegeography.model.VoyageGeography;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VoyageGeographyImpl {
    
    private static Session session;
    
    private ClippedPolygon clippedPolygon;
    
    private Config config = ConfigFactory.load();
    
    private static Geography.GeographyBuilder voyageGeographyResponseBuilder = Geography.builder();
    
    @Inject
    VoyageGeographyImpl(ClippedPolygon clippedPolygon) {
        this.clippedPolygon = clippedPolygon;
    }
    
    public void insertVoyageGeography(RegionGeography regionGeography, LandGeography landGeography) {
        
        getCassandraSession();
        
        regionGeography.getFeatures().forEach(result -> {
            List<List<List<Float>>> regionCoordinates = result.getGeometry().getCoordinates();
            List<Float> regionCodeCoordinates = regionCoordinates.stream().flatMap(List::stream)
                    .flatMap(List::stream).collect(Collectors.toList());
            
            // Region Code Points
            float[][] regionCodePoints = {{regionCodeCoordinates.get(1), regionCodeCoordinates.get(0)},
                    {regionCodeCoordinates.get(3), regionCodeCoordinates.get(2)},
                    {regionCodeCoordinates.get(5), regionCodeCoordinates.get(4)},
                    {regionCodeCoordinates.get(7), regionCodeCoordinates.get(6)}
            };
            
            List<Float> neBounds = new ArrayList<>();
            neBounds.add(regionCodeCoordinates.get(4));
            neBounds.add(regionCodeCoordinates.get(5));
            
            List<Float> swBounds = new ArrayList<>();
            swBounds.add(regionCodeCoordinates.get(0));
            swBounds.add(regionCodeCoordinates.get(1));
            
            Bounds.builder().NE(neBounds).SW(swBounds).build();
            
            voyageGeographyResponseBuilder.regionCode(result.getProperties().getRegionCode())
                    .bounds(Bounds.builder()
                            .NE(neBounds)
                            .SW(swBounds)
                            .build());
            
            VoyageGeography voyageGeography = insertLandMassCordBasedOnRegion(regionCodePoints, landGeography);
            
            Geography voyageGeographyResponse = voyageGeographyResponseBuilder.voyageGeography(voyageGeography).build();
            
            VoyageGeographyRepositoryImpl.insertVoyageGeography(voyageGeographyResponse.getRegionCode(),
                    voyageGeographyResponse.getVoyageGeography(), session);
            
        });
        
        session.close();
        
    }
    
    private void getCassandraSession() {
        
        final CassandraConnector client = new CassandraConnector();
        final String ipAddress = config.getString("cassandraHostName");
        final int port = config.getInt("cassandraPortName");
        System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
        session = client.connect(ipAddress, port);
    }
    
    private VoyageGeography insertLandMassCordBasedOnRegion(float[][] regionCodePoints,
                                                                   LandGeography landGeography) {
        
        VoyageGeography.VoyageGeographyBuilder voyageGeographyBuilder = VoyageGeography.builder();
        voyageGeographyBuilder.type("FeatureCollection");
        List<Features> featuresList = new ArrayList<>();
        landGeography.getFeatures().forEach(result -> {
            List<List<List<Float>>> coordinates = result.getGeometry().getCoordinates();
            List<Float> landCoordinates = coordinates.stream().flatMap(List::stream)
                    .flatMap(List::stream).collect(Collectors.toList());
            int landCoordinatesSize = landCoordinates.size() / 2;
            
            float[][] landmassPoints = new float[11000][2];
            int rowIndex;
            int coordinateCounter = 0;
            for (rowIndex = 0; rowIndex < landCoordinatesSize; rowIndex++) {
                landmassPoints[rowIndex][1] = landCoordinates.get(coordinateCounter++);
                landmassPoints[rowIndex][0] = landCoordinates.get(coordinateCounter++);
            }
            
            List<List<List<Float>>> clippedLandmassGeometry = clippedPolygon.getLandmassGeometry(landmassPoints,
                    landCoordinatesSize, regionCodePoints, 4);
            
            if (!(clippedLandmassGeometry.get(0).isEmpty())) {
                Features.FeaturesBuilder featuresBuilder = Features.builder();
                featuresBuilder.type("Feature");
                Geometry.GeometryBuilder geometryBuilder = Geometry.builder();
                geometryBuilder.type("Polygon")
                        .coordinates(clippedLandmassGeometry);
                
                featuresBuilder.geometry(geometryBuilder.build())
                        .properties(Properties.builder().prop(Prop.builder().description("Default").build()).build());
                featuresList.add(featuresBuilder.build());
            }
            
        });
        
        voyageGeographyBuilder.features(featuresList);
        return voyageGeographyBuilder.build();
        
    }
    
}
