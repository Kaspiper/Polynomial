import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Testing
final class PoynomialTesting {
    //Testing
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        PolynomialM[] expression = new PolynomialM[3];
        expression[0] = new PolynomialM();
        expression[1] = new PolynomialM();
        expression[2] = new PolynomialM();

        String choice = "";
        int operand = 0;
        int constant = 0;
        int power = 0;

        try {
            do {
                System.out.print("First or Second Expression (1/2):");
                try {
                    operand = Integer.valueOf(in.readLine()) - 1;
                } catch (NumberFormatException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                System.out.println("1: Add Term");
                System.out.println("2: Remove Term");
                System.out.println("3: Degree");
                System.out.println("4: Add");
                System.out.println("5: Subtract");
                System.out.println("6: Multiply");
                System.out.println("7: Divide");
                System.out.println("8: CLEAR");

                try {
                    choice = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (choice) {
                    case "1":
                        System.out.print("Enter Constant: ");
                        try {
                            constant = Integer.valueOf(in.readLine());
                        } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        System.out.print("Enter Power: ");
                        try {
                            power = Integer.valueOf(in.readLine());
                        } catch (NumberFormatException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        expression[operand].addTerm(constant, power);
                        break;

                    case "2":
                        System.out.print("Enter Power: ");
                        power = Integer.valueOf(in.readLine());
                        System.out.print("Removed Term: "
                                + expression[operand].removeTerm(power));
                        System.out.println("x^" + power);
                        break;

                    case "3":
                        System.out.println(
                                "Degree: " + expression[operand].degree());
                        break;

                    case "4":
                        expression[2].clear();
                        expression[2].add(expression[0]);
                        expression[2].add(expression[1]);
                        break;

                    case "5":
                        expression[2].clear();
                        expression[2].add(expression[operand]);
                        if (operand == 0) {
                            expression[2].subtract(expression[1]);
                        } else {
                            expression[2].subtract(expression[0]);
                        }

                    case "6":
                        expression[2].clear();
                        expression[2].add(expression[0]);
                        expression[2].multiply(expression[1]);
                        break;

                    // case "7":
                    //     expression[2].clear();

                    //     if (operand == 0) {
                    //         expression[2] = (PolynomialM) expression[0]
                    //                 .divide(expression[1]);
                    //     } else {
                    //         expression[2] = (PolynomialM) expression[1]
                    //                 .divide(expression[0]);
                    //     }
                    case "8":
                        if (operand == 0) {
                            expression[0].clear();
                        } else {
                            expression[1].clear();
                        }

                    default:

                        System.out.println("Choice Not Recognized.");

                        break;

                }
                System.out.println("[CURRENT STATE]: ");

                System.out.println("Expression 1: " + expression[0].toString());

                System.out.println("Expression 2: " + expression[1].toString());

                System.out.println("RESULT      : " + expression[2].toString());

                System.out.print("MORE OPERATIONS? (Y / N): ");

            } while (in.readLine().equals("Y"));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
