package com.CC.Party;
import java.util.ArrayList;

public class Party {
 
 private String name;
 private ArrayList<String> members = new ArrayList<String>();
 
 public void Storage(String name) {
  this.name = name;
 }

 
 public void addMember(String name) {
  if(!this.members.contains(name)) {
   this.members.add(name);
  }
 }
 
}