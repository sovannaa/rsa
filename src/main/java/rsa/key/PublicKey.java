package rsa.key;

import java.math.BigInteger;

public class PublicKey {

    private BigInteger e;
    private BigInteger n;

    public PublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }
}
