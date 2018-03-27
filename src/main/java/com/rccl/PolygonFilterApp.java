package com.rccl;

import com.rccl.voyagegeography.model.VoyageGeography;

import javax.inject.Inject;

public class PolygonFilterApp {

    public static void main(String[] args) throws Exception {

        //Geography Impl

        VoyageGeographyImpl.insertVoyageGeography(new RegionCodeExtractor().getRegionCode(),
                new LandGeometryExtractor().getLandGeometry());

        //new VoyageResponseExtractor().getVoyageResponse();




    }
}
