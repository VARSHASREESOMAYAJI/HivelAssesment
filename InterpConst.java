import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class InterpConst {
    // Fraction with BigInteger numerator/denominator, always reduced, denominator positive
    static class Frac {
        BigInteger num, den;
        Frac(BigInteger n, BigInteger d) {
            if (d.signum() == 0) throw new ArithmeticException("zero denom");
            if (d.signum() < 0) { n = n.negate(); d = d.negate(); }
            BigInteger g = n.gcd(d);
            if (!g.equals(BigInteger.ONE)) { n = n.divide(g); d = d.divide(g); }
            num = n; den = d;
        }
        static Frac of(BigInteger n) { return new Frac(n, BigInteger.ONE); }
        Frac add(Frac o) {
            BigInteger n = num.multiply(o.den).add(o.num.multiply(den));
            BigInteger d = den.multiply(o.den);
            return new Frac(n, d);
        }
        Frac mul(Frac o) {
            return new Frac(num.multiply(o.num), den.multiply(o.den));
        }
        Frac neg() { return new Frac(num.negate(), den); }
        public String toString() {
            if (den.equals(BigInteger.ONE)) return num.toString();
            return num.toString() + "/" + den.toString();
        }
        boolean isInteger() { return den.equals(BigInteger.ONE); }
    }

    // parse a value string in given base to BigInteger
    static BigInteger parseValueInBase(String s, int base) {
        s = s.trim().toLowerCase();
        BigInteger b = BigInteger.valueOf(base);
        BigInteger acc = BigInteger.ZERO;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            int digit;
            if (c >= '0' && c <= '9') digit = c - '0';
            else if (c >= 'a' && c <= 'z') digit = c - 'a' + 10;
            else throw new IllegalArgumentException("invalid digit '"+c+"'");
            if (digit >= base) throw new IllegalArgumentException("digit >= base");
            acc = acc.multiply(b).add(BigInteger.valueOf(digit));
        }
        return acc;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: java InterpConst <input.json>");
            return;
        }
        String content = new String(Files.readAllBytes(Paths.get(args[0])), "UTF-8");

        // extract k
        Pattern pk = Pattern.compile("\"keys\"\\s*:\\s*\\{[^}]*\"k\"\\s*:\\s*(\\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher mk = pk.matcher(content);
        if (!mk.find()) { System.err.println("Could not find keys.k in JSON"); return; }
        int k = Integer.parseInt(mk.group(1));

        // find all numbered entries "1": { "base":"..", "value":".." }
        Pattern p = Pattern.compile("\"(\\d+)\"\\s*:\\s*\\{[^}]*?\"base\"\\s*:\\s*\"([^\"]+)\"\\s*,[^}]*?\"value\"\\s*:\\s*\"([^\"]+)\"[^}]*?\\}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);

        // keep first k distinct bases (x-values)
        LinkedHashMap<BigInteger, BigInteger> map = new LinkedHashMap<>(); // x -> y
        while (m.find() && map.size() < k) {
            String idx = m.group(1);
            String baseS = m.group(2).trim();
            String valS  = m.group(3).trim();
            int base = Integer.parseInt(baseS);
            BigInteger x = BigInteger.valueOf(base);
            if (map.containsKey(x)) continue; // skip duplicate x
            BigInteger y = parseValueInBase(valS, base);
            map.put(x, y);
        }

        if (map.size() < k) {
            System.err.println("Not enough distinct x values found (need k = " + k + ")");
            return;
        }

        // convert to arrays
        List<BigInteger> xs = new ArrayList<>(map.keySet());
        List<BigInteger> ys = new ArrayList<>(map.values());

        // compute C = sum_i y_i * prod_{j!=i} (-x_j)/(x_i - x_j)
        Frac C = new Frac(BigInteger.ZERO, BigInteger.ONE);
        for (int i = 0; i < k; ++i) {
            Frac li = new Frac(BigInteger.ONE, BigInteger.ONE);
            BigInteger xi = xs.get(i);
            for (int j = 0; j < k; ++j) {
                if (j == i) continue;
                BigInteger xj = xs.get(j);
                BigInteger numer = xj.negate();                // -x_j
                BigInteger denom = xi.subtract(xj);           // x_i - x_j
                li = li.mul(new Frac(numer, denom));
            }
            Frac term = li.mul(Frac.of(ys.get(i)));
            C = C.add(term);
        }

        // reduce and print
        if (C.isInteger()) {
            System.out.println(C.num.toString());
        } else {
            // in case it's not integer (should be int in correct problems) we print reduced fraction
            System.out.println(C.toString());
        }
    }
}
