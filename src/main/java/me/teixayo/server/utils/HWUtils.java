package me.teixayo.server.utils;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

@SuppressWarnings("unused")
public class HWUtils {

    public static float getMemoryUsage() {
        return (float) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 0x100000;
    }

    public static float getMaxMemory() {
        return (float) (Runtime.getRuntime().totalMemory()) / 0x100000;
    }

    public static float getCPUUsage() {
        return (float) (((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getProcessCpuLoad() * 100.0f);
    }
}
