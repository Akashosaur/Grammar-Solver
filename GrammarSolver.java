// Akash Arora
// CSE 143
// Stuart Reges
// 2/12/16
// Assignment 5: Grammar Solver
//
//
// This class stores and manipulates a grammar (passed in BNF notation). It allows
// the user to randomly generate elements of the grammar.
import java.util.*;

public class GrammarSolver {
   private SortedMap<String, String[]> productions = new TreeMap<String, String[]>();
   private Random r;
   
   // pre: passed a grammar in BNF as a list of Strings. List is nonempty, nonterminals have
   //      only one entry (throws IllegalArgumentException if the grammar is empty or
   //      if there are two or more entries in the grammar for the same nonterminal)
   // post: constructs a GrammarSolver object and stores the grammar
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      r = new Random();
      for (String sequence : grammar) {
         String[] result = sequence.split("::=");
         if (productions.containsKey(result[0])) {
            throw new IllegalArgumentException();
         }
         productions.put(result[0],result[1].split("[|]"));
      }
   }
   
   // pre: takes in a given String symbol
   // post: returns true if the given symbol is a nonterminal of
   //       the grammar; returns false otherwise.
   public boolean grammarContains(String symbol) {
      return (productions.containsKey(symbol));
   }
   
   // pre: grammar contains given nonterminal symbol and times >= 0 (throws
   //      IllegalArgument if either is otherwise)
   // post: uses the grammar to randomly generate the given number of occurrences
   //       of the given symbol and returns the result as an array of Strings.
   //       For any given nonterminal symbol, each of its rules is applied with
   //       equal probability.
   public String[] generate(String symbol, int times) {
      if (!productions.containsKey(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] result = new String[times];
      for (int i = 0; i < times; i++) {
         result[i] = helperWordConstruct(symbol).trim();
      }
      return result;
   }
   
   // post: returns a String representation of the various nonterminal
   //       symbols from the grammar as a sorted, comma-separated list
   //       enclosed in square brackets
   public String getSymbols() {
      return productions.keySet().toString();
   }
   
   // pre: takes in a given String nonterminal symbol
   // post: a helper method to the generate method. Goes through the grammar
   //       with random probability for nonterminal symbols
   //       and returns astring
   private String helperWordConstruct (String symbol) {
      if (!productions.containsKey(symbol)) {
         return symbol;
      }
      String result = "";
      if (productions.containsKey(symbol)) {
         String[] rulesSequence = productions.get(symbol);
         rulesSequence = rulesSequence[r.nextInt(rulesSequence.length)].split("[ \t]+");
         for (String rule : rulesSequence) {
            result += " " + helperWordConstruct(rule).trim();
         }
      }
      return result;
   }
}