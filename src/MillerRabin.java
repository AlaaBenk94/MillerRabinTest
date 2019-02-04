import java.math.BigInteger;
import java.security.SecureRandom;

public class MillerRabin {

    /**
     * le Programme principale.
     * @param args
     */
    public static void main(String[] args) {

//        BigInteger bint1 = new BigInteger(20, new SecureRandom());
//        BigInteger bint2 = new BigInteger(20, new SecureRandom());
//        BigInteger bint3 = new BigInteger(20, new SecureRandom());
//
//        System.out.println("Num 1 : " + bint1);
//        System.out.println("Num 2 : " + bint2);
//        System.out.println("Num 3 : " + bint3);
//
//        System.out.println("ExpMod Num2^Num3 : " + expMod(bint1, bint2, bint3).toString(16));
//        System.out.println("ExpMod Num2^Num3 : " + bint2.modPow(bint3, bint1).toString(16));
//

//        for(int i=0;i<10;i++) {
//            BigInteger n = BigInteger.probablePrime(20, new SecureRandom());
//            BigInteger[] tab = decomp(n);
//            System.out.println("n = " + n + " s = " + tab[0] + " et d =  " + tab[1]);
//
//        }
//

        BigInteger n1 = BigInteger.valueOf(8);
        System.out.println("n1, est-il premier ? : " + millerRabinTest(n1, 20));
        System.out.println("n1, est-il premier ? : " + n1.isProbablePrime(100));

    }

    /**
     * cette methode implémente le test de Miller Rabin, en repetant l'algorithme de ce dernier cpt fois.
     * @param n grand nombre entier
     * @param cpt compteur de boucle
     * @return false si le nombre est composé, true si le nombre est probablement premier
     */
    public static boolean millerRabinTest(BigInteger n, int cpt) {

        for (int i=0; i<cpt; i++) {

            if(!algoMillerRabin(n))
                return false; // le nombre est composé
        }

        return true; // n est probablement premier

    }

    /**
     * Test de Miller Rabin, prend en parametre un nombre n et un compteur cpt et renvoie :
     * true si le nombre est premier, false si le nombre est composé.
     * sur la nature du nombre.
     * @param n grand nombre entier
     * @return true si n est probablement premier, false si n est composé.
     */
    public static boolean algoMillerRabin(BigInteger n) {

        BigInteger[] sd = decomp(n);
        // 1 < a < n-1  donc 2 <= a <= n-2 implique que a = 2 + rand % (((n-2)-2)+1)
        BigInteger a = (new BigInteger(n.bitLength(), new SecureRandom())).mod(n.subtract(BigInteger.TWO).subtract(BigInteger.ONE)).add(BigInteger.TWO);
        BigInteger tmp = a.modPow(sd[1], n);

//        System.out.println("a = " + a);

//        System.out.println("n-1 = d2^s : \ns(" + sd[0] + ") \nd(" + sd[1] + ")");

        if (tmp.equals(BigInteger.ONE) || tmp.equals(n.subtract(BigInteger.ONE)))
            return true;

        for (BigInteger i = BigInteger.ONE; i.compareTo(sd[0]) != 1; i = i.add(BigInteger.ONE)) {

            BigInteger d2i = sd[1].multiply(BigInteger.TWO.pow(i.intValue()));
            tmp = a.modPow(d2i, n);

            if (tmp.equals(n.subtract(BigInteger.ONE)))
                return true;

            if (tmp.equals(BigInteger.ONE))
                return false;

        }

        if(!tmp.equals(BigInteger.ONE))
            return false;

        return true;

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
