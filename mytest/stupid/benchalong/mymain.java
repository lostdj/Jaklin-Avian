public class mymain
{
    // avian
    /*
t: 33, i: 113469. etc: 40168026
t: 33, i: 124716. etc: 13469328
t: 33, i: 133503. etc: 5076248311451993173
t: 33, i: 122596. etc: 0
t: 33, i: 99364. etc: 794912
    */

    // ems ch
    /*
t: 33, i: 1370. etc: 484980
t: 33, i: 1098. etc: 118584
t: 33, i: 993. etc: -8822395017994331523
t: 33, i: 2170. etc: 0
t: 33, i: 1843. etc: 14744
    */

    // ems ff
    /*
t: 33, i: 20324. etc: 7194696
t: 33, i: 21969. etc: 2372652
t: 33, i: 21666. etc: 2733349079854699145
t: 33, i: 20883. etc: 0
t: 33, i: 18456. etc: 147648
    */

    // ems ff33
    /*
t: 33, i: 20738. etc: 7341252
t: 33, i: 23186. etc: 2504088
t: 33, i: 22420. etc: 7548389914119388561
t: 33, i: 21693. etc: 0
t: 33, i: 18391. etc: 147128

t: 33, i: 33498. etc: 11858292
t: 33, i: 35223. etc: 3804084
t: 33, i: 34166. etc: -3431212409659339367
t: 33, i: 33889. etc: 0
t: 33, i: 25930. etc: 207440
    */

    public static native void myprintln(String s);

    public static int timest()
    {
        return (int)System.currentTimeMillis();
    }

    public static void print(long t, long i, long etc)
    {
        // myprintln("t: " + t + ", i: " + i + ". etc: " + etc);
        System.out.println("t: " + t + ", i: " + i + ". etc: " + etc);
    }

    static volatile int tstop = 33;

    public static void bench(boolean print)
    {
        // +
        {
            long t = timest();

            long i = 0;
            long a = 123, b = 231, c = 0;
            do
            {
                c += a + b;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // -
        {
            long t = timest();

            long i = 0;
            long a = 123, b = 231, c = 0;
            do
            {
                c -= a - b;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // *
        {
            long t = timest();

            long i = 0;
            long a = 123, b = 231, c = 1;
            do
            {
                c *= a * b;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // /
        {
            long t = timest();

            long i = 0;
            long a = 123, b = 231, c = 0;
            do
            {
                c /= b / a;
                ++i;
            } while((timest() - t) < tstop);

            if(print) print(timest() - t, i, c);
        }

        // vec GetDistanceSquared
        {
            long t = timest();

            long i = 0;
            long ax = 5, ay = 4, bx = 3, by = 2, dx = 0, dy = 0, c = 0;
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
