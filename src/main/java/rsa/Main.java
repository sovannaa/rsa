package rsa;

import rsa.algoritm.FileCipher;
import rsa.algoritm.KeyGenerator;


public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            switch (args[0]) {
                case "gen":
                    new KeyGenerator(Integer.parseInt(args[2])).saveToFile(args[1]);
                    break;
                case "encr":
                    FileCipher.encrypt(args[1], args[2]);
                    break;
                case "decr":
                    FileCipher.decrypt(args[1], args[2]);
                    break;
                default:
                    helpMessage();
            }
        } else {
            helpMessage();
        }
    }

    private static void helpMessage() {
        System.out.println("gen  [name of file with keys]  [key length in bits]");
        System.out.println("encr  [input file name]  [name of file with public key]");
        System.out.println("decr  [input file name]  [name of file with private keys]");
    }
}
