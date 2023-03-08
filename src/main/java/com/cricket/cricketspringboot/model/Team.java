package com.cricket.cricketspringboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "Team")
@Getter
@Setter
public class Team {

   @Id
   private String _id;

   private String teamName;
   private ArrayList<String> players = new ArrayList<>();

   public Team() {
   }

   public Team(String teamName) {
      for (int i = 0; i < 11; i++) {
         players.add(teamName + i);
      }
      this.teamName = teamName;
   }
}
