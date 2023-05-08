package org.example.common;

import java.lang.String;

public class utils {

    public static String str_clean(String str) {
        return str
        .replace("; ", ";")
        .replace(".", "_")
        .replace(",", "_")
        .replace(": ", ":")
        .replace("'s", "_s")
        .replace(" ", "_")
        .replace("-", "_")
        .replace("’", "")
        .replace("©", "")
        .replace("›", "")
        .replace("‹", "")
        .replace("°", "")
        .replace("%", "")
        .replace("$", "")
        .replace("@", "")
        .replace("!", "")
        .replace("&", "")
        .replace("~", "")
        .replace("'", "")
        .replace("+", "")
        .replace("ν", "")
        .replace("η", "")
        .replace("?", "")
        .replace(">", "")
        .replace("<", "")
        .replace("μ", "")
        .replace("\"", "")
        .replace("\\", "")
        .replace("^", "")
        .replace("#", "")
        .replaceAll("[\\p{Ps}\\p{Pe}]", "") // To remove all opening & closing brackets (https://stackoverflow.com/a/25853119/6390175)
        .replaceAll("[^A-Za-z0-9] ","")
        ;
    }

    public static void print(Object text) { System.out.println(text); }

	public static void log(String text) { print("[ log ] " + text); }
	
    public static void error(String text) { print("[ error ] " + text); System.exit(1); }
	
	public static void line_separator() { print("\n----------------------------------\n"); }

}