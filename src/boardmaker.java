import java.util.ArrayList;

public class boardmaker {
    public ArrayList<ArrayList<Pawn>> Board() {

        ArrayList<ArrayList<Pawn>> game = new ArrayList();

        for (int i = 0; i < 8; i++) {
            game.add(new ArrayList(8));
            for (int j = 0; j < 8; j++) {
                game.get(i).add(new Pawn());
            }
        }

        return game;
    }
}
