package com.mcloud.dbprovider.repository.mongdb.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MongoUser implements Serializable{

    @Id
    private String id;
    private Integer userId;

    private String title;

    private String userName;

    private String msgType;

    private String msgInfo;

    //getter setter


 //   @Override
 //   public String toString() {
 //       return "[ id ="+id+", title ="+title+", msgInfo="+msgInfo+" ]";
 //   }
}
