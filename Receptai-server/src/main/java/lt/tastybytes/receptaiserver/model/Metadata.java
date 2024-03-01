package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

@Entity
public class Metadata{
  private String anonymousName;
  
  private String anonymousSurname;
  
  private String ipAdress;

  public void setAnonymousName(String name){
     anonymousName = name;
  }

  public void setAnonymousSurname(String surname){
     anonymousSurname = surname;
  }

  public void setIpAdress(String ipAdress){
     this.ipAdress = ipAdress;
  }

  public String getAnonymousName(){
     return anonymousName;
  }

  public String getAnonymousSurname(){
     return anonymousSurname;
  }

  public String getIpAdress(){
      return ipAdress;
  }
}
