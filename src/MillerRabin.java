import java.math.BigInteger;
import java.security.SecureRandom;

public class MillerRabin {

    /**
     * le Programme principale.
     * @param args
     */
    public static void main(String[] args) {

        BigInteger bint1 = new BigInteger(20, new SecureRandom());
        BigInteger bint2 = new BigInteger(20, new SecureRandom());
        BigInteger bint3 = new BigInteger(20, new SecureRandom());

        System.out.println("Num 1 : " + bint1);
        System.out.println("Num 2 : " + bint2);
        System.out.println("Num 3 : " + bint3);

        System.out.println("ExpMod Num2^Num3 : " + expMod(bint1, bint2, bint3));
        System.out.println("ExpMod Num2^Num3 : " + bint2.modPow(bint3, bint1));


        for(int i=0;i<10;i++) {
            BigInteger n = BigInteger.probablePrime(20, new SecureRandom());
            BigInteger[] tab = decomp(n);

            System.out.println(n);
            System.out.println(" s = " + tab[0] + " et d =  " + tab[1]);

        }


    }


    /**
     * Methode de calcule d'exponentiation modulaire sur les grand nombres entiers.
     * Elle prend n, a et t et retourne (a^t mod n).
     * @param n modulo.
     * @param a base.
     * @param t exposant.
     * @return
     */
    public static BigInteger expMod(BigInteger n, BigInteger a, BigInteger t) {

        System.out.println("ExpMod(" + n + ", " + a + ", " + t + ").");

        if(t.equals(BigInteger.ONE))
            return a.mod(n);

        if(t.getLowestSetBit() != 0)
            return expMod(n, a.pow(2), t.divide(BigInteger.TWO)).mod(n);
        return a.multiply(expMod(n, a.pow(2), t.subtract(BigInteger.ONE).divide(BigInteger.TWO))).mod(n);

    }

    /**
     * Cette methode prend n en entrée et renvoie s et d telque n-1 = 2^s*d avec d impair.
     * @param n nombre entier
     * @return [s, d] avec s et d de type BigInteger
     */
    public static BigInteger[] decomp(BigInteger n){

        BigInteger tab[] = new BigInteger[2];
        BigInteger d=n.subtract(BigInteger.valueOf(1));;
        BigInteger s=BigInteger.valueOf(0);

        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            d=d.divide(BigInteger.valueOf(2));
            s=s.add(BigInteger.valueOf(1));
        }

        tab[0]=s;
        tab[1]=d;
        return tab;
    }


}
