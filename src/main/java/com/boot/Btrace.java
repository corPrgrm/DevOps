package com.boot;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

@BTrace
public class Btrace {
    @OnMethod(
            clazz = "com.boot.Cpu",
            method="test",
            location=@Location(Kind.ENTRY)
    )

    public static void andRead(@ProbeClassName String pcn , @ProbeMethodName String pmn, /*@Return*/ AnyType[] args){//加上return就是返回值了
        BTraceUtils.printArray(args);
        BTraceUtils.println(pcn+","+pmn);
        BTraceUtils.println();
    }
}
