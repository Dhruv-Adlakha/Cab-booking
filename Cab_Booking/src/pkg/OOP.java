package pkg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author StylishDheeru
 */
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;

class MyThread implements Runnable {
                static int travel_time;
                Thread t;
                int sleep_time;
                int driver_index;
                
                int droplocation=(ShortestPath.i2);
                MyThread(int sleep_time,int driver_index,int min)
                {
                    this.sleep_time = sleep_time;
                    this.driver_index=driver_index;
                    this.travel_time = 1000*min;
                    t = new Thread(this);
                    t.start();
                }
                public void run()
                {   
                    String u = UserName.user;
                    try{
                      //  System.out.println(Bookings.min);
                        //System.out.println(travel_time);
                        if(travel_time!=0){
                    JOptionPane.showMessageDialog(null,"Driver will arrive at pickup location in "+travel_time/1000+" minutes");}
                        else if(travel_time==0){
                            JOptionPane.showMessageDialog(null,"Driver Already at Location");
                        }
                    
                    new DriverDetails().setVisible(true);
                 
                    t.sleep(travel_time);
                    
                    JOptionPane.showMessageDialog(null,"Driver Has Arrived at Pickup Location");
                    
                    t.sleep(sleep_time);
                    new rating().setVisible(true);
                    
                    Bookings.trips++;
                    System.out.println(Bookings.trips);
                    if(Bookings.trips==3){
                           System.out.println("inside shuffle");
                            Bookings.shuffle();
                    }
                    ////////
                    JOptionPane.showMessageDialog(null,"Trip Ended for "+UserName.user);
                    
                    
                    
                    }
                    catch(InterruptedException ie){
                    JOptionPane.showMessageDialog(null,"Sleep Interrupted");
                    }
                    try{
                     Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","root");
                    Statement stmt=con.createStatement();
                    String query="Update driver set Availability=0 where S_No="+driver_index+";";
                      //  System.out.println("0");
                   String queryride ="Update login set ride=0 where userid='"+u+"';";
                   // System.out.println("1");
                    String query2="Update driver set Location="+droplocation+" where S_No="+driver_index+";";
                   // System.out.println("2");
                   stmt.executeUpdate(query);
                  // System.out.println("3");
                   stmt.executeUpdate(queryride);
                  // System.out.println("4");
                   stmt.executeUpdate(query2);
                  // System.out.println("5");
                     con.close();
                    stmt.close();
                }
                catch(Exception e)
                {
                    System.out.println("error in updating");
                }
                }
}
            class Bookings
            {
                
                static int loc[]=new int[10000];
                static int flag[]=new int[10000];
                static int trips=0;
                int[][] min_dist = ShortestPath.mat;
                double rating[]=new double[10000];
                static double driver_rating;
                int[] drivers = new int[10000];
                int start,end;
                 int min= 100000;
                int driver_index;
                static String Driver_Name="";
                static String carModel;
                static String carNo;
                Bookings(int start,int end)
                {
                    
                    this.start = start;
                    this.end = end;
                   // set_Val();
                }
                void set_Val()
                {   
                        try
                    {
                        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","root");
                        Statement stmt=con.createStatement();
                        String query="Select Availability,Location,Rating from driver;";
                        ResultSet m=stmt.executeQuery(query);
                        int i=0;
                        while(m.next())
                        {
                            loc[i]=m.getInt("Location");
                            flag[i]=m.getInt("Availability");
                            rating[i]=m.getInt("Rating");
                            i++;
                        }
                        m.close();
                        stmt.close();
                        con.close();
                    }
                    catch (Exception e)
                    {JOptionPane.showMessageDialog(null,"error");}
                }
            void find_driver(){


                 for(int j=0;j<9;j++){
                    //System.out.println(loc[j]);   
                }
   


                for(int i=0;i<9;i++){
    
                 if(min_dist[start][loc[i]]<min && flag[i]==0){   
       
                   min=min_dist[start][loc[i]];
         
                }
}
   // System.out.println("tst min");

    for(int i=0;i<9;i++){
        if(min_dist[start][loc[i]]==min && flag[i]==0) {
        drivers[i]=1;
        }
    }

double max=0.0;
for(int i=0;i<9;i++){
if(drivers[i]==1 && rating[i]>max && flag[i]==0){
    max = rating[i];
    driver_index=i;
    UserName.driverindex=i;
    driver_rating = max;
}
}

update_driver_status();
}

                void update_driver_status()
                {
                    
                    String u =UserName.user;
                    try{
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","root");
                    Statement stmt=con.createStatement();
                    String query="Update driver set Availability=1 where S_No="+driver_index+";";
                    String queryride ="Update login set ride=1 where userid='"+u+"';";
                    String query2="Select Driver_name,Rating,Car_Model,Car_No from driver where S_No="+driver_index+";";
                    stmt.executeUpdate(query);
                    stmt.executeUpdate(queryride);
                    ResultSet m=stmt.executeQuery(query2);
                    m.next();
                    Driver_Name=m.getString("Driver_name");
                    driver_rating=m.getDouble("Rating");
                    carModel =m.getString("Car_Model");
                    carNo =m.getString("Car_No");
                    con.close();
                    stmt.close();
                    m.close();
                    //JOptionPane.showMessageDialog(null,""+Driver_Name+"( Rated:- "+driver_rating+ ")"+" called for the ride");
                   // flag[driver_index]=1;// in SQL DATABASE, CREATE THIS FIELD ALSO, TO LET US KNOW THAT THE DRIVER IS OCCUPIED!!!
                    loc[driver_index] = end;
                    int time = ShortestPath.give_time(start,end);
                    MyThread t = new MyThread(time,driver_index,min); 
                   flag[driver_index]=0;
//                    try{
//                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","root");
//                    stmt=con.createStatement();
//                    query="Update driver set Availability=1 where S_No="+driver_index+";";
//                    stmt.executeUpdate(query);
//                    con.close();
//                    stmt.close();
//                    }
//                    catch (Exception e)
//                    {JOptionPane.showMessageDialog(null,"error");}
                    }
                    catch (Exception e)
                    {JOptionPane.showMessageDialog(null,"Driver Not Available");}
                    
                }
                
               static void shuffle(){
    ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<9; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i=0; i<9; i++) {
            if(flag[i]==0){
                loc[i]=list.get(i);
                System.out.println(list.get(i));
            }
        }
        
//        Collections.shuffle(list);
//        for(int i=9;i<15;i++){
//            if(flag[i]==0){
//                System.out.println(" "+list.get(i-9));
//                loc[i]=list.get(i-9);
//            }
//        }
        
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oop","root","root");
            Statement stmt=con.createStatement();
            for(int i=0;i<9;i++){
                String query1 = "Update driver set Location="+loc[i]+" where S_no="+i+";";
                stmt.executeUpdate(query1);
                
            }
            con.close();
            stmt.close();
            Bookings.trips=0;
        
        }
        catch(Exception e){
            System.out.println("error in shuffle");
        }
}
                
                
            }

            
////////////////////////////////////////////////////////////////////////////////////////////




public class OOP {  
     public static void main(String[] args) {
         new Registration().setVisible(true);
            
                    // TODO code application logic here
        }
    
}
