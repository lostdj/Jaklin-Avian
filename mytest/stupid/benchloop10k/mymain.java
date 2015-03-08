public class mymain
{
    public static volatile int k = 10000;
    public static volatile int s = 0;
    public static volatile int i = 0;
    public static volatile int j = 0;
    public static void foo()
    {
        for (s=0,i=0;i<k;i++) {
            for (j=0;j<k;j++)
                s+=i*j;
        }
    }
    public static long _t = 0;
    public static void main(String[] args)
    {
        System.out.println("hello :)");
        long s = System.currentTimeMillis();
        // foo();
        long e = System.currentTimeMillis();
        _t = e-s;
        System.out.println("...done in: "+(e-s));
        // System.out.println(e-s);
    }
    // public static native void myprintln(String s);

    // java -cp mytest benchloop10k
    // 1360

    // avian tux64debug
    // meh
 
    // avian tux64fast
    // 30401

    // ems fast ch
    // meh

    // ems fast ff
    // 63981
}

