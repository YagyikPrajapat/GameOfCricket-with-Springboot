package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.controller.InningController;
import com.cricket.cricketspringboot.controller.ScoreboardController;
import com.cricket.cricketspringboot.controller.TeamController;
import com.cricket.cricketspringboot.enums.Format;
import com.cricket.cricketspringboot.enums.TossChoice;
import com.cricket.cricketspringboot.model.Scoreboard;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class T20Service {
   @Autowired
   private PlayerRepository playerRepository;
   @Autowired
   private PlayerStatsRepository playerStatsRepository;
   @Autowired
   private InningController inningController = new InningController();
   @Autowired
   private TeamController teamController;
   @Autowired
   private ScoreboardController scoreboardController;
   private Team team1;
   private Team team2;
   private Team battingTeam;
   private Team bowlingTeam;
   private static final int OVERS = 20;
   public String setMatchDetails(String teamName1, String teamName2){
      team1 = teamController.getTeamByName(teamName1);
      team2 = teamController.getTeamByName(teamName2);
      return "response : true";
   }

   public String matchStarts(){
      InningService firstInning = inningController.firstInnings(battingTeam, bowlingTeam, OVERS);
      InningService secondInning = inningController.secondInnings(bowlingTeam, battingTeam, OVERS);
      Scoreboard scoreboard = new Scoreboard("", Format.T20, firstInning.getTotalRuns(), firstInning.getTotalWickets(),
              secondInning.getTotalRuns(), secondInning.getTotalWickets(), battingTeam.get_id(), bowlingTeam.get_id(),
              new ArrayList<>());
      scoreboardController.addScoreboard(scoreboard);
      firstInning.savingPlayerStats(scoreboard.get_id(), playerRepository, playerStatsRepository);
      secondInning.updatingPlayerStats(scoreboard.get_id(), playerRepository, playerStatsRepository);
      scoreboardController.updateScoreboard(scoreboard.get_id());
      return "done";
   }

   public String tossResult(TossChoice tossChoice){
      boolean tossResult = TossService.toss(tossChoice);
      System.out.println(tossResult);
      //TODO assuming bowling pitch
      bowlingTeam = (tossResult == true) ? (team1) : (team2);
      battingTeam = (tossResult == false) ? (team1) : (team2);
      String result = (tossResult == true) ? (team1.getTeamName()+" won the toss and choose to bowl first") :
              (team2.getTeamName()+" won the toss and choose to bowl first");
      System.out.println(result);
      return "toss";
   }
}
