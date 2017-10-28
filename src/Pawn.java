public class Pawn {

    public boolean player;
    public boolean occupied;
    public int x;
    public int y;


    public void Capture(){player = !player;}
    public void Place(boolean p){
        occupied = true;
        player = p;
    }
    public void Remove(){ occupied = false; }

    public Pawn() {
        player = false;
        occupied = false;
    }
}
