package lt.tastybytes.receptaiserver.model;

import jakarta.persistence.*;

@Entity
public class Feedback{
  
  private long id;

  private String rating; // temporary variable type

  private String comment;

  private Date feedbackDate;

  public void setID(long id){
      this.id = id;
  }
  
  public void setRating(String rating){
      this.rating = rating;
  }

  public void setCommnet(String comment){
      this.comment = comment;
  }

  public void setDate(Date date){
      this.feedbackDate = date;
  }
  
  public long getID(){
      return id;
  }
  
  public String getRating(){
      return rating;
  }

  public String getComment(){
      return comment;
  }

  public Date getFeedbackDate(){
      return feedbackDate
  }
  
}
