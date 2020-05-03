package rsa.util

import spock.lang.Specification
import spock.lang.Unroll

class MathUtilsTest extends Specification {
    @Unroll
    def "Miller Rabin Test should work correctly with number: #n "(BigInteger n) {
        when:
        boolean result = MathUtils.isPrime(n, 200)
        boolean extended = n.isProbablePrime(200)

        then:
        result == extended

        where:
        n << {
            def temp = []
            (1..5).each { temp << new Random().nextLong() }
            return temp
        }()
    }

    @Unroll
    def "exEu should work correctly with numbers: #a and #b "(BigInteger a, BigInteger b) {
        when:
        boolean result = MathUtils.gcdExtend(a, b)
        boolean extended = a.modInverse(b)

        then:
        result == extended

        where:
        a << {
            def temp = []
            (1..5).each { temp << BigInteger.probablePrime(256, new Random()) }
            return temp
        }()

        b << {
            def temp = []
            (1..5).each { temp << BigInteger.probablePrime(256 * 3, new Random()) }
            return temp
        }()
    }

    @Unroll
    def "monPro work correctly with numbers: #a,  #b and #n"(BigInteger a, BigInteger b, BigInteger n) {
        when:
        boolean result = MathUtils.monPro(a, b, n)
        boolean extended = a.multiply(b).mod(n)

        then:
        result == extended

        where:
        a << {
            def temp = []
            (1..5).each {
                BigInteger tmp = BigInteger.probablePrime(8, new Random())
                temp << (tmp.compareTo(0) == -1 ? -tmp : tmp)
            }
            return temp
        }()

        b << {
            def temp = []
            (1..5).each {
                BigInteger tmp = BigInteger.probablePrime(8, new Random())
                temp << (tmp.compareTo(0) == -1 ? -tmp : tmp)
            }
            return temp
        }()
        n << {
            def temp = []
            (1..5).each {
                BigInteger tmp = BigInteger.probablePrime(8, new Random())
                temp << (tmp.compareTo(0) == -1 ? -tmp : tmp)
            }
            return temp
        }()
    }

    @Unroll
    def "monPow work correctly with numbers: #a,  #b and #n"(BigInteger a, BigInteger b, BigInteger n) {
        when:
        boolean result = MathUtils.modPow(a, b, n)
        boolean extended = a.modPow(b, n)

        then:
        result == extended

        where:
        a << {
            def temp = []
            (1..5).each {
                BigInteger tmp = BigInteger.probablePrime(8, new Random())
                temp << (tmp.compareTo(0) == -1 ? -tmp : tmp)
            }
            return temp
        }()

        b << {
            def temp = []
            (1..5).each {
                BigInteger tmp = BigInteger.probablePrime(8, new Random())
                temp << (tmp.compareTo(0) == -1 ? -tmp : tmp)
            }
            return temp
        }()
        n << {
            def temp = []
            (1..5).each {
                BigInteger tmp = BigInteger.probablePrime(8, new Random())
                temp << (tmp.compareTo(0) == -1 ? -tmp : tmp)
            }
            return temp
        }()
    }

    @Unroll
    def "genPow work correctly with bit length: #n"(int n) {
        when:
        def result = MathUtils.genPrime(n)

        then:
        result.isProbablePrime(100)

        where:
        n << [128, 256, 512]
    }
}
