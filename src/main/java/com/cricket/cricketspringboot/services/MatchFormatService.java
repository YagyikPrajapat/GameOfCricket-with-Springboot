package com.cricket.cricketspringboot.services;

import com.cricket.cricketspringboot.controller.T20Controller;
import com.cricket.cricketspringboot.enums.Format;
import com.cricket.cricketspringboot.interfaces.CricketFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchFormatService {
   @Autowired
   private CricketFormat cricketFormat;
   public String matchFormat(String matchFormat){
      cricketFormat  = matchFormatChoice(matchFormat);
      return cricketFormat.matchInitiate();
   }

   private CricketFormat matchFormatChoice(String matchFormat){
      Format format = Format.valueOf(matchFormat);
      switch(format){
         case T20:
            return new T20Controller();
         case ODI:
            System.out.println("ODI");
         case CUSTOM:
            System.out.println("CUSTOM");
         default:
            throw new IllegalArgumentException("Unknown enums.Format");
      }
   }
}
