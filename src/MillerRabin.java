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
//
//        for(int i=0;i<10;i++) {
//            BigInteger n = BigInteger.probablePrime(20, new SecureRandom());
//            BigInteger[] tab = decomp(n);
//
//            System.out.println(n);
//            System.out.println(" s = " + tab[0] + " et d =  " + tab[1]);
//
//        }


        BigInteger n1 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A63A3620FFFFFFFFFFFFFFFF", 16);

        System.out.println("n1 est " + MillerRabin(n1, 20));


    }

    /**
     * Test de Miller Rabin, prend en parametre un nombre n et un compteur cpt et renvoie :
     * PREMIER si le nombre est premier, COMPLEXE si le nombre est complexe et INCONNU si on peut rien dire
     * sur la nature du nombre.
     * @param n grand nombre entier
     * @param cpt compteur
     * @return la nature de nombre
     */
    public static NATURE MillerRabin(BigInteger n, int cpt) {

        NATURE ret = NATURE.INCONNU;

        for(int c=1; c<=cpt; c++) {

            BigInteger[] sd = decomp(n);
            // to be reviewed
            BigInteger a = (new BigInteger(n.bitLength(), new SecureRandom())).add(BigInteger.TWO).mod(n.subtract(BigInteger.ONE));
            BigInteger tmp = a.modPow(sd[1], n);

            if (tmp.equals(BigInteger.ONE) || tmp.equals(BigInteger.ONE.negate())) {
                ret = NATURE.INCONNU;
                continue;
            }

            BigInteger i;
            for (i = BigInteger.ONE; i.compareTo(sd[0]) != 1; i = i.add(BigInteger.ONE)) {
                BigInteger d2i = sd[1].multiply(BigInteger.TWO.pow(i.intValue()));
                tmp = a.modPow(d2i, n);

                if (tmp.equals(BigInteger.ONE.negate()))
                    ret = NATURE.INCONNU;

                if (tmp.equals(BigInteger.ONE))
                    ret = NATURE.PREMIER;

            }

            if(i.compareTo(sd[0]) != 1 && !tmp.equals(BigInteger.ONE))
                ret = NATURE.COMPLEXE;

            System.out.println("Loop " + c + " conclusion " + ret);

        }

        return ret;
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

    /**
     * Cette enumeration represente les trois etats que le test de MILLER RABIN Peut retourner
     * PREMIER = le nombre est premier
     * COMPLEXE = le nombre est complexe
     * INCONNU = on ne peut rien dire sur la nature du nombre
     */
    public static enum NATURE {
        PREMIER,
        COMPLEXE,
        INCONNU
    }

}
