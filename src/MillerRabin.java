import org.jetbrains.annotations.Contract;

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

//        BigInteger n1 = BigInteger.valueOf(8);
//        System.out.println("n1, est-il premier ? : " + millerRabinTest(n1, 20));
//        System.out.println("n1, est-il premier ? : " + n1.isProbablePrime(100));

        /*
         ** TESTER LES NOMBRES EN HEXADECIMAL
         */



        String s1 =("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A63A3620FFFFFFFFFFFFFFFF");
        String s2 =("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEC4FFFFFDAF0000000000000000000000000000000000000000000000000000000000000000000000000000000000000002D9AB");
        String s3 =("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF");
        BigInteger n1 = new BigInteger(s1,16);
        BigInteger n2 = new BigInteger(s2,16);
        BigInteger n3 = new BigInteger(s3,16);
        BigInteger tabInt[] = {n1, n2, n3};
        for (int i =0; i<3;i++){
            if(millerRabinTest(tabInt[i],20)){System.out.println("n"+i+" est premier");}
            else {System.out.println("n"+i+" n'est pas premier");}

        }
         int k = eval(128,20);
        System.out.println(k);






    }

    /**
     * cette methode implémente le test de Miller Rabin, en repetant l'algorithme de ce dernier cpt fois.
     * @param n grand nombre entier
     * @param cpt compteur de boucle
     * @return false si le nombre est composé, true si le nombre est probablement premier
     */
    public static boolean millerRabinTest(BigInteger n, int cpt) {
        boolean compose = false;
        boolean premier = true;

        for (int i=0; i<cpt; i++) {

            if(!algoMillerRabin(n))
                return compose; // le nombre est composé
        }

        return premier; // n est probablement premier

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
        BigInteger a = (new BigInteger(n.bitLength(), new SecureRandom())).mod(n.subtract(BigInteger.valueOf(2)).subtract(BigInteger.ONE)).add(BigInteger.valueOf(2));
        BigInteger tmp = a.modPow(sd[1], n);

//        System.out.println("a = " + a);

//        System.out.println("n-1 = d2^s : \ns(" + sd[0] + ") \nd(" + sd[1] + ")");

        if (tmp.equals(BigInteger.ONE) || tmp.equals(n.subtract(BigInteger.ONE)))
            return true;

        for (BigInteger i = BigInteger.ONE; i.compareTo(sd[0]) != 1; i = i.add(BigInteger.ONE)) {

            BigInteger d2i = sd[1].multiply(BigInteger.valueOf(2).pow(i.intValue()));
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
     * La fonction eval() qui prend en entrée une taille en bits b, le compteur cpt et
     * donne en sortie le nombre d’itérations qu’il a fallu répéter avant de trouver un nombre probablement
     * premier.
     * @param b taille de n en bits
     * @param cpt compteur pour millerRabin
     * @return nombre d’itérations
     */
    public static int eval(int b, int cpt){
        int compteur=0;

        BigInteger n = (new BigInteger(b, new SecureRandom()));
        System.out.println("valeur de n est : "+n);
        while (!millerRabinTest(n,cpt)) {
            compteur++;
            n = (new BigInteger(b, new SecureRandom()));
            System.out.println("nouvelle valeur de n est : "+n);
        }

        return compteur;
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
            return expMod(n, a.pow(2), t.divide(BigInteger.valueOf(2))).mod(n);
        return a.multiply(expMod(n, a.pow(2), t.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)))).mod(n);

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
