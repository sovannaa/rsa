package rsa.algoritm;

import rsa.key.PrivateKey;
import rsa.key.PublicKey;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileCipher {
    public static void encrypt(String filename, String pKey) throws IOException {
        PublicKey publicKey = loadPublicKey(pKey);
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] numbers = line.split(" ");
                for (String number : numbers) {
                    System.out.print(publicKey.encrypt(new BigInteger(number)) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Invalid input data");
        }
    }

    public static void decrypt(String filename, String sKey) throws IOException {
        PrivateKey privateKey = loadPrivateKey(sKey);
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] numbers = line.split(" ");
                for (String number : numbers) {
                    System.out.print(privateKey.decrypt(new BigInteger(number)) + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Invalid  input data");
        }
    }

    private static PublicKey loadPublicKey(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            BigInteger n = new BigInteger(reader.readLine());
            BigInteger e = new BigInteger(reader.readLine());
            return new PublicKey(e, n);
        }
    }

    private static PrivateKey loadPrivateKey(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            BigInteger n = new BigInteger(reader.readLine());
            BigInteger d = new BigInteger(reader.readLine());
            return new PrivateKey(d, n);
        }
    }
}
