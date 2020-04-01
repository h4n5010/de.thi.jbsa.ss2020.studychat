package de.thi.jbsa.prototype.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log
public class MessageService {

  public List<String> history = new ArrayList<>();

  @Getter
  private String message ;

  public void setMessage(String input){
    this.message += input + ",";
  }
}
