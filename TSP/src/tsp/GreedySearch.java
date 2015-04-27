package tsp;

 

import java.io.BufferedReader;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collections;

import java.util.List;

 

 

public class GreedySearch {

 

  /**

  * @param args

  */

  public static void main(String[] args) {

     // TODO Auto-generated method stub

     City[] cities = getCities("berlin52.txt");

     int root = (int) (Math.random()*cities.length);
//     int root = 4;
    

     List<Boolean> visitList=new ArrayList<Boolean>(Arrays.asList(new Boolean[cities.length]));

     Collections.fill(visitList, new Boolean(false));

    

     double totalDistance = 0;

     double [][] disArray = getArr(cities);

     System.out.print(root);

     int point = root;

     visitList.set(point,true);

     do{

       double minDistance = 99999;

       for (int i = 0; i < cities.length; i++){

          if(disArray[point][i] < minDistance &&  ! visitList.get(i)){

            minDistance = disArray[point][i];        

            point = i;

           

          }

         

       }

       totalDistance = totalDistance + minDistance ;

       visitList.set(point,true);

       System.out.print(","+point);

         

     }while(!allVisited(visitList));
     System.out.print(","+root);
     System.out.println("");
     System.out.print(totalDistance+disArray[point][root]);

 

  }

 

 

 

 

 

private static boolean allVisited(List<Boolean> visitList) {

     if(countVisited(visitList) == visitList.size()){

       return true;

     }

     return false;

  }

 

 

 

 

 

  private static int countVisited(List<Boolean> visitList) {

     int visited = 0;

     for (int i = 0; i < visitList.size(); i++){

       if(visitList.get(i))

          visited ++;

     }

     return visited ;

  }

 

 

 

 

 

  private static boolean canVisit(int root, int point, List<Boolean> visitList) {

     boolean canVisited = false;

    

     if(root == point){

      

       if (countVisited(visitList) + 1 == visitList.size()){

          canVisited = false;

       }

     }else{

       canVisited = !visitList.get(point);

     }

     return canVisited;

  }

 

 

 

 

 

  private static double[][] findNext( int point, double[][] visitList, double[][] disArray) {

    

     return visitList;

  }

 

  private static double[][] getArr(City[] cities) {

     double[][] disArray = new double [cities.length][cities.length];

     for (int i = 0; i < cities.length; i++){

       for (int j = 0; j < cities.length ; j++){

          if (i ==j){

            disArray[i][j]=99999;

          }else{

            double x = cities[i].getX() - cities[j].getX();

            double y = cities[i].getY() - cities[j].getY();

            disArray[i][j] =  Math.sqrt(x*x+y*y);

          }

       }

     }

     return disArray;

  }

 

  private static City[] getCities(String file) {

    

     City[] cities = new City[52];//ArrayList <City>

      

      FileReader fr2;

     try {

       fr2 = new FileReader(file);

 

         

      BufferedReader sr2 = new BufferedReader(fr2);

       

      for ( int i = 0; i < cities.length; i++){

         String s3 = sr2.readLine();

         String[] s2 = s3.split("\\s+");

         cities[i] = new City();

         cities[i].setX(Double.parseDouble(s2[1]));

         cities[i].setY(Double.parseDouble(s2[2]));

      }    

     } catch (IOException e) {

       // TODO Auto-generated catch block

       e.printStackTrace();

    

      }

     return cities;

          

    }

 

 

}