package com.github.stokito.jsmallmap;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.HashMap;

import static java.lang.System.out;

public class Jol {
    public static void main(String[] args) throws Exception {
        out.println(VM.current().details());
        out.println(ClassLayout.parseClass(HashMap.class).toPrintable());
        out.println(ClassLayout.parseClass(SmallMap.class).toPrintable());
    }

}
