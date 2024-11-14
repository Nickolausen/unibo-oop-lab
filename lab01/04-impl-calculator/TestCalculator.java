class TestCalculator {
  public static void main(String[] args) {
	  /*
	   * Uncomment the code below once Calculator has been created!
	   */
	  
    Calculator calc = new Calculator();
    System.out.println("1 + 2 =" + calc.add(1, 2));
    System.out.println("-1 - 2 =" + calc.sub(-1, 2));
    System.out.println("6 * 3 =" + calc.mul(6, 3));
    System.out.println("8 / 4 =" + calc.div(8, 4));
    

    double somma = calc.add(2, 3);
    somma += 5;

    System.out.println(somma);
    System.out.println(calc.lastRes);
  }
}
