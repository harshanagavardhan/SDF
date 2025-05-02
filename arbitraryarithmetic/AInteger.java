package arbitraryarithmetic;

public class AInteger {
    String value; //stores intiger value as a string

    public AInteger() {
        value = "0";
    }
    public AInteger(String s) {
        value = s.trim();
    }
    public AInteger(AInteger other) {
        value = other.value;
    }
    public static AInteger parse(String s) {
        return new AInteger(s);
    }
    public AInteger add(AInteger other) {
        String a = this.value;
        String b = other.value;
        boolean neg = false;
        if (a.charAt(0) == '-' && b.charAt(0) != '-') {
            return (new AInteger(b)).sub(new AInteger(a.substring(1)));
        }
        if (a.charAt(0) != '-' && b.charAt(0) == '-') {
            return this.sub(new AInteger(b.substring(1)));
        }
        if (a.charAt(0) == '-' && b.charAt(0) == '-') {
            neg = true;
            a = a.substring(1);
            b = b.substring(1);
        }
        StringBuilder res = new StringBuilder();
        int carry = 0, i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 || j >= 0 || carry > 0) {
            int x = i >= 0 ? a.charAt(i--) - '0' : 0;
            int y = j >= 0 ? b.charAt(j--) - '0' : 0;
            int sum = x + y + carry;
            res.insert(0, sum % 10);
            carry = sum / 10;
        }
        if (neg) res.insert(0, '-');
        return new AInteger(res.toString());
    }
    public AInteger sub(AInteger other) {
        String a = this.value;
        String b = other.value;
        boolean neg = false;
        if (b.charAt(0) == '-') return this.add(new AInteger(b.substring(1)));
        if (a.charAt(0) == '-') return new AInteger("-" + (new AInteger(a.substring(1))).add(other).value);
        if (compareAbs(a, b) < 0) {
            neg = true;
            String temp = a;
            a = b;
            b = temp;
        }
        StringBuilder res = new StringBuilder();
        int carry = 0, i = a.length() - 1, j = b.length() - 1;
        while (i >= 0) {
            int x = a.charAt(i) - '0';
            int y = j >= 0 ? b.charAt(j) - '0' : 0;
            x -= carry;
            if (x < y) {
                x += 10;
                carry = 1;
            } else {
                carry = 0;
            }
            res.insert(0, x - y);
            i--; j--;
        }
        while (res.length() > 1 && res.charAt(0) == '0') res.deleteCharAt(0);
        if (neg) res.insert(0, '-');
        return new AInteger(res.toString());
    }
    public AInteger mul(AInteger other) {
        String a = this.value, b = other.value;
        boolean neg = false;
        if (a.charAt(0) == '-') {
            neg = !neg;
            a = a.substring(1);
        }
        if (b.charAt(0) == '-') {
            neg = !neg;
            b = b.substring(1);
        }
        int[] res = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                int mul = (a.charAt(i) - '0') * (b.charAt(j) - '0');
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + res[p2];
                res[p2] = sum % 10;
                res[p1] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int num : res) {
            if (!(sb.length() == 0 && num == 0)) sb.append(num);
        }
        if (sb.length() == 0) return new AInteger("0");
        if (neg) sb.insert(0, '-');
        return new AInteger(sb.toString());
    }
    public AInteger div(AInteger other) {
        String a = this.value;
        String b = other.value;
        if (b.equals("0")) {
            System.out.println("Division by zero error");
            return new AInteger("0");
        }
        boolean neg = false;
        if (a.charAt(0) == '-') {
            neg = !neg;
            a = a.substring(1);
        }
        if (b.charAt(0) == '-') {
            neg = !neg;
            b = b.substring(1);
        }
        StringBuilder result = new StringBuilder();
        String curr = "";
        for (int i = 0; i < a.length(); i++) {
            curr += a.charAt(i);
            int x = 0;
            while (compareAbs(curr, b) >= 0) {
                curr = new AInteger(curr).sub(new AInteger(b)).value;
                x++;
            }
            result.append(x);
            while(curr.length()>1&&curr.charAt(0)=='0') curr = curr.substring(1);
        }
        while (result.length() > 1 && result.charAt(0) == '0') result.deleteCharAt(0);
        if (result.length() == 0) result.append('0');
        if (neg && !result.toString().equals("0")) result.insert(0, '-');
        return new AInteger(result.toString());
    }
    private int compareAbs(String x, String y) {
        x = x.replaceFirst("^0+(?!$)", "");
        y = y.replaceFirst("^0+(?!$)", "");
        if (x.length() != y.length()) return x.length() - y.length();
        return x.compareTo(y);
    }
    public String toString() {
        return value;
    }
}
