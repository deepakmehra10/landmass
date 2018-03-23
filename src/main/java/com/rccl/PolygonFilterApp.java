package com.rccl;


public class PolygonFilterApp {

    public static void main(String[] args) {

        ClippedPolygon clippedPolygon = new ClippedPolygon();
        int poly_size = 3;
        int[][] poly_points = new int[20][2];
        poly_points[0][0] = 100;
        poly_points[0][1] = 150;
        poly_points[1][0] = 200;
        poly_points[1][1] = 250;
        poly_points[2][0] = 300;
        poly_points[2][1] = 200;

        int clipper_size = 4;
        int [][]clipper_points = {{150,150}, {150,200},
            {200,200}, {200,150} };

        //Calling the clipping function
        clippedPolygon.suthHodgClip(poly_points, poly_size,clipper_points,
                clipper_size);

    }
}
