/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamebackend;

import java.util.Arrays;

/**
 *
 * @author David
 */
public class Card {
    int[] props = new int[4];
    
    public Card(int a, int b, int c, int d){
        props[0] = a;
        props[1] = b;
        props[2] = c;
        props[3] = d;
    }
    
    public Card(int num){
        props[0] = (num/27)%3;
        props[1] = (num/9)%3;
        props[2] = (num/3)%3;
        props[3] = num%3;
    }
    
    public int GetPropertyValue(int pnum){
        return props[pnum];
    }
    
    @Override
    public boolean equals(Object oth){
        Card other = (Card) oth;
        return (props[0]==other.props[0])
                && (props[1]==other.props[1])
                && (props[2]==other.props[2])
                && (props[3]==other.props[3]);
    }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + Arrays.hashCode(this.props);
    return hash;
  }
    
    public int GetSingleDigit(){
        return props[0]*27+props[1]*9+props[2]*3+props[3];
    }
    
    @Override
    public String toString(){
      return props[0] + " " + props[1] + " " + props[2] + " " + props[3];      
    }
}
