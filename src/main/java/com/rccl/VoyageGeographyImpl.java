package com.rccl;

import akka.Done;
import akka.persistence.cassandra.session.javadsl.CassandraSession;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rccl.exception.MappingException;
import com.rccl.landmassboundaries.LandGeography;
import com.rccl.regioncodeboundaries.RegionGeography;
import com.rccl.voyagegeography.model.Bounds;
import com.rccl.voyagegeography.model.Features;
import com.rccl.voyagegeography.model.Geography;
import com.rccl.voyagegeography.model.Geometry;
import com.rccl.voyagegeography.model.Prop0;
import com.rccl.voyagegeography.model.Properties;
import com.rccl.voyagegeography.model.VoyageGeography;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.rccl.constants.QueryConstants.INSERT_VOYAGE_GEOGRAPHY;

public class VoyageGeographyImpl {

   // private static  CassandraSession cassandraSession;

    private  static Session session;



   public static ClippedPolygon clippedPolygon = new ClippedPolygon();

   public static Geography.GeographyBuilder voyageGeographyResponseBuilder = Geography.builder();

    public static void insertVoyageGeography(RegionGeography regionGeography, LandGeography landGeography) {

        getCassandraSession();

        regionGeography.getFeatures().forEach(result -> {
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
            List<Float> neBounds = new ArrayList<>();
            neBounds.add(regionCodeCoordinates.get(4));
            neBounds.add(regionCodeCoordinates.get(5));
            List<Float> swBounds = new ArrayList<>();
            swBounds.add(regionCodeCoordinates.get(0));
            swBounds.add(regionCodeCoordinates.get(1));
            Bounds.builder().NE(neBounds).SW(swBounds).build();

            voyageGeographyResponseBuilder.regionCode(result.getProperties().getRegionCode())
                    .bounds(Bounds.builder().NE(neBounds).SW(swBounds).build());
           // System.out.println("region code: "+ result.getProperties().getRegionCode() + "\n\n");
            VoyageGeography voyageGeography = insertLandMassCordBasedOnRegion(regionCodePoints, landGeography);

             Geography voyageGeographyResponse = voyageGeographyResponseBuilder.voyageGeography(voyageGeography).build();

            VoyageGeographyRepositoryImpl.insertVoyageGeography (voyageGeographyResponse.getRegionCode(), voyageGeographyResponse.getVoyageGeography(),session);

        });
        
        
   session.close();
        
        
    }

    private static void getCassandraSession(){
        final CassandraConnector client = new CassandraConnector();
        final String ipAddress = "127.0.0.1";
        final int port = 9042;
        System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");

      session =   client.connect(ipAddress, port);
    }
    
    public static VoyageGeography insertLandMassCordBasedOnRegion(float[][] regionCodePoints, LandGeography landGeography) {
        VoyageGeography.VoyageGeographyBuilder voyageGeographyBuilder = VoyageGeography.builder();
        voyageGeographyBuilder.type("FeatureCollection");
        List<Features> featuresList = new ArrayList<>();
        landGeography.getFeatures().forEach(result -> {
            List<List<List<Float>>> coordinates = result.getGeometry().getCoordinates();
            List<Float> landCoordinates = coordinates.stream().flatMap(List::stream)
                    .flatMap(List::stream).collect(Collectors.toList());
            int landCoordinatesSize = landCoordinates.size()/2;

            float[][] landmassPoints = new float[11000][2];
            int rowIndex = 0;
            int coordinateCounter = 0;
            for (rowIndex = 0; rowIndex < landCoordinatesSize ; rowIndex++){
                    landmassPoints[rowIndex][1] = landCoordinates.get(coordinateCounter++);
                    landmassPoints[rowIndex][0] = landCoordinates.get(coordinateCounter++);
            }
            
            List<List<List<Float>>> clippedLandmassGeometry = clippedPolygon.getLandmassGeometry(landmassPoints,
                    landCoordinatesSize , regionCodePoints,4);
                   //if(!(clippedLandmassGeometry.stream().map(ele->ele.stream().map(inner-> inner.isEmpty())))) {
            if(!(clippedLandmassGeometry.get(0).isEmpty())) {
            Features.FeaturesBuilder featuresBuilder = Features.builder();
                       featuresBuilder.type("Feature");
                       Geometry.GeometryBuilder geometryBuilder = Geometry.builder();
                       geometryBuilder.type("Polygon")
                               .coordinates(clippedLandmassGeometry);

                       featuresBuilder.geometry(geometryBuilder.build())
                               .properties(Properties.builder().prop0(Prop0.builder().description("Default").build()).build());
                       featuresList.add(featuresBuilder.build());
                   }

        });
        voyageGeographyBuilder.features(featuresList);
        return voyageGeographyBuilder.build();

    }
    
}
