package com.cricket.cricketspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerStats")
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStats {
   @Id
   private String _id;
   private String playerId;
   private String scoreboardId;
   private String playerName;
   private int runsScored;
   private int ballsPlayed;
   private int runsGiven;
   private int ballsThrown;
   private int wicketsTaken;

   public String get_id() {
      return _id;
   }

   public void set_id(String _id) {
      this._id = _id;
   }
   public String getPlayerName() {
      return playerName;
   }

   public void setPlayerName(String playerName) {
      this.playerName = playerName;
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   public String getScoreboardId() {
      return scoreboardId;
   }

   public void setScoreboardId(String scoreboardId) {
      this.scoreboardId = scoreboardId;
   }

   public int getRunsScored() {
      return runsScored;
   }

   public void setRunsScored(int runsScored) {
      this.runsScored = runsScored;
   }

   public int getBallsPlayed() {
      return ballsPlayed;
   }

   public void setBallsPlayed(int ballsPlayed) {
      this.ballsPlayed = ballsPlayed;
   }

   public int getRunsGiven() {
      return runsGiven;
   }

   public void setRunsGiven(int runsGiven) {
      this.runsGiven = runsGiven;
   }

   public int getBallsThrown() {
      return ballsThrown;
   }

   public void setBallsThrown(int ballsThrown) {
      this.ballsThrown = ballsThrown;
   }

   public int getWicketsTaken() {
      return wicketsTaken;
   }

   public void setWicketsTaken(int wicketsTaken) {
      this.wicketsTaken = wicketsTaken;
   }
}
