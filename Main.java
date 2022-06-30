import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main will be in control of the UI for the user, directing them to various methods in the class that
 * will call upon the other classes as needed
 * @author Sebastian Majsa
 * CS 160L
 * @date 29 June 2022
 */
public class Main {
    static double finalDouble;
    static File inventory;
    /**
     *The first method to be executed upon the start of the program, will call other methods as needed
     * @name main
     */
    static int[] inv;
    public static void main(String[] args) throws IOException {
        inv = new int[]{5, 0, 2, 2, 4, 2};
        inventoryWriter(inv);
        ArrayList<String> Item = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();
        ArrayList<String> Temp2 = new ArrayList<>();
        Scanner CafeApplication = new Scanner(System.in);
        Stack<String> stack = new Stack<String>();
        System.out.println("Cafe Application Running:...");

        int input = 0;
        while(input != 1)
        {
            System.out.println("Press 0: Add to Inventory");
            System.out.println("Press 1 : Read Inventory");
            System.out.println("Press 2 : Create Coffee Order");
            System.out.println("Press 3 : Update Inventory");
            System.out.println("Press 4 : Update log file");
            System.out.println("Press 5 : Exit the application");

            switch (CafeApplication.nextLine())
            {
                case "0":
                    addInventory();
                    break;

                case "1":
                    int[] in = inventoryReader();
                    System.out.println("Current items in the inventory: ");
                    for(int i = 0; i < in.length; i++)
                    {
                        if(i == 0)
                        {
                            System.out.println("Black Coffee = " + in[i]);
                        }
                        else if(i == 1)
                        {
                            System.out.println("Milk = " + in[i]);
                        }
                        else if(i == 2)
                        {
                            System.out.println("HotWater = " + in[i]);
                        }
                        else if(i == 3)
                        {
                            System.out.println("Espresso = " + in[i]);
                        }
                        else if(i == 4)
                        {
                            System.out.println("Sugar = " + in[i]);
                        }
                        else if(i == 5)
                        {
                            System.out.println("WhippedCream = " + in[i]);
                        }
                    }
                    break;
                case "2":
                    int caffe = inv[0];
                    if(caffe == 0)
                    {
                        System.out.println("Out of Coffee. Visit us later.");
                        break;
                    }
                    System.out.println("Coffee order created. Select toppings for the first coffee:");
                    String line = "yes" ;

                    do
                    {
                        int i = 0;
                        Temp2.add(CreateOrder().get(i));
                        price.add(finalDouble);
                        Item.add(Temp2.get(i));
                        i++;
                        caffe--;
                        if(caffe == 0)
                        {
                            System.out.println("Order Completed. No more coffees.");
                            break;
                        }
                        System.out.println("Do you want to add another coffee to this order? - yes or no");
                    } while (!(line = CafeApplication.nextLine()).equals("no"));
                    stack.push(PrintOrder(Item, price));
                    try
                    {
                        String s = stack.peek();
                        FileWriter file = new FileWriter("order.csv", true);
                        file.append(s);
                        System.out.println(s);
                    }
                    catch(FileNotFoundException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case "3":
                    inventoryWriter(inv);
                    System.out.println("Successfully updated the inventory");
                    break;
                case "4":
                    logWriter(stack);
                    break;
                case "5":
                    System.exit(0);
                default:
                    System.out.println("Invalid Selection. Please Try Again");
            }
        }
    }
    /**
     * Print the receipt using the item arraylist parameter as well as the price parameter
     * Associated, they create a list of both price and item name that will display on the receipt
     * @name PrintOrder
     * @param Item
     * @param price
     */
    public static String PrintOrder(ArrayList<String> Item, ArrayList<Double> price){
        StringBuilder str = new StringBuilder();
        str.append("RECEIPT\n");
        double total = 0;
        for (int i=1; i<=Item.size(); i++){
            str.append("Item "+i+": "+Item.get(i-1)+" | cost:"+price.get(i-1)+"\n");
            total = total + price.get(i-1);
        }
        str.append("TOTAL COST OF ORDER:");
        str.append(String.format("%.2f", total));
        str.append("\n");
        return str.toString();
    }

    /**
     * Creates a coffee object, according to user input various toppings objects are created
     * Ultimately these toppings together create a single coffee for the customer
     * @name CreateOrder
     */
    public static ArrayList<String> CreateOrder()
    {
        Scanner userFeedback = new Scanner(System.in);
        ArrayList<String> coffeeOrder = new ArrayList<String>();

        Coffee basicCoffee = new BasicCoffee();
        int in = 0;

        while(in != 1)
        {
            System.out.println("Enter the following values to add toppings: 1.) milk, 2.) hot water), 3.) espresso, 4.) sugar, 5.) whipped cream, e - to complete order");
            switch(userFeedback.nextLine())
            {
                case "1":
                    if(inv[1] != 0)
                    {
                        basicCoffee = new Milk(basicCoffee);
                        inv[1] = inv[1] - 1;
                    }
                    else {
                        System.out.println("Out of Milk, Try a different topping.");
                    }
                    break;
                case "2":
                    if(inv[2] != 0)
                    {
                        basicCoffee = new HotWater(basicCoffee);
                        inv[2] = inv[2] - 1;
                    }
                    else {
                        System.out.println("Out of Hot Water, Try a different topping.");
                    }
                    break;
                case "3":
                    if(inv[3] != 0)
                    {
                        basicCoffee = new Espresso(basicCoffee);
                        inv[3] = inv[3] - 1;
                    }
                    else {
                        System.out.println("Out of Espresso, Try a different topping.");
                    }
                    break;
                case "4":
                    if(inv[4] != 0)
                    {
                        basicCoffee = new Sugar(basicCoffee);
                        inv[4] = inv[4] - 1;
                    }
                    else {
                        System.out.println("Out of Sugar, Try a different topping.");
                    }
                    break;
                case "5":
                    if(inv[5] != 0)
                    {
                        basicCoffee = new WhippedCream(basicCoffee);
                        inv[5] = inv[5] - 1;
                    }
                    else {
                        System.out.println("Out of Whipped Cream, Try a different topping.");
                    }
                    break;
                case "e":
                    in = 1;
                    break;
            }
        }
        coffeeOrder.add(basicCoffee.printCoffee());
        getDouble(basicCoffee);
        return coffeeOrder;
    }

    /**
     * Simple method to return the private arraylist that holds the prices of the various coffees
     * @name getDouble
     * @param c
     */
    public static void getDouble(Coffee c)
    {
        finalDouble = c.cost();
    }

    /**
     * Read the inventory and return the number of each inventory stock
     * @name inventoryReader
     * @exception FileNotFoundException
     */
    public static int[] inventoryReader()
    {
        String sentence = "";
        int[] nums = new int[6];
        try
        {
            int i = 0;
            Scanner reader = new Scanner(inventory);
            while(reader.hasNextLine())
            {
                sentence = reader.nextLine();
                nums[i] = Integer.parseInt(sentence.substring(sentence.indexOf("=") + 2));
                i++;
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        return nums;
    }

    /**
     * Writes out the inventory to inventory.txt
     * @name inventoryWriter
     * @param inv
     */
    public static void inventoryWriter(int[] inv)
    {
        try{
            inventory = new File("inventory.txt");
            inventory.createNewFile();
            PrintWriter p = new PrintWriter(inventory);
            p.println("Black Coffee = " + inv[0]);
            p.println("Milk = " + inv[1]);
            p.println("HotWater = " + inv[2]);
            p.println("Espresso = " + inv[3]);
            p.println("Sugar = " + inv[4]);
            p.println("WhippedCream = " + inv[5]);
            p.flush();
        }
        catch(IOException e)
        {
            System.out.println("Exception found with Inventory");
        }
    }

    /**
     * Writes to LogFile.txt with information on the code
     * @name logWriter
     * @param s
     */
    public static void logWriter(Stack<String> s)
    {
        try{
            FileWriter myWriter = new FileWriter("LogFile.txt", true);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            myWriter.write("\n\nWriting orders from stack " + formatter.format(date) + "\n");
            if(s.isEmpty() == true)
            {
                System.out.println("Nothing to log. Stack is empty");
            }
            while(s.isEmpty() == false)
            {
                myWriter.append(s.pop());
            }
            System.out.println("Successfully updated the log file");
            myWriter.flush();
            myWriter.close();
        }
        catch(IOException e)
        {
            System.out.println("Exception involving the logfile");
        }
    }
    /**
     * A function to add inventory to the array
     * @name addInventory
     */
    public static void addInventory()
    {
        Scanner scnr = new Scanner(System.in);
        boolean escape = false;

        System.out.println("Press 1 : Add Coffee");
        System.out.println("Press 2 : Add Milk");
        System.out.println("Press 3 : Add Hot Water");
        System.out.println("Press 4 : Add Espresso");
        System.out.println("Press 5 : Add WhippedCream");
        System.out.println("Press e : Escape to Main Menu");

        while(!escape)
        {
            switch(scnr.nextLine())
            {
                case "1":
                    inv[0] = inv[0] + 1;
                    System.out.println("Coffee added");
                    break;
                case "2":
                    inv[1] = inv[1] + 1;
                    System.out.println("Milk added");
                    break;
                case "3":
                    inv[2] = inv[2] + 1;
                    System.out.println("Hot Water added");
                    break;
                case "4":
                    inv[3] = inv[3] + 1;
                    System.out.println("Espresso added");
                    break;
                case "5":
                    inv[4] = inv[4] + 1;
                    System.out.println("Whipped Cream added");
                    break;
                case "6":
                    inv[5] = inv[5] + 1;
                    break;
                case "e":
                    escape = true;
                    break;
                default:
                    System.out.println("Not a Menu Item, Try Again");
            }
        }
        inventoryWriter(inv);
    }
}
