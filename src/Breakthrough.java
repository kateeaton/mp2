import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.Math.*;

public class Breakthrough {

    public int p0_rem;
    public int p1_rem;
    public boolean current_player;
    //public ArrayList<ArrayList<Pawn>> game = new ArrayList();
    public static boolean p1 = true;
    public static boolean p0 = false;

    public void PlayBreakthrough(boolean p1_start, ArrayList<ArrayList<Pawn>> game){
        //initialize piece counts
        p0_rem = 16;
        p1_rem = 16;
        //game = gameIn;

        //initialize board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 2; j++){
                game.get(i).get(j).Place(p0);
                game.get(7-i).get(7-j).Place(p1);
            }
        }

        if(p1_start){ current_player= true;}
        else {current_player = false;}
        MinimaxDecide(game);
        PrintCurrent(game);

        //initialize first turn
        if(p1_start){ current_player= true;}
        else {current_player = false;}
/*
        while(!complete()){
            //first player turn
            MinimaxDecide();
            current_player = !current_player;
            //second player turn
            AlphaBetaDecide();
            current_player = !current_player;
        }
*/
    }
    public void MinimaxDecide(ArrayList<ArrayList<Pawn>> game){
        int choice_x = 0;
        int choice_y = 0;
        int move = 0;
        int curr_x = 0;
        int curr_y = 0;
        ArrayList<ArrayList<Integer>> MyTuples = FindPawns(game, current_player);
        ArrayList<ArrayList<Integer>> TheirTuples = FindPawns(game, !current_player);
        ArrayList<Double> utility = new ArrayList(8);
        for( ArrayList<Integer> list : MyTuples){
            System.out.println();
            for(Integer i : list){
                System.out.print(i);
            }
        }
        for(int i = 0; i < 8; i++){
            curr_x = MyTuples.get(0).get(i);
            curr_y = MyTuples.get(1).get(i);

        }
        //MovePawn(choice_x, choice_y, move, game);
    }

    public void AlphaBetaDecide(ArrayList<ArrayList<Pawn>> game){

    }


    public ArrayList<ArrayList<Integer>> FindPawns(ArrayList<ArrayList<Pawn>> game, boolean player){
        ArrayList<Integer> x = new ArrayList();
        ArrayList<Integer> y = new ArrayList();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(game.get(i).get(j).player == player && game.get(i).get(j).occupied){
                    x.add(i);
                    y.add(j);
                }
            }
        }
        ArrayList<ArrayList<Integer>> Tuples = new ArrayList();
        Tuples.add(x);
        Tuples.add(y);

        return Tuples;
    }
    private boolean complete(ArrayList<ArrayList<Pawn>> game){
        if(P0Victory(game) || P1Victory(game)){return true;}
        return false;
    }

    public void MovePawn(int x, int y, int dx, ArrayList<ArrayList<Pawn>> game){
        game.get(x).get(y).Remove();
        if(game.get(x+dx).get(y+1).occupied == true){
            game.get(x+dx).get(y+1).Capture();
            if(!current_player)   {p1_rem = p1_rem-1;}  //if current player == 0
            else {p0_rem = p0_rem-1;}
        }
        else{game.get(x+dx).get(y+1).Place(current_player);}
    }

    public boolean P0Victory(ArrayList<ArrayList<Pawn>> game){
        if (p1_rem == 0 ){return true;}
        for( int i = 0; i < 8; i++){
            if(game.get(i).get(7).occupied || game.get(i).get(7).player == p0){return true;}
        }
        return false;
    }

    public boolean P1Victory(ArrayList<ArrayList<Pawn>> game){
        if (p0_rem == 0 ){return true;}
        for( int i = 0; i < 8; i++){
            if(game.get(i).get(0).occupied || game.get(i).get(0).player == p1){return true;}
        }
        return false;
    }

    public double Defensive1(ArrayList<ArrayList<Pawn>> game){
        if(current_player){ //player 1
            return 2 * p1_rem + Math.random();
        }
        return 2 * p0_rem + Math.random();
    }

    public double Offensive1(ArrayList<ArrayList<Pawn>> game){
        if(current_player){ //player 1
            return 2 * (30 - p0_rem) + Math.random();
        }
        return 2 * (30 - p1_rem) + Math.random();
    }

    public double Defensive2(ArrayList<ArrayList<Pawn>> game){ return 0;}
    public double Offensive2(){ return 0;}

    public void PrintCurrent(ArrayList<ArrayList<Pawn>> game){
        int i = 0;
        for(ArrayList<Pawn> line : game){
            System.out.println();
            for (Pawn value : line) {
                if (!value.occupied) {
                    System.out.print("  ");
                } else if (!value.player) {
                    System.out.print('O');
                } else System.out.print('X');
            }
        }
    }
}
