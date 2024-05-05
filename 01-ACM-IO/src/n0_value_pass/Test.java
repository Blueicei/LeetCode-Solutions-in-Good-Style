package n0_value_pass;

public class Test {
    public static void main(String[] args) {
        Person p1 = new Person();
        String name = "fuke";
        Dog d1 = new Dog();
        d1.setName("hh");
        p1.setName(name);
        p1.setDog(d1);
        System.out.println(p1);
        name = "kefu";
        d1.setName("xx");
        System.out.println(p1);
    }
}
