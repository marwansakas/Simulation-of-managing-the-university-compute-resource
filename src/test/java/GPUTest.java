import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.objects.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GPUTest {
    private static GPU gpu;
    
    @BeforeEach
    void setUp() {
        gpu = new GPU("RTX3090");
    }

    @Test
    void getType() {
        assertEquals(gpu.getType(), gpu.getAType("RTX3090"));
    }

    @Test
    void getModel() {
        assertNull(gpu.getModel());
        Model a = new Model();
        gpu.setModel(a);
        assertEquals(gpu.getModel(),a);
    }

    @Test
    void getWorking(){
        assertEquals(true, gpu.isWorking());
        gpu.setNotWorking();
        assertEquals(false, gpu.isWorking());
    }

    @Test
    void resetState(){
        gpu.resetState();
        assertEquals(gpu.getCurrent_index() , 0);
    }
}