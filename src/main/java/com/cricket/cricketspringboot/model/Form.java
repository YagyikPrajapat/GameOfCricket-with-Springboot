package com.cricket.cricketspringboot.model;


import com.cricket.cricketspringboot.enums.TossChoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Form {
   private String team1;
   private String team2;
   private TossChoice tossChoice;
}
