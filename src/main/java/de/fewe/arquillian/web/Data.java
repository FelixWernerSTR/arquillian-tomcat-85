package de.fewe.arquillian.web;

import java.io.Serializable;

public class Data implements Serializable {
  
  @Override
  public String toString() {
    return "Data [id=" + id + ", field1=" + field1 + ", field2=" + field2 + "]";
  }
  
  private static final long serialVersionUID = -7788619177798333712L;
  
  private int id;
  private String field1;
  private String field2;
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   * @return the field1
   */
  public String getField1() {
    return field1;
  }
  
  /**
   * @param field1 the field1 to set
   */
  public void setField1(String field1) {
    this.field1 = field1;
  }
  
  /**
   * @return the field2
   */
  public String getField2() {
    return field2;
  }
  
  /**
   * @param field2 the field2 to set
   */
  public void setField2(String field2) {
    this.field2 = field2;
  }
  
}
