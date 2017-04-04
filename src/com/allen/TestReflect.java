package com.allen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {
    public static void main(String[] args) {
        // 获取“类类型对象”  方法1
        Class<?> c0 = User.class;

        // 获取“类类型对象”  方法2
        User user1 = new User();
        Class<?> c1 = user1.getClass();

        User user2 = new User();
        Class<?> c2 = user2.getClass();

        System.out.println("user class instance 0:" + c0); //user class instance 0:class com.allen.User
        System.out.println("user class instance 1:" + c1); //user class instance 1:class com.allen.User
        System.out.println("user class instance 2:" + c2); //user class instance 2:class com.allen.User

        //c0 == c1 :true, c1 == c2 :true
        System.out.println("c0 == c1 :" + (c0 == c1) + ", c1 == c2 :" + (c1 == c2));

        try {
            // 获取“类类型对象”  方法3
            Class<?> c3 = Class.forName("com.allen.User");
            System.out.println("user class instance 3:" + c3); //user class c3:class com.allen.User

            System.out.println("c2 == c3 :" + (c2 == c3));

            try {
                User user3 = (User) c3.newInstance();

                //Constructor public com.allen.User(int,java.lang.String,int)
                //Constructor public com.allen.User(int,java.lang.String)
                //Constructor public com.allen.User()
                Constructor<?>[] cons = c3.getConstructors();
                System.out.println("cons.length: " + cons.length);

                for (int i = 0; i < cons.length; i++) {
                    // 一次返回类定义中所有public修饰符修饰的“构造器对象”,其与类定义中的构造器顺序不一定相同
                    System.out.println("Constructor " + cons[i]);
                }

                try {
                    User user4 = (User) cons[1].newInstance(51, "corn");
                    //user instance 4:user instance uid:51 name:corn
                    System.out.println("user instance 4:" + user4);

                    // 返回特定参数类型的“构造器对象”所新建的实例
                    User user40;
                    user40 = (User) c3.getConstructor(int.class, String.class).newInstance(520, "corn0");
                    //user instance 40:user instance uid:520 name:corn0
                    System.out.println("user instance 40:" + user40);

                    // 返回该类“类类型对象”的所有的父类“类类型对象”
                    Class<?> father = c3.getSuperclass();
                    //father class instance class com.allen.Person
                    System.out.println("father class instance " + father);

                    //返回类对象所有的接口对象（其实也是类类型对象）数组
                    Class<?>[] interfaceArray = c3.getInterfaces();
                    //interface class instance 0 interface com.allen.Workable
                    //interface class instance 1 interface com.allen.Thinkable
                    for (int i = 0; i < interfaceArray.length; i++) {
                        // 与implements中接口顺序相同
                        System.out.println("interface class instance " + i + " " + interfaceArray[i]);
                    }

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回“类类型对象”所具有的全部属性实例/对象
        Field[] fieldArray = c0.getDeclaredFields();
        //filed 0 private int com.allen.User.uid
        //filed 1 private java.lang.String com.allen.User.name
        //filed 2 private int com.allen.User.age
        for (int i = 0; i < fieldArray.length; i++) {
            System.out.println("filed " + i + " " + fieldArray[i]);
        }

        User u = new User(12, "yumidi", 99);

        try {
            // 直接获取private/私有属性的值
            Field nameField = c0.getDeclaredField("name");
            //取消Java对访问修饰符的检查
            nameField.setAccessible(true);
            String nameValue = (String) nameField.get(u);
            //name:yumidi
            System.out.println("name:" + nameValue);

            // 直接改变private/私有属性的值
            nameField.set(u, "corn");
            //new name:corn
            System.out.println("new name:" + u.getName());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //访问私有方法
        try {
            Method method = c0.getDeclaredMethod("getResult");
            //取消Java对访问修饰符的检查
            method.setAccessible(true);
            int result = (int) method.invoke(u);
            //result:1000
            System.out.println("result:" + result);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class User extends Person implements Workable, Thinkable {

    private int uid;
    private String name;
    private int age;

    public User() {

    }

    private User(int uid) {
        this.uid = uid;
    }

    protected User(String name) {
        this.name = name;
    }

    public User(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public User(int uid, String name, int age) {
        this.uid = uid;
        this.name = name;
        this.age = age;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int getResult() {
        return 1000;
    }

    @Override
    public String toString() {
        return "user instance uid:" + this.uid + " name:" + this.name;
    }

}

class Person {

}

interface Workable {

}

interface Thinkable {

}