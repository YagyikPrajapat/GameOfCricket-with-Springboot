package com.cricket.cricketspringboot.model;

import com.cricket.cricketspringboot.enums.playerType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Player")
@Getter
@Setter
public class Player {
   @Id
   private String _id;
   private String name;
   private String teamId;
   private playerType playerType;

   public Player(){}
}
