package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {
    /**
     * Enum representing the Data type.
     */
    public enum Type {
        Images, Text, Tabular
    }

    private Type type;
    private int processed;
    private int size;

    public Data(String type, int size) {
        if (type.equals("images") || type.equals("Images"))
            this.type = Type.Images;
        else if (type.equals("Text"))
            this.type = Type.Text;
        else {
            this.type = Type.Tabular;
        }
        this.size = size;
        this.processed = 0;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public synchronized void increaseProccesed() {
        processed += 1000;
    }

    public String getTypeName(){
        if(type.equals(Type.Images)){
            return "Images";
        }else if(type.equals(Type.Text))
            return "Text";
        else
            return "Tabular";
    }

    @Override
    public String toString() {
        return "Data{" +
                "type=" + type +
                ", processed=" + processed +
                ", size=" + size +
                '}';
    }


}
