import java.math.BigInteger;
import java.security.SecureRandom;

public class MillerRabin {

    /**
     * le Programme principale.
     * @param args
     */
    public static void main(String[] args) {

        BigInteger a,t,n;
        int rpt, cpt;

        if(args.length < 2) {
            System.out.println("Tapez une de ces instructions pour avoie de l'aide :");
            System.out.println("    java MillerRabin decomp");
            System.out.println("    java MillerRabin expmod");
            System.out.println("    java MillerRabin miller");
            System.out.println("    java MillerRabin eval");
            return;
        }

        switch(args[0]) {

            case "decomp":
                if (args.length != 3){
                    System.out.println("Veuillez suivre cette expression :");
                    System.out.println("    java MillerRabin decomp <n> <cpt>");
                    System.out.println("n      le nombre entier a tester en Hexadecimal\n" +
                                       "       mettez 0 pour qu'il sera choisi au hasard.");
                    System.out.println("rpt    nombre de répétition de la methode.");
                    return;
                }

                System.out.print("DECOMPE PARAMS : ");
                n = new BigInteger(args[1], 16);
                if(n.equals(BigInteger.ZERO))
                    n = new BigInteger(128, new SecureRandom());
                System.out.print("n = " + n + " | ");
                rpt = Integer.parseInt(args[2]); System.out.println("rpt = " + rpt);

                for (int i=1 ; i <= rpt ; i++) {
                    BigInteger[] sd = decomp(n);
                    System.out.println("decomp["+ i +"](" + n + ") = (s=" + sd[0] + ", d=" + sd[1] + ")");
                }
                break;

            case "expmod":
                if (args.length != 4 && args.length != 2){
                    System.out.println("Veuillez suivre une de ces expressions :");
                    System.out.println("    java MillerRabin expmod <a> <t> <n>");
                    System.out.println("    java MillerRabin expmod <rpt>");
                    System.out.println("a      la base en Hexadecimal");
                    System.out.println("t      l'exposant en Hexadecimal");
                    System.out.println("n      le modulo en Hexadecimal");
                    System.out.println("rpt    nombre de répétition de expmod\n" +
                                       "       si vous choisissez cette expression les parametres a, t et n\n" +
                                       "       seront choisis aléatoirement");
                    return;
                }

                System.out.print("EXPMOD PARAMS : ");
                if(args.length == 4) {
                    a = new BigInteger(args[1], 16); System.out.print("a = " + a + " | ");
                    t = new BigInteger(args[2], 16); System.out.print("t = " + t + " | ");
                    n = new BigInteger(args[3], 16); System.out.println("n = " + n);

                    System.out.println("expMod(" + a + ", " + t + ", " + n + ") = " + expMod(n, a, t));
                    return;

                }

                SecureRandom sr = new SecureRandom();
                rpt = Integer.parseInt(args[1]); System.out.println("rpt = " + rpt);

                for (int i=1; i<= rpt; i++) {
                    a = new BigInteger(128, sr);
                    t = new BigInteger(128, sr);
                    n = new BigInteger(128, sr);
                    System.out.println("expMod[" + i + "](" + a.toString(16) + ", " + t.toString(16) + ", " + n.toString(16) + ") = " + expMod(n, a, t).toString(16));
                }
                break;

            case "miller":
                if (args.length != 3){
                    System.out.println("Veuillez suivre cette expression :");
                    System.out.println("    java MillerRabin miller <n> <cpt>");
                    System.out.println("n      le nombre entier a tester en Hexadecimal");
                    System.out.println("cpt    compteur d'algorithme de miller rabin");
                    return;
                }

                System.out.print("MILLER PARAMS : ");
                n = new BigInteger(args[1], 16); System.out.print("n = " + n + " | ");
                cpt = Integer.parseInt(args[2]); System.out.println("cpt = " + cpt);

                System.out.println("millerRabin("+ n +") = " + (millerRabinTest(n, cpt)?"probablement premier":"composé"));
                break;

            case "eval":
                if (args.length != 4){
                    System.out.println("Veuillez suivre cette expression :");
                    System.out.println("    java MillerRabin eval <bits> <cpt> <rpt>");
                    System.out.println("bits   taille en bits de nombre entier");
                    System.out.println("cpt    compteur d'algorithme de miller rabin");
                    System.out.println("rpt    nombre de répétition de la fonction eval()");
                    return;
                }

                System.out.print("EVAL PARAMS : ");
                int b = Integer.parseInt(args[1]); System.out.print("bits = " + b + " | ");
                int cmpt = Integer.parseInt(args[2]); System.out.print("cpt = " + cmpt + " | ");
                rpt = Integer.parseInt(args[3]); System.out.println("rpt = " + rpt);

                int s = 0;
                int i;
                for (i=1; i <= rpt; i++){
                    System.out.print("eval[" + i + "]" );
                    s += eval(b, cmpt);
                    System.out.println();
                }
                System.out.println("eval[ Moyenne ] = " + (s/--i));
                break;

            default:
                System.out.println("Tapez une de ces instructions pour avoie de l'aide :");
                System.out.println("    java MillerRabin decomp");
                System.out.println("    java MillerRabin expmod");
                System.out.println("    java MillerRabin miller");
                System.out.println("    java MillerRabin eval");

        }

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
        BigInteger tmp = expMod(n, a, sd[1]);

        if (tmp.equals(BigInteger.ONE) || tmp.equals(n.subtract(BigInteger.ONE)))
            return true;

        for (BigInteger i = BigInteger.ONE; i.compareTo(sd[0]) != 1; i = i.add(BigInteger.ONE)) {

            BigInteger d2i = sd[1].multiply(BigInteger.valueOf(2).pow(i.intValue()));
            tmp = expMod(n, a, d2i);

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
        while (!millerRabinTest(n,cpt)) {
            compteur++;
            n = (new BigInteger(b, new SecureRandom()));
        }

        System.out.print("(" + n + ") = " + compteur);

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

        if(t.equals(BigInteger.ONE))
            return a.mod(n);

        if(t.getLowestSetBit() != 0)
            return expMod(n, a.pow(2).mod(n), t.divide(BigInteger.valueOf(2))).mod(n);
        return a.multiply(expMod(n, a.pow(2).mod(n), t.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)))).mod(n);

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
