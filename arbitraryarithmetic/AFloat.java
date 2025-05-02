package arbitraryarithmetic;

public class AFloat {
    String value;

    public AFloat() {
        value = "0.0";
    }
    public AFloat(String s) {
        value = s.trim();
    }
    public AFloat(AFloat other) {
        value = other.value;
    }
    public static AFloat parse(String s) {
        return new AFloat(s);
    }
    private String[] normalize(String a, String b) {
        String[] aSplit = a.split("\\.");
        String[] bSplit = b.split("\\.");
        String aWhole = aSplit[0];
        String bWhole = bSplit[0];
        String aFrac = aSplit.length > 1 ? aSplit[1] : "";
        String bFrac = bSplit.length > 1 ? bSplit[1] : "";
        int maxLen = Math.max(aFrac.length(), bFrac.length());
        while (aFrac.length() < maxLen) aFrac += "0";
        while (bFrac.length() < maxLen) bFrac += "0";
        return new String[] {
            aWhole + aFrac,
            bWhole + bFrac,
            String.valueOf(maxLen)
        };
    }
    public AFloat add(AFloat other) {
        String[] parts = normalize(this.value, other.value);
        String x = parts[0], y = parts[1];
        int dec = Integer.parseInt(parts[2]);
        AInteger a = new AInteger(x);
        AInteger b = new AInteger(y);
        String sum = a.add(b).toString();
        if (sum.length() <= dec)
            sum = "0".repeat(dec + 1 - sum.length()) + sum;
        String intPart = sum.substring(0, sum.length() - dec);
        String fracPart = sum.substring(sum.length() - dec);
        return new AFloat(intPart + "." + fracPart);
    }
    public AFloat sub(AFloat other) {
        String[] parts = normalize(this.value, other.value);
        String x = parts[0], y = parts[1];
        int dec = Integer.parseInt(parts[2]);
        AInteger a = new AInteger(x);
        AInteger b = new AInteger(y);
        String diff = a.sub(b).toString();
        boolean isNeg = diff.startsWith("-");
        if (isNeg) diff = diff.substring(1);
        if (diff.length() <= dec)
            diff = "0".repeat(dec + 1 - diff.length()) + diff;
        String intPart = diff.substring(0, diff.length() - dec);
        String fracPart = diff.substring(diff.length() - dec);
        if (isNeg) return new AFloat("-" + intPart + "." + fracPart);
        else return new AFloat(intPart + "." + fracPart);
    }
    public AFloat mul(AFloat other) {
        String[] parts = normalize(this.value, other.value);
        String x = parts[0], y = parts[1];
        int dec = Integer.parseInt(parts[2]);
        AInteger a = new AInteger(x);
        AInteger b = new AInteger(y);
        String prod = a.mul(b).toString();
        int totalDec = dec * 2;
        if (prod.length() <= totalDec)
            prod = "0".repeat(totalDec + 1 - prod.length()) + prod;
        String intPart = prod.substring(0, prod.length() - totalDec);
        String fracPart = prod.substring(prod.length() - totalDec);
        return new AFloat(intPart + "." + fracPart);
    }
    public AFloat div(AFloat other) {
        String[] parts = normalize(this.value, other.value);
        String x = parts[0], y = parts[1];
        int dec = Integer.parseInt(parts[2]);
        AInteger a = new AInteger(x + "0".repeat(30));  // add extra zeros for precision
        AInteger b = new AInteger(y);
        if (b.toString().equals("0")) {
            System.out.println("Division by zero error");
            return new AFloat("0.0");
        }
        String result = a.div(b).toString();
        if (result.length() <= 30)
            result = "0".repeat(31 - result.length()) + result;
        String intPart = result.substring(0, result.length() - 30);
        String fracPart = result.substring(result.length() - 30);
        return new AFloat(intPart + "." + fracPart);
    }
    public String toString() {
        return value;
    }
}
