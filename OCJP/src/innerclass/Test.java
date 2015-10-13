package innerclass;

public class Test {
    public static void main(String[] args) {
        System.out.println(Sub.ID); // Sub wird nicht geladen

        System.out.println(new Sub().ID);

    }
}
