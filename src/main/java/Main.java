import bool.expression.Expression;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter a first Expression: ");
        Expression expression1 = new Expression(reader.nextLine());
        System.out.println(expression1.getExpression());

        System.out.println("Enter a second Expression: ");
        Expression expression2 = new Expression(reader.nextLine());
        System.out.println(expression2.getExpression());

        reader.close();

        if (expression1.isEquivalentWith(expression2)) {
            System.out.println("The two expressions are equivalent");
        } else {
            System.out.println("The two expressions are not equivalent");
        }
    }
}
