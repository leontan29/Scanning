package com.craftinginterpreters.lox;

class Test {

   public static void main(String args[]) {
      Double x = new Double(0.1);
      Object y = new Double(0.2);
      Object z = x;

      System.out.println(x);
      System.out.println(y);
      System.out.println(z);
      System.out.println(y instanceof Double);
      System.out.println(y instanceof Integer);
     
   }
}
