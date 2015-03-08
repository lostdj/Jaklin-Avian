public class mymain
{
    // avian
    /*
t: 33, i: 134332. etc: 4.75535e+07
t: 33, i: 133178. etc: 1.43832e+07
t: 33, i: 136806. etc: inf
t: 33, i: 134932. etc: 0
t: 33, i: 103963. etc: 831704
    */

    // ems ch
    /*
t: 33, i: 1287. etc: 455598
t: 33, i: 909. etc: 98172
t: 33, i: 2298. etc: inf
t: 33, i: 3085. etc: 0
t: 33, i: 2018. etc: 16144
    */

    // ems ff
    /*
t: 33, i: 34175. etc: 1.2098e+07
t: 33, i: 37993. etc: 4.10324e+06
t: 33, i: 38330. etc: inf
t: 33, i: 38556. etc: 0
t: 33, i: 28449. etc: 227592
    */

    public static native void myprintln(String s);

    public static int timest()
    {
        return (int)System.currentTimeMillis();
    }

    public static void print(int t, double i, double etc)
    {
        // myprintln("t: " + t + ", i: " + i + ". etc: " + etc);
        System.out.println("t: " + t + ", i: " + i + ". etc: " + etc);
    }

    static volatile int tstop = 33;

    public static void bench(boolean print)
    {
        // +
        {
            int t = timest();

            double i = 0;
            double a = 123, b = 231, c = 0;
            do
            {
                c += a + b;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // -
        {
            int t = timest();

            double i = 0;
            double a = 123, b = 231, c = 0;
            do
            {
                c -= a - b;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // *
        {
            int t = timest();

            double i = 0;
            double a = 123, b = 231, c = 1;
            do
            {
                c *= a * b;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // /
        {
            int t = timest();

            double i = 0;
            double a = 123, b = 231, c = 0;
            do
            {
                c /= b / a;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // vec GetDistanceSquared
        {
            int t = timest();

            double i = 0;
            double ax = 5, ay = 4, bx = 3, by = 2, dx = 0, dy = 0, c = 0;
            do
            {
                dx = ax - bx;
                dy = ay - by;
                c += (dx * dx) + (dy * dy);
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }
    }

    public static void main(String[] args) throws Exception
    {
        // warmup
        // bench(false);
        // bench(false);
        // bench(false);
        bench(true);
    }
}
