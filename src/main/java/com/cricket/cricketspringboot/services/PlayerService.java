package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.enums.playerType;
import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerRepository;
import com.cricket.cricketspringboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {
   @Autowired
   private PlayerRepository playerRepository;

   public String addPlayer(Player player){
      player.set_id(UUID.randomUUID().toString());
      playerRepository.save(player);
      return "success";
   }

   public Player getPlayerById(String id){
      return playerRepository.findById(id).get();
   }

   public Player getPlayerByName(String playerName){
      return playerRepository.findPlayerByName(playerName);
   }

   public String deletePlayerByName(String playerName){
      Player player = playerRepository.findPlayerByName(playerName);
      playerRepository.delete(player);
      return "success";
   }

   public String updatePlayer(String playerName, playerType playerType){
      Player player = playerRepository.findPlayerByName(playerName);
      player.setPlayerType(playerType);
      playerRepository.save(player);
      return "success";
   }

   public List<Player> getPlayers(String teamId){
      return playerRepository.findByTeamId(teamId);
   }
}
