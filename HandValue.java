/** This class  is designed to calculate hand value for cribbage.  
 *  It provides methods to compute hand value accoring to the following rules: 
  - 15s: 2 points are scored for each distinct combinations of cards that add to 15.
  - Pairs: 2 points are scored for each pair. 
  - Runs: 1 point is scored for each card in a run of 3 or more consecutive cards.
  - Flushes: 4 points is scored if all the cards in the hand are of the same suit.  Plus 1 if the start card is the same suit.
  - One for his nob: 1 point is scored if the hand contains the jack of the same suit as the start card.

  It also provides a main method which receives 5 cards from the command line argument 
  with the format of two-character strings defined as the following:
  - First char- an upper-case A for Ace, K for King, Q for Queen, J for Jack, T for Ten, or digit(2-9) 
  - Second char- C for Clubs, D for Diamonds, H for Hearts, S for Spades
  and will compute the value of a hand and print out the value. 
 */

import java.util.Comparator;
import java.util.Arrays;

public class HandValue {
    /** Int used to store hand value. */
    private int handValue = 0;
    
    /** Array of class Card used to store cards. */
    private Card [] cards;
    
    /** Matrix of class Card used to store card comninations. */
    private Card[][] combinations;
    
    /** Index of start card in array cards. */
    private final int startCardIndex = 4;
    
    /** The point of hand value for the rule of 15s. */
    private final int cardsValueAddTo15 = 15;
    
    /** The hand value gained for satisfying the rule of 15s. */
    private final int handValueGain_15 = 2;
    
    /** The hand value gained for satisfying the rule of pair. */
    private final int handValueGain_pair = 2;
    
    /** The hand value gained for satisfying the rule of flush. */
    private final int handValueGain_flush = 4;
    
    /** The hand value gained for satisfying the rule of flush with the start card fulfilled. */
    private final int handValueGain_flushStartCard = 1;
    
    /** The hand value gained for satisfying the rule of "One For His Nob". */
    private final int handValueGain_oneForHisNob = 1; 
    
    /** Length of a min flush. */
    private final int minflushLength = 4;
    
    /** Length of a pair. */
    private final int pairLength = 2;
    
    /** Length of a min run. */
    private final int minRunsLength = 3;
    
    /** Construct HandValue.
     * @param cards: array of class Card
     * @param combinations: subarrays of the input array cards
     */
    public HandValue(Card[] cards){
        this.cards = cards;
        combinations = Combinations.combinations(cards);
    }
    
    /** This method return handValue before calling computeHandValue()
     * @return handValue
     */  
    public int getHandValue(){
        computeHandValue();
        return handValue;
    }
    
    /** This method computes hand value totally. */    
    private void computeHandValue(){
        computeNob();
        computeFlushes();
        computeFifteen();
        computePairs();
        computeRuns();
    }
    
    /** This method computes hand value for the rule "One for His Nob". */
    private void computeNob(){      
        for (int i = 0; i < startCardIndex ; i++){
            if(cards[i].getRank().abbrev() == 'J'){
                if(cards[i].getSuit()==cards[startCardIndex].getSuit()) // If the suit of the start card equals the Jack's.
                    this.addHandValue(handValueGain_oneForHisNob);
            }
        }            
    }
    
    /** This method computes hand value for flushes. */
    private void computeFlushes(){
        char suit = cards[0].getSuit();
        int same_suit_count = 1; 
        for (int i = 1; i < startCardIndex; i++){
            if(cards[i].getSuit() == suit)
                same_suit_count++; 
        }        
        if(same_suit_count == minflushLength ){ // All cards in the hand are of the same suit
            this.addHandValue(handValueGain_flush);           
            if(cards[startCardIndex].getSuit() == suit){ // The start card has the same suit as the rest of cards
                this.addHandValue(handValueGain_flushStartCard);
            }
        }        
    }
    
    /** This method computes hand value for the rule of 15s. */
    private void computeFifteen(){
        for (Card[] cards : combinations) { 
            int sum = 0;
            for (Card card : cards) {
                sum = sum + card.getRank().faceValue();                              
            }
            if(sum==cardsValueAddTo15){ 
                this.addHandValue(handValueGain_15);
            }            
        }
    }
    
    /** This method computes hand value for the rule of pairs. */
    private void computePairs(){
        for (Card[] cards : combinations) {
            if(cards.length == pairLength){ //Only focus on paris
                if(cards[0].getRank().abbrev() == cards[1].getRank().abbrev()){ // Two cards have the same abbreviation.
                    this.addHandValue(handValueGain_pair);
                }
            }
        }
    }
    
    /** This method computes hand value for the rule of Runs. */
    private void computeRuns(){
        Arrays.sort(combinations, new ComLengthComparator());//Sort arrays descendingly according to the length
        // Record the current max length of runs 
        // to avoid adding duplicated hand values for runs of length 3, 4, 5 with same combination.
        int currentMaxRun = 0 ;   
        for (Card[] cards : combinations) {            
            if(cards.length >= minRunsLength && cards.length >= currentMaxRun  ){
                Arrays.sort(cards, new CardComparator()); //Sort the cards ascendingly according to their ordinal
                if(areCardsRun(cards) == true){ // This combination of cards is a run
                    currentMaxRun = cards.length;
                    this.addHandValue(cards.length);
                }
            }
        }
    }
    
    /** This method checks whether a combination of cards is a run. 
     * @param cards: array of class Card
     * @return true if it is a run
     */  
    private boolean areCardsRun(Card [] cards){
        boolean areRun = true;
        //Check whether a combination of cards is consecutive             
        for(int slowIndex =0, fastIndex =1 ; fastIndex < cards.length; slowIndex++, fastIndex++){
            if(cards[slowIndex].getRank().ordinal()+1 !=cards[fastIndex].getRank().ordinal() ){
                areRun = false;   
            }
        }                       
        return areRun;
    }
  
    /** This method adds the hand value gained to variable handValue.
     * @param handValueGain 
     */
    private void addHandValue(int handValueGain){
        this.handValue += handValueGain;
    }
    
    /** This main method takes in command line arguments 
     * and creates array for those five cards used to compute and print the hand value.
     * @param args 5 cards with the format of two-character strings
     */
    public static void main(String[] args) {
        final int handCardNumber = 5;
        Card [] cards = new Card [handCardNumber];
        //Store 5 cards from command line arguments in array cards
        for(int i = 0; i < handCardNumber; i++){
            cards[i] = new Card(args[i].charAt(0), args[i].charAt(1));
        }      
        HandValue handvalue = new HandValue(cards);
        System.out.println( handvalue.getHandValue() );        
    }
}
