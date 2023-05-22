
import bgu.spl.mics.application.objects.CPU;

import bgu.spl.mics.application.objects.GPU;

import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CPUTest {

    private static CPU cpu;


    @BeforeEach
    void setUp() {
        cpu = new CPU(32);
    }

    @Test
    void setD1(){
        DataBatch temp = new DataBatch(new Data("Images" , 1000), new GPU("RTX3090"), 0);
        cpu.setD1(temp);
        assertEquals(temp, cpu.getD1());
    }

    @Test
    void getCores() {
        assertEquals(cpu.getCores(),32);
    }

    @Test
    void setNumTicks() {
        cpu.setnumTicks(0);
        assertEquals(cpu.getNumTicks(), 0);
    }



}