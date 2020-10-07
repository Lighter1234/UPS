package SP;

public class Player {

    private final short number;

    private String name;

    public Player(String name, short number) {
        this.name = name;
        this.number = number;
    }

    public short getNumber(){
        return this.number;
    }

    public String getName(){
        return this.name;
    }
}
