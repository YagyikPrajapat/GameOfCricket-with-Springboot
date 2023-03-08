package com.cricket.cricketspringboot.controller;

import com.cricket.cricketspringboot.interfaces.CricketFormat;
import com.cricket.cricketspringboot.model.Form;
import com.cricket.cricketspringboot.model.PlayerStats;
import com.cricket.cricketspringboot.model.Scoreboard;
import com.cricket.cricketspringboot.model.Team;
import com.cricket.cricketspringboot.repository.PlayerStatsRepository;
import com.cricket.cricketspringboot.repository.ScoreboardRepository;
import com.cricket.cricketspringboot.repository.TeamRepository;
import com.cricket.cricketspringboot.services.T20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController("/t20")
public class T20Controller implements CricketFormat {
   @Autowired
   private T20Service t20Service;
   @Autowired
   private ScoreboardRepository scoreboardRepository;
   @Autowired
   private TeamRepository teamRepository;
   @Autowired
   private PlayerStatsRepository playerStatsRepository;
   @GetMapping("setmatchdetails")
   public ModelAndView setMatchDetails(){
      ModelAndView mav = new ModelAndView("matchDetails");
      Form form = new Form();
      mav.addObject("form", form);
      return mav;
   }

   @PostMapping("teams")
   public ModelAndView teams(@ModelAttribute Form form){
      t20Service.setMatchDetails(form.getTeam1(), form.getTeam2());
      ModelAndView mav = new ModelAndView("toss");
      return mav;
   }

   @GetMapping("/toss")
   public ModelAndView toss(){
      ModelAndView mav = new ModelAndView("toss");
      Form form = new Form();
      mav.addObject("form", form);
      return mav;
   }

   @PostMapping("/toss1")
   public RedirectView toss(@ModelAttribute Form form){
      RedirectView redirectView = new RedirectView();
      redirectView.setUrl("t20service");
      t20Service.tossResult(form.getTossChoice());
      return redirectView;
   }
   @Override
   @PostMapping("t20")
   public String matchInitiate() {
      return t20Service.matchStarts();
   }

   @GetMapping("t20service")
   public RedirectView matchInitiates() {
      RedirectView redirectView = new RedirectView();
      redirectView.setUrl("scorecard");
      t20Service.matchStarts();
      return redirectView;
   }

   @GetMapping("/scorecard")
   public ModelAndView scoreboard(){
      int size = scoreboardRepository.findAll().size();
      Scoreboard scoreboard = scoreboardRepository.findAll().get(size-1);
      Team firstInningteam = teamRepository.findById(scoreboard.getFirstInningTeamId()).get();
      Team secondInningteam = teamRepository.findById(scoreboard.getSecondInningTeamId()).get();
      List<PlayerStats> playerStatsList = new ArrayList<>();
      for(String id : scoreboard.getPlayerStats()){
         playerStatsList.add(playerStatsRepository.findById(id).get());
      }
      ModelAndView mav = new ModelAndView("scoreboard");
      mav.addObject("scoreboard", scoreboard);
      mav.addObject("firstInningTeam", firstInningteam);
      mav.addObject("secondInningTeam", secondInningteam);
      mav.addObject("playerStats", playerStatsList);
      return mav;
   }
}
