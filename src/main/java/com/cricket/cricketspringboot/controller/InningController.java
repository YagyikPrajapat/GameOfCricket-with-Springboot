package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.services.InningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InningController {
   private InningService firstInning;
   private InningService secondInning;
   public InningController(){
      firstInning = new InningService();
      secondInning = new InningService();
   }

   @GetMapping("first_innings")
   public InningService firstInnings(Team battingTeam, Team bowlingTeam, int overs){
      firstInning.inningsStart(battingTeam, bowlingTeam, 1000, overs);
      return firstInning;
   }

   @GetMapping("second_innings")
   public InningService secondInnings(Team battingTeam, Team bowlingTeam, int overs){
      secondInning.inningsStart(battingTeam, bowlingTeam, firstInning.getTotalRuns(), overs);
      return secondInning;
   }

   @GetMapping("innings_result")
   public void showInningsResult(){
//      inningService.results();
   }
}
