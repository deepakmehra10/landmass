package com.rccl;

public class ClippedPolygon {
    
    float xIntersectPoint(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        
        float num = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4);
        float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        return num / den;
    }
    
    float yIntersectPoint(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        
        float num = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4);
        float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        return num / den;
    }
    
    int clipPolygon(float landmassPoints[][], int polySize, float x1, float y1, float x2, float y2) {
        
        float[][] clippedLandmass = new float[5][2];
        int clippedLandmassSize = 0;
        
        // (ix,iy),(kx,ky) are the co-ordinate values of
        // the points
        for (int currentRow = 0; currentRow < polySize; currentRow++) {
            // currentRow and nextRow form a line in polygon
            int nextRow = (currentRow + 1) % polySize;
            float currentRowX = landmassPoints[currentRow][0];
            float currentRowY = landmassPoints[currentRow][1];
            float nextRowX = landmassPoints[nextRow][0];
            float nextRowY = landmassPoints[nextRow][1];
            
            // Calculating position of first point
            // w.r.t. clipper line
            float iPosition = (x2 - x1) * (currentRowY - y1) - (y2 - y1) * (currentRowX - x1);
            
            // Calculating position of second point
            // w.r.t. clipper line
            float kPosition = (x2 - x1) * (nextRowY - y1) - (y2 - y1) * (nextRowX - x1);
            
            // Case 1 : When both points are inside
            if (iPosition < 0 && kPosition < 0) {
                
                //Only second point is added
                clippedLandmass[clippedLandmassSize][0] = nextRowX;
                clippedLandmass[clippedLandmassSize][1] = nextRowY;
                clippedLandmassSize++;
            }
            
            // Case 2: When only first point is outside
            else if (iPosition >= 0 && kPosition < 0) {
                
                clippedLandmass[clippedLandmassSize][0] = xIntersectPoint(x1,
                        y1, x2, y2, currentRowX, currentRowY, nextRowX, nextRowY);
                clippedLandmass[clippedLandmassSize][1] = yIntersectPoint(x1,
                        y1, x2, y2, currentRowX, currentRowY, nextRowX, nextRowY);
                clippedLandmassSize++;
                
                clippedLandmass[clippedLandmassSize][0] = nextRowX;
                clippedLandmass[clippedLandmassSize][1] = nextRowY;
                clippedLandmassSize++;
            }
            
            // Case 3: When only second point is outside
            else if (iPosition < 0 && kPosition >= 0) {
                
                //Only point of intersection with edge is added
                clippedLandmass[clippedLandmassSize][0] = xIntersectPoint(x1,
                        y1, x2, y2, currentRowX, currentRowY, nextRowX, nextRowY);
                clippedLandmass[clippedLandmassSize][1] = yIntersectPoint(x1,
                        y1, x2, y2, currentRowX, currentRowY, nextRowX, nextRowY);
                clippedLandmassSize++;
            }
            
            // Case 4: When both points are outside
            else {
                System.out.println("Inside Case 4");
                //No points are added
            }
            
        }
        
        // Copying new points into original array
        // and changing the no. of vertices
        polySize = clippedLandmassSize;
        
        for (int currentRow = 0; currentRow < polySize; currentRow++) {
            landmassPoints[currentRow][0] = clippedLandmass[currentRow][0];
            landmassPoints[currentRow][1] = clippedLandmass[currentRow][1];
        }
        
        
        return polySize;
        
    }
    
    void getLandmassGeometry(float[][] landmassPoints, int polySize, float[][] regionCodePoints, int clipperSize) {
        //currentRow and nextRow are two consecutive indexes
        for (int currentRow = 0; currentRow < clipperSize; currentRow++) {
            int nextRow = (currentRow + 1) % clipperSize;
            
            polySize = clipPolygon(landmassPoints, polySize, regionCodePoints[currentRow][0],
                    regionCodePoints[currentRow][1], regionCodePoints[nextRow][0],
                    regionCodePoints[nextRow][1]);
        }
        
        // Printing vertices of clipped polygon
        for (int currentRow = 0; currentRow < polySize; currentRow++)
            System.out.println("(" + landmassPoints[currentRow][0] + "," + landmassPoints[currentRow][1] + ")");
    }
}
