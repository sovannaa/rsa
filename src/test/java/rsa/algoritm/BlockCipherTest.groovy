package rsa.algoritm

import spock.lang.Specification
import spock.lang.Unroll

class BlockCipherTest extends Specification {

    @Unroll
    def "encr/decr test with bit length #bitLength should be correctly"(int bitLength) {
        given:
        BigInteger[] source = [2030031994, 1029011971]

        when:
        KeyGenerator keys = new KeyGenerator(bitLength)

        BigInteger[] encrypted = new BigInteger[2]
        encrypted[0] = keys.getPublicKey().encrypt(source[0])
        encrypted[1] = keys.getPublicKey().encrypt(source[1])

        BigInteger[] decrypted = new BigInteger[2]
        decrypted[0] = keys.getPrivateKey().decrypt(encrypted[0])
        decrypted[1] = keys.getPrivateKey().decrypt(encrypted[1])

        then:
        source == decrypted

        where:
        bitLength << [128, 256, 512]
    }
}
