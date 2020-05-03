package rsa.util;

import java.math.BigInteger;
import java.util.Random;


public class MathUtils {

    public static BigInteger exEu(BigInteger a, BigInteger b) {
        BigInteger[] result = gcdExtend(a, b);
        if (result[1].compareTo(BigInteger.ZERO) == -1) {
            result[1] = result[1].add(b);
        }
        return result[1];
    }

    private static BigInteger[] gcdExtend(BigInteger a, BigInteger b) {

        BigInteger result[] = new BigInteger[3]; // d, x, y

        if (b.compareTo(BigInteger.ZERO) == 0) {
            result[0] = a;
            result[1] = BigInteger.ONE;
            result[2] = BigInteger.ZERO;

            return result;
        }

        result = gcdExtend(b, a.mod(b));
        BigInteger tmp = result[2];
        result[2] = result[1].subtract(((a.divide(b)).multiply(result[2])));
        result[1] = tmp;

        return result;
    }

    // Рабина-Миллера
    public static boolean isPrime(BigInteger n, int k) {
        BigInteger zero = BigInteger.ZERO;
        BigInteger two = BigInteger.TWO;

        if (n.compareTo(two) == 0 || n.compareTo(BigInteger.valueOf(3)) == 0)
            return true;

        if (n.compareTo(two) == -1 || n.mod(two).compareTo(zero) == 0)
            return false;

        // n − 1= (2^s)*r
        BigInteger r = n.subtract(BigInteger.ONE);
        BigInteger s = BigInteger.ZERO;

        while (r.mod(two).compareTo(zero) == 0) {
            r = r.divide(two);
            s = s.add(BigInteger.ONE);
        }

        for (int i = 0; i < k; i++) {
            byte[] bytes = new byte[n.toByteArray().length];
            BigInteger a;

            do {
                new Random().nextBytes(bytes);
                a = new BigInteger(bytes);
            }
            while ((a.compareTo(two) == -1) || (a.compareTo(n.subtract(two)) != -1));

            // v ← a^r mod n
            BigInteger v = a.modPow(r, n);
            if (v.compareTo(BigInteger.ONE) == 0 || v.compareTo(n.subtract(BigInteger.ONE)) == 0)
                continue;

            for (BigInteger j = BigInteger.ONE; j.compareTo(s) == -1; j = j.add(BigInteger.ONE)) {
                // x ← x^2 mod n
                v = v.modPow(two, n);

                if (v.compareTo(BigInteger.ONE) == 0)
                    return false;

                if (v.compareTo(n.subtract(BigInteger.ONE)) == 0)
                    break;
            }
            if (v.compareTo(n.subtract(BigInteger.ONE)) != 0)
                return false;
        }
        return true;
    }

    public static BigInteger monPro(BigInteger a, BigInteger b, BigInteger n) throws Exception {
        int length = n.bitLength();
        BigInteger r = getMutuallySimple(n, length);
        BigInteger k = (r.multiply(r.modInverse(n)).subtract(BigInteger.ONE)).divide(n);

        BigInteger a_2 = (a.multiply(r)).mod(n);
        BigInteger b_2 = (b.multiply(r)).mod(n);

        BigInteger x = a_2.multiply(b_2);
        BigInteger s = (x.multiply(k)).mod(r);
        BigInteger t = x.add(s.multiply(n));
        BigInteger u = t.divide(r);
        BigInteger c_2 = (u.compareTo(n) == -1) ? u : u.subtract(n);

        return c_2.multiply(r.modInverse(n)).mod(n);
    }

    //Монтгомери
    public static BigInteger modPow(BigInteger a, BigInteger e, BigInteger n) throws Exception {
        int length = n.bitLength();
        BigInteger r = getMutuallySimple(n, length);
        BigInteger a_2 = (a.multiply(r)).mod(n);
        BigInteger x = r.mod(n);

        for (int i = e.bitLength() - 1; i >= 0; i--) {
            x = monPro(x, x, n);
            if (e.testBit(i)) {
                x = monPro(x, a_2, n);
            }
        }

        return monPro(x, BigInteger.ONE, n);
    }

    public static BigInteger getMutuallySimple(BigInteger n, int length) {
        BigInteger r;
        while (true) {
            r = new BigInteger(length, new Random());

            if (r.compareTo(BigInteger.ONE) != 0
                    && r.compareTo(n) == -1
                    && r.gcd(n).compareTo(BigInteger.ONE) == 0) {
                return r;
            }
        }
    }

    public static BigInteger genPrime(int bitLength) {
        do {
            BigInteger n = BigInteger.valueOf(new Random().nextInt(bitLength));
            if (isPrime(n, 10)) {
                return n;
            }
        }
        while (true);
    }
}
