public class mymain
{
    // java
    /*
t: 33, i: 682919. etc: 241753326
t: 33, i: 1079164. etc: 116549712
t: 33, i: 1101196. etc: -196305423
t: 33, i: 1022350. etc: 0
t: 33, i: 1074434. etc: 8595472
    */

    // avian
    /*
t: 33, i: 168353. etc: 59596962
t: 33, i: 171749. etc: 18548892
t: 33, i: 174794. etc: -1573880919
t: 33, i: 169392. etc: 0
t: 33, i: 121232. etc: 969856

t: 33, i: 160273. etc: 56736642
t: 33, i: 158132. etc: 17078256
t: 33, i: 153596. etc: -1343783759
t: 33, i: 124904. etc: 0
t: 33, i: 119774. etc: 958192
    */

    // ems ch
    /*
t: 33, i: 1885. etc: 667290
t: 33, i: 1497. etc: 161676
t: 33, i: 1396. etc: 52072209
t: 33, i: 4059. etc: 0
t: 33, i: 2717. etc: 21736

t: 33, i: 1771. etc: 626934
t: 34, i: 1245. etc: 134460
t: 33, i: 3610. etc: -686488087
t: 33, i: 2823. etc: 0
t: 33, i: 2775. etc: 22200
    */

    // ems ff
    /*
t: 33, i: 24103. etc: 8532462
t: 33, i: 25504. etc: 2754432
t: 33, i: 26220. etc: -1890469007
t: 33, i: 25761. etc: 0
t: 33, i: 22055. etc: 176440

t: 33, i: 37407. etc: 13242078
t: 33, i: 38098. etc: 4114584
t: 33, i: 38517. etc: 1117214157
t: 33, i: 38438. etc: 0
t: 33, i: 30027. etc: 240216

t: 33, i: 38128. etc: 13497312
t: 33, i: 38715. etc: 4181220
t: 33, i: 39046. etc: -808214823
t: 33, i: 38806. etc: 0
t: 33, i: 29468. etc: 235744
    */

    public static native void myprintln(String s);

    public static int timest()
    {
        return (int)System.currentTimeMillis();
    }

    public static void print(int t, int i, int etc)
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

            int i = 0;
            int a = 123, b = 231, c = 0;
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

            int i = 0;
            int a = 123, b = 231, c = 0;
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

            int i = 0;
            int a = 123, b = 231, c = 1;
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

            int i = 0;
            int a = 123, b = 231, c = 0;
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

            int i = 0;
            int ax = 5, ay = 4, bx = 3, by = 2, dx = 0, dy = 0, c = 0;
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
