import bool.expression.Expression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    public void mainTest1() {
        Expression expression1 = new Expression("a or b");
        Expression expression2 = new Expression("!A(A + B) + (B+AA)(A+!B)");

        assertTrue(expression1.isEquivalentWith(expression2));
    }

    @Test
    public void mainTest2() {
        Expression expression1 = new Expression("!ABC + A!B!C + A!BC + AB!C + ABC");
        Expression expression2 = new Expression("A + BC");

        assertTrue(expression1.isEquivalentWith(expression2));
    }

    @Test
    public void mainTest3() {
        Expression expression1 = new Expression("AD + AC");
        Expression expression2 = new Expression("A!B!CD + A!BC!D + A!BCD + AB!CD + ABC!D + ABCD");

        assertTrue(expression1.isEquivalentWith(expression2));
    }

    @Test
    public void mainTest4() {
        Expression expression1 = new Expression("B!D + !A!B!C!D + !AB!CD + ABCD + A!BC!D");
        Expression expression2 = new Expression("!A!B!C!D+!AB!C!D+!AB!CD+!ABC!D+A!BC!D+AB!C!D+ABC!D+ABCD");

        assertTrue(expression1.isEquivalentWith(expression2));
    }

    @Test
    public void mainTest5() {
        Expression expression1 = new Expression("not a");
        Expression expression2 = new Expression("!(AB)(!A + B)(!B + B)");

        assertTrue(expression1.isEquivalentWith(expression2));
    }

    @Test
    public void mainTest6() {
        Expression expression1 = new Expression("!A(A + B) + (B + AA)(A + !B)");
        Expression expression2 = new Expression("a or b");

        assertTrue(expression1.isEquivalentWith(expression2));
    }
}