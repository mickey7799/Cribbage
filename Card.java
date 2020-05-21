/** This class provides methods to get the Cribbage rank and suit of a card.
 * It also provides two Comparator classes used for sorting cards and card combinations  
 */

import java.util.Comparator;

public class Card {
    /** A class CribbageRank used to represent a card. */
    private CribbageRank rank;
    
    /** A single character used to represent the suit of a card. */
    private char suit;
    
    /** Construct a card. 
     * @param abbrev the single-character abbreviation for this card
     * @param suit the single-character suit for this card
     */    
    public Card(char abbrev, char suit){        
        for (CribbageRank r : CribbageRank.values()){
            if(r.abbrev()== abbrev){
                this.rank= r;
            }
        }
        this.suit=suit;
    }
    
    /** @return the CribbageRank of a card */
    public CribbageRank getRank(){
        return this.rank;    
    }
    
    /** @return the suit of a card */
    public char getSuit(){
        return this.suit;  
    }             
}

/** This class provides a single method to compare the ordinal of cards
 * which is used for sorting cards.
 */
class CardComparator implements Comparator<Card>{
    @Override
    
    /** @param c1 one of two cards which compared with their ranks 
      * @param c2 another one of two cards which compared with their ranks 
      * @return  1 if c1' s rank bigger than c2' s rank
      * @return -1 if c1' s rank smaller than c2' s rank
      * @return  0 if c1' s rank equals to c2' s rank
      */
    public int compare(Card c1, Card c2) {   
        if(c1.getRank().ordinal() > c2.getRank().ordinal() ){ 
            return 1;
        }else if(c1.getRank().ordinal() < c2.getRank().ordinal()){
            return -1;
        }else{
            return 0;
        }
    }
}

/** This class provides a single method to compare the length of arrays representing card combinations
 * which is used for sorting card-combination arrays.
 */
class ComLengthComparator implements Comparator<Card[]>{
    @Override
    
    /** @param c1 one of two combinations which compared with their length of cards
      * @param c2 another one of two combinations which compared with their length of cards 
      * @return  1 if c1' s length of cards bigger than c2' s length of cards
      * @return -1 if c1' s length of cards smaller than c2' s length of cards
      * @return  0 if c1' s length of cards equals to c2' s length of cards
      */
    public int compare(Card[] c1, Card[] c2){    
        if(c1.length > c2.length ){ 
            return -1;
        }else if(c1.length < c2.length){
            return 1;
        }else{
            return 0;
        }
    }
}