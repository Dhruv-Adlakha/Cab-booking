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
public class UserName {
    static String user;
      static int driverindex;
      UserName(){
          
      }
      UserName(int driverindex){
          this.driverindex = driverindex;
      }
      UserName(String user){
          this.user = user;
      }
      String get_rider(){
          return user;
      }
}
