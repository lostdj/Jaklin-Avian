public class mymain
{
    // avian
    /*
t: 128, i: 299293. etc: 1.05541e+08
t: 128, i: 525783. etc: 5.67846e+07
t: 128, i: 530068. etc: inf
t: 128, i: 535089. etc: 0
t: 128, i: 435383. etc: 3.48306e+06

// fixed time (was in float). wtf?
t: 33, i: 136692. etc: 4.83052e+07
t: 33, i: 147412. etc: 1.59205e+07
t: 33, i: 150923. etc: inf
t: 33, i: 148147. etc: 0
t: 33, i: 119688. etc: 957504
    */

    // ems ch
    /*
t: 33, i: 1564. etc: 553656
t: 33, i: 1429. etc: 154332
t: 33, i: 3404. etc: inf
t: 33, i: 3699. etc: 0
t: 33, i: 2566. etc: 20528
    */

    // ems ff
    /*
t: 33, i: 35919. etc: 1.27153e+07
t: 33, i: 38599. etc: 4.16869e+06
t: 33, i: 38649. etc: inf
t: 33, i: 38422. etc: 0
t: 33, i: 28246. etc: 225968
    */

    public static native void myprintln(String s);

    public static int timest()
    {
        return (int)System.currentTimeMillis();
    }

    public static void print(int t, float i, float etc)
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

            float i = 0;
            float a = 123, b = 231, c = 0;
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

            float i = 0;
            float a = 123, b = 231, c = 0;
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

            float i = 0;
            float a = 123, b = 231, c = 1;
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

            float i = 0;
            float a = 123, b = 231, c = 0;
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

            float i = 0;
            float ax = 5, ay = 4, bx = 3, by = 2, dx = 0, dy = 0, c = 0;
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
