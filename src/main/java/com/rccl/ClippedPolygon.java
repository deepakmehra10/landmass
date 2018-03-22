package com.rccl;

public class ClippedPolygon {
    public int poly_size = 3;

    int x_intersect(int x1, int y1, int x2, int y2,
                    int x3, int y3, int x4, int y4)
    {
        int num = (x1*y2 - y1*x2) * (x3-x4) -
                (x1-x2) * (x3*y4 - y3*x4);
        int den = (x1-x2) * (y3-y4) - (y1-y2) * (x3-x4);
        return num/den;
    }

    int y_intersect(int x1, int y1, int x2, int y2,
                    int x3, int y3, int x4, int y4)
    {
        int num = (x1*y2 - y1*x2) * (y3-y4) -
                (y1-y2) * (x3*y4 - y3*x4);
        int den = (x1-x2) * (y3-y4) - (y1-y2) * (x3-x4);
        return num/den;
    }

    void clip(int poly_points[][],
              int x1, int y1, int x2, int y2)
    {
        int [][] new_points = new int[4][2];
        int new_poly_size = 0;

        // (ix,iy),(kx,ky) are the co-ordinate values of
        // the points
        for (int i = 0; i < poly_size; i++)
        {
            // i and k form a line in polygon
            int k = (i+1) % poly_size;
            int ix = poly_points[i][0], iy = poly_points[i][1];
            int kx = poly_points[k][0], ky = poly_points[k][1];

            // Calculating position of first point
            // w.r.t. clipper line
            int i_pos = (x2-x1) * (iy-y1) - (y2-y1) * (ix-x1);

            // Calculating position of second point
            // w.r.t. clipper line
            int k_pos = (x2-x1) * (ky-y1) - (y2-y1) * (kx-x1);

            // Case 1 : When both points are inside
            if (i_pos < 0  && k_pos < 0)
            {

                //Only second point is added
                new_points[new_poly_size][0] = kx;
                new_points[new_poly_size][1] = ky;
                new_poly_size++;
            }

            // Case 2: When only first point is outside
            else if (i_pos >= 0  && k_pos < 0)
            {

                new_points[new_poly_size][0] = x_intersect(x1,
                        y1, x2, y2, ix, iy, kx, ky);
                new_points[new_poly_size][1] = y_intersect(x1,
                        y1, x2, y2, ix, iy, kx, ky);
                new_poly_size++;

                new_points[new_poly_size][0] = kx;
                new_points[new_poly_size][1] = ky;
                new_poly_size++;
            }

            // Case 3: When only second point is outside
            else if (i_pos < 0  && k_pos >= 0)
            {

                //Only point of intersection with edge is added
                new_points[new_poly_size][0] = x_intersect(x1,
                        y1, x2, y2, ix, iy, kx, ky);
                new_points[new_poly_size][1] = y_intersect(x1,
                        y1, x2, y2, ix, iy, kx, ky);
                new_poly_size++;
            }

            // Case 4: When both points are outside
            else
            {
                //No points are added
            }

        }

        // Copying new points into original array
        // and changing the no. of vertices
        poly_size = new_poly_size;
        for (int i = 0; i < poly_size; i++)
        {
            //System.out.println("i for final print : "+i);
            poly_points[i][0] = new_points[i][0];
            poly_points[i][1] = new_points[i][1];
        }


        //return poly_size;

    }

    void suthHodgClip(int [][]poly_points,
                      int [][]clipper_points, int clipper_size)
    {
        //i and k are two consecutive indexes
        for (int i=0; i<clipper_size; i++)
        {
            //System.out.println("i inside suth: "+i);
            int k = (i+1) % clipper_size;

            clip(poly_points, clipper_points[i][0],
                    clipper_points[i][1], clipper_points[k][0],
                    clipper_points[k][1]);
        }

        // Printing vertices of clipped polygon
        for (int i=0; i < poly_size; i++)
            System.out.println("("+poly_points[i][0] + "," + poly_points[i][1]+")" );
    }
}
