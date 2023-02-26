package org.future.code.homework;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeWork2 {
    public static class LoginValidationException extends Exception {

    }
    public static void validateLogin(String login) throws LoginValidationException {
        Pattern pat1 = Pattern.compile("[a-z]");
        Pattern pat2 = Pattern.compile("[A-Z]");
        Pattern pat3 = Pattern.compile("\\d");
        Pattern pat4 = Pattern.compile("_+");
        Matcher matcher = pat1.matcher(login);
        Matcher matcher2 = pat2.matcher(login);
        Matcher matcher3 = pat3.matcher(login);
        Matcher matcher4 = pat4.matcher(login);
        if (!matcher.find()||!matcher2.find()||!matcher3.find()||login.length() > 20||!matcher4.find()) {
            System.out.println("Invalid login! ");
            throw new LoginValidationException();
        }
        //Место для Вашего кода из пункта 2
    }

    public static Boolean isLoginValid(String login) {
        //Место для Вашего кода из пункта 3
        try {
            validateLogin(login);
            return true;
        } catch (LoginValidationException error) {
            return false;
        }
    }

    /*
    Это метод main - нажми play что бы запустить тесты
    Ничего не меняй в тестах, они уже написаны так что бы проверить твое решение
    */

    public static void main(String[] args) {
        System.out.println("\nTests for validateLogin");
        AntiCheat.run();
        for (int i = 0; i < loginList.size(); i++) {
            try {
                validateLogin(loginList.get(i));
                printTestCase(i, checkLoginResults.get(i), true, 20);
            } catch(Exception e) {
                printTestCase(i, checkLoginResults.get(i), false, 20);
            }
        }

        System.out.println("\nTests for isLoginValid");
        AntiCheat.run();
        for (int i = 0; i < loginList.size(); i++)
            printTestCase(i + loginList.size(),
                    checkLoginResults.get(i),
                    isLoginValid(loginList.get(i)),
                    20);
    }

    /* Техническая секция - сюда писать ничего не надо */

    public static List<Boolean> checkLoginResults = Arrays.asList(
            true, true, true, false, false, false, true, false, false, false, false, true
    );

    public static List<String> loginList = Arrays.asList(
            "Minecraft_12",                                     // true
            "Player_3433",                                      // true
            "Dok_a111",                                         // true
            "Java",                                             // false
            "1122233",                                          // false
            "Play__",                                           // false
            "_Sun2_",                                           // true
            "____",                                             // false
            "Winx!",                                            // false
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa12_",            // false
            "WOWOWOOWOWOWOOWOWOWOWOWOW",                        // false
            "Correct_22"                                        // true
    );

    public static class AntiCheat {
        public static void run() {
            StringBuilder sb = new StringBuilder("");
            List<String> antiCheatList = new ArrayList<>();
            antiCheatList.addAll(loginList);
            antiCheatList.addAll(checkLoginResults.stream().map(Object::toString).toList());
            antiCheatList.add(sb.toString());
            calcHash(antiCheatList);
        };

        public static String bytesToHex(byte[] bytes) {
            char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars);
        }

        public static void calcHash(List<String> list) {
            String total = String.join("", list);
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(total.getBytes());
                byte[] digest = md.digest();
                System.out.println("AntiCheatCheck: " + bytesToHex(digest));
            } catch (NoSuchAlgorithmException ignored) {}
        }
    }

    public static String constLen(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while (len-- - str.length() > 0) sb.append(" ");
        return sb.toString();
    }

    public static void printTestCase(int n, Boolean exp, Boolean act, int minLen) {
        Function<String, String> green = str -> "\u001B[34m" + str + "\u001B[0m";
        Function<String, String> yellow = str -> "\u001B[33m" + str + "\u001B[0m";
        System.out.print( "TEST CASE " + constLen(String.valueOf(n), 4));
        System.out.print( "Ожидание: " + yellow.apply(constLen(exp.toString(), minLen)) + " Реальность: " + green.apply(constLen(act.toString(), minLen) + " "));
        if (Objects.equals(exp, act)) System.out.print("✅"); else System.out.print("❌");
        System.out.println();
    }

}