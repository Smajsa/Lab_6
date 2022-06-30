/**
 *A coffee with no added toppings, would call works with the extended class to create the most
 * basic coffee that can be purchased
 * @author Sebastian Majsa
 */
public class BlackCoffee extends CoffeeDecorator {
    public BlackCoffee(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void addTopping(Coffee coffee) {
        coffee.addTopping(this.coffee);
        this.coffee = coffee;
    }

    @Override
    public String printCoffee() {
        return this.coffee.printCoffee();
    }

    @Override
    public Double cost() {
        return this.coffee.cost();
    }
}
