public class mymain
{
@FunctionalInterface
interface Action {
void run(String s);
}

public static void action(Action action){
action.run("Hello!");
}

public static void main(String[] args) {
action(s -> System.out.print(s));
}
}

