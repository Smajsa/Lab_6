/**
 *The interface that every topping implements
 * @author Sebastian Majsa
 */
public interface Coffee {

    void addTopping(Coffee coffee);
    String printCoffee();
    //Double cost = Double.valueOf(0);
    Double cost();
}
