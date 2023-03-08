package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.enums.playerType;
import com.cricket.cricketspringboot.model.Player;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.TeamRepository;
import com.cricket.cricketspringboot.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class PlayerController {
   @Autowired
   private PlayerService playerService;
   @Autowired
   private TeamRepository teamRepository;

   @GetMapping("add_player")
   public ModelAndView getAddPlayer(){
      ModelAndView mav = new ModelAndView("player");
      Player player = new Player();
      mav.addObject("player", player);
      mav.addObject("team", new Team());
      return mav;
   }
   @PostMapping("add_player")
   public RedirectView addPlayer(@ModelAttribute Player player, @ModelAttribute Team team){
      RedirectView redirectView = new RedirectView("add_player");
      player.setTeamId(teamRepository.findByTeamName(team.getTeamName()).get_id());
      playerService.addPlayer(player);
      return redirectView;
   }

   @GetMapping("get_player")
   public Player getPlayerById(String id){
      return playerService.getPlayerById(id);
   }

   @GetMapping("get_player_by_name")
   public Player getPlayerByName(@RequestParam String playerName){
      return playerService.getPlayerByName(playerName);
   }

   @DeleteMapping("delete_player")
   public String deletePlayerByName(@RequestParam String playerName){
      return playerService.deletePlayerByName(playerName);
   }

   @PutMapping("update_player")
   public String updatePlayer(@RequestParam String name, @RequestParam playerType playerType){
      return playerService.updatePlayer(name, playerType);
   }
   @GetMapping("get_players")
   public List<Player> getPlayers(String teamId){
      return playerService.getPlayers(teamId);
   }
}
