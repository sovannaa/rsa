package rsa.algoritm;

import rsa.key.PrivateKey;
import rsa.key.PublicKey;
import rsa.util.MathUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyGenerator(int keyBitLength)  {
        int pqBitLength = keyBitLength / 2;
        BigInteger p;
        BigInteger q;
        BigInteger n;
        do {
            p = BigInteger.probablePrime(pqBitLength, new Random());
            q = BigInteger.probablePrime(pqBitLength, new Random());
            n = p.multiply(q);
        } while (n.bitLength() != keyBitLength || (p.compareTo(q) == 0));

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        int length = pqBitLength + new Random().nextInt(phi.bitLength() - pqBitLength);
        BigInteger e = MathUtils.getMutuallySimple(phi, length);

        BigInteger d = MathUtils.exEu(e, phi);
        publicKey = new PublicKey(e, n);
        privateKey = new PrivateKey(d, n);
    }

    public void saveToFile(String fileName) {
        try (PrintWriter publicKeyWriter = new PrintWriter(fileName + ".pk");
             PrintWriter privateKeyWriter = new PrintWriter(fileName + ".sk")) {

            PublicKey publicKey = getPublicKey();
            publicKeyWriter.println(publicKey.getN());
            publicKeyWriter.println(publicKey.getE());

            PrivateKey privateKey = getPrivateKey();
            privateKeyWriter.println(privateKey.getN());
            privateKeyWriter.println(privateKey.getD());

        } catch (FileNotFoundException e) {
            System.err.println("Cannot create file with keys");
            e.printStackTrace();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
