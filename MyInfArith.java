import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java MyInfArith <int/float> <add/sub/mul/div> <num1> <num2>");
            return;
        }
        String type = args[0];
        String op = args[1];
        String num1 = args[2];
        String num2 = args[3];
        if (type.equals("int")) {
            AInteger a = new AInteger(num1);
            AInteger b = new AInteger(num2);
            switch (op) {
                case "add": System.out.println(a.add(b)); break;
                case "sub": System.out.println(a.sub(b)); break;
                case "mul": System.out.println(a.mul(b)); break;
                case "div": System.out.println(a.div(b)); break;
                default: System.out.println("Unknown operation."); break;
            }
        } else if (type.equals("float")) {
            AFloat a = new AFloat(num1);
            AFloat b = new AFloat(num2);
            switch (op) {
                case "add": System.out.println(a.add(b)); break;
                case "sub": System.out.println(a.sub(b)); break;
                case "mul": System.out.println(a.mul(b)); break;
                case "div": System.out.println(a.div(b)); break;
                default: System.out.println("Unknown operation."); break;
            }
        } else {
            System.out.println("Invalid type. Must be 'int' or 'float'.");
        }
    }
}
