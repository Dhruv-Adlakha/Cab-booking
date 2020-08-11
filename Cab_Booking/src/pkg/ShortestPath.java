/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

/**
 *
 * @author StylishDheeru
 */
public class ShortestPath {
    static int i1,i2;
                static int[][] mat;
                static int start,end;
                static final int V = 9;
                static int[][] graph = new int[][]{{0,7,0,0,0,0,0,0,0},{7,0,3,0,0,0,19,15,0},{0,3,0,16,0,0,0,0,9},{0,0,16,0,0,5,4,0,0},{0,0,0,0,0,3,0,0,0},{0,0,0,5,3,0,0,0,0},{0,19,0,4,0,0,0,0,0},{0,15,0,0,0,0,0,0,0},{0,0,9,0,0,0,0,0,0}};
                ShortestPath(int i1,int i2)
                {
                    this.i1 = i1;
                    this.i2 = i2;
                    mat = new int[9][9];
                }
                
                int minDistance(int dist[], Boolean sptSet[])
                {
                    int min = Integer.MAX_VALUE, min_index = -1;
                    for (int v = 0; v < V; v++)
                        if (sptSet[v] == false && dist[v] <= min) 
                        {
                            min = dist[v];
                            min_index = v;
                        }
                    return min_index;
                }
                   
                void copySolution(int dist[], int n,int src,int mat[][])
                {
                    System.arraycopy(dist, 0, mat[src], 0, V);
                }
                
           
                void dijkstra(int src)
                {
                    int dist[] = new int[V]; 
                

               
                    Boolean sptSet[] = new Boolean[V];

               
                    for (int i = 0; i < V; i++) 
                        {
                            dist[i] = Integer.MAX_VALUE;
                            sptSet[i] = false;
                        }

               
                        dist[src] = 0;

              
                        for (int count = 0; count < V - 1; count++)
                        {
               
               
                            int u = minDistance(dist, sptSet);

               
                            sptSet[u] = true;

                
                            for (int v = 0; v < V; v++)

               
                                if (!sptSet[v] && graph[u][v] != 0 &&
                                        dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                                dist[v] = dist[u] + graph[u][v];
                        }
                        copySolution(dist, V,src,mat);
                }
                static int give_dist(int i1,int i2)
                    {
                    return mat[i1][i2];
                    }
                static int give_fare(int i1,int i2)
                    {
                        return mat[i1][i2]*45;
                    }
                static int give_time(int i1,int i2)
                    {
                        return mat[i1][i2]*1000;
                    }

}
