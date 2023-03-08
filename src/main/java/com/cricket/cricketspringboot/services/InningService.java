package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.PlayerStats;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Service
public class InningService {
   private Team battingTeam;
   private Team bowlingTeam;
   private List<Integer> playerRuns = new ArrayList<>(Collections.nCopies(11, 0));
   private List<Integer> ballsPlayed = new ArrayList<>(Collections.nCopies(11, 0));
   private List<Integer> runsGiven = new ArrayList<>(Collections.nCopies(11, 0));
   private List<Integer> ballsThrown = new ArrayList<>(Collections.nCopies(11, 0));
   private List<Integer> wicketsTaken = new ArrayList<>(Collections.nCopies(11, 0));

   private int strike = 0, non_strike = 1, next_batsman = 2, bowler = 6;
   private int totalWickets = 0, totalRuns = 0;
   public String inningsStart(Team battingTeam, Team bowlingTeam, int target, int overs){
      this.battingTeam = battingTeam;
      this.bowlingTeam = bowlingTeam;
      for (int i = 0; i < overs; i++) {
         int ball = 0, flag = 0;
         for (int j = 0; j < 6; j++) {
            if (totalWickets >= 10 || totalRuns > target) {
               flag = 1;
               break;
            }
            ball = (int) (Math.random() * 8);
            updateBallsPlayedAndThrown(strike, bowler);  //updating model.Scoreboard
            ballDecision(ball);
         }
         if (flag == 1) break;
         swapPlayers();
         bowler++;
         if (bowler > 10) bowler = 6;
         //System.out.println(" ** over ends **");
      }
      return "";
   }
   private void swapPlayers() {
      int temp = strike;
      strike = non_strike;
      non_strike = temp;
   }

   private void ballDecision(int ball) {
      if (ball == 7) {
         //System.out.print("Wicket ");
         totalWickets++;
         wicketsTaken.set(bowler, wicketsTaken.get(bowler) + 1); //updating wickets taken
         strike = next_batsman;
         next_batsman++;
      } else {
         //System.out.print(ball + " ");
         totalRuns += ball;
         updateRunsScoredAndRunsGiven(strike, bowler, ball);
         if (ball % 2 != 0) {
            swapPlayers();
         }
      }
   }

   public void savingPlayerStats(String scoreboardId, PlayerRepository playerRepository, PlayerStatsRepository playerStatsRepository){
      List<Player> battingPlayerList = playerRepository.findByTeamId(battingTeam.get_id());
      List<Player> bowlingPlayerList = playerRepository.findByTeamId(bowlingTeam.get_id());
      for(int i=0;i<11;i++){
         PlayerStats battingPlayerStats = new PlayerStats(UUID.randomUUID().toString(), battingPlayerList.get(i).get_id(), scoreboardId,
                 battingPlayerList.get(i).getName(), playerRuns.get(i), ballsPlayed.get(i),0,0,0);
         playerStatsRepository.save(battingPlayerStats);
      }
      for(int i=0;i<11;i++){
         PlayerStats bowlingPlayerStats = new PlayerStats(UUID.randomUUID().toString(), bowlingPlayerList.get(i).get_id(), scoreboardId,
                 bowlingPlayerList.get(i).getName() ,0, 0,runsGiven.get(i),ballsThrown.get(i),wicketsTaken.get(i));
         playerStatsRepository.save(bowlingPlayerStats);
      }
   }
   public void updatingPlayerStats(String scoreboardId, PlayerRepository playerRepository, PlayerStatsRepository playerStatsRepository){
      List<Player> battingPlayerList = playerRepository.findByTeamId(battingTeam.get_id());
      List<Player> bowlingPlayerList = playerRepository.findByTeamId(bowlingTeam.get_id());
      for(int i=0;i<11;i++){
         PlayerStats battingPlayerStats = playerStatsRepository.findByPlayerIdAndScoreboardId(battingPlayerList.get(i).get_id(), scoreboardId);
         PlayerStats bowlingPlayerStats = playerStatsRepository.findByPlayerIdAndScoreboardId(bowlingPlayerList.get(i).get_id(), scoreboardId);
         battingPlayerStats.setRunsScored(playerRuns.get(i)); battingPlayerStats.setBallsPlayed(ballsPlayed.get(i));
         bowlingPlayerStats.setRunsGiven(runsGiven.get(i)); bowlingPlayerStats.setBallsThrown(ballsThrown.get(i));
         bowlingPlayerStats.setWicketsTaken(wicketsTaken.get(i));
         playerStatsRepository.save(battingPlayerStats);
         playerStatsRepository.save(bowlingPlayerStats);
      }
   }

   private void updateRunsScoredAndRunsGiven(int strike, int bowler, int ball) {
      playerRuns.set(strike, playerRuns.get(strike) + ball);
      runsGiven.set(bowler, runsGiven.get(bowler) + ball);
   }

   private void updateBallsPlayedAndThrown(int strike, int bowler) {
      ballsPlayed.set(strike, ballsPlayed.get(strike) + 1);
      ballsThrown.set(bowler, ballsThrown.get(bowler) + 1);
   }

   public void results(){
      System.out.println();
      for(int i=0;i<11;i++){
         System.out.println(playerRuns.get(i) + " -> " + ballsPlayed.get(i));
      }
   }

}
