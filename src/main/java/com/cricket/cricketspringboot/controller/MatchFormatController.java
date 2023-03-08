package com.cricket.cricketspringboot.controller;


import com.cricket.cricketspringboot.services.MatchFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MatchFormatController {
   @Autowired
   private MatchFormatService matchService;
   @GetMapping("/")
   public String matchFormat(@RequestParam String format){
      matchService.matchFormat(format);
      return "match";
   }

}
