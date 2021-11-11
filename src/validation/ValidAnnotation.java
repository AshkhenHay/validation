package validation;

import validation.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidAnnotation {
    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");


    public static void validEmail(Object email) throws IllegalAccessException {
        Field[] fields = email.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Email.class)) {
                String email1 = (String) field.get(email);
                Matcher matcher = emailPattern.matcher(email1);
                System.out.println("The Email address " + email1 + " is " + (matcher.matches() ? " valid" : "invalid"));
            }
        }
    }

    public static void validAdulthood(Object adulthood) throws IllegalAccessException {
        Field[] fields = adulthood.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Adulthood.class)) {
                LocalDate birthday = (LocalDate) field.get(adulthood);
                if (LocalDate.now().getYear() - birthday.getYear() == 18) {
                    System.out.println("You are Adulthood and you can registred");
                } else {
                    System.out.println("You aren`t Adulthood and you can not registred");
                }
            }
        }
    }

    public static void validLenght(Object lenght) throws IllegalAccessException {
        Field[] fields = lenght.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Length.class)) {
                String name = (String) field.get(lenght);
                Length annotationLenght = field.getAnnotation(Length.class);
                if (name.length() < annotationLenght.min() && name.length() > annotationLenght.max())
                    System.out.println("Your name must be containt min "
                            + annotationLenght.min() + "symbol, and max " + annotationLenght.max() + "symbol");
            }
        }
    }

    public static void validMaxMin(Object maxMin) throws IllegalAccessException {
        Field[] fields = maxMin.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Min.class) && field.isAnnotationPresent(Max.class)) {
                int discount = (int) field.get(maxMin);
                Min minAnnotation = field.getAnnotation(Min.class);
                Max maxAnnotation = field.getAnnotation(Max.class);
                if (discount > maxAnnotation.value() && discount < minAnnotation.value())
                    System.out.println("Your discount rate is wrong. It must be greater than"
                            + minAnnotation.value() + "  and less than" + maxAnnotation.value());
            }
        }
    }
}
