import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.Math.*;

import static java.util.Collections.max;
import static java.util.Collections.min;

public class Breakthrough {

    public int p0_rem;
    public int p1_rem;
    public boolean current_player;
    //public ArrayList<ArrayList<Pawn>> game = new ArrayList();
    public static boolean p1 = true;
    public static boolean p0 = false;
    public boolean complete_bool = false;
    public void PlayBreakthrough(boolean p1_start, ArrayList<ArrayList<Pawn>> game){
        //initialize piece counts
        p0_rem = 16;
        p1_rem = 16;
        boardmaker h = new boardmaker();

        //initialize board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 2; j++){
                game.get(i).get(j).Place(p0);
                game.get(7-i).get(7-j).Place(p1);
            }
        }

        if(p1_start){ current_player= true;}
        else {current_player = false;}
        PrintCurrent(game);
        while(!complete_bool){
            //first player turn
            System.out.println("Player: " + current_player);
            MinimaxDecide(game, 0, h);
            PrintCurrent(game);
            PlayerCount(game);
            System.out.println("false: " + p0_rem + " true: " + p1_rem);
            current_player = !current_player;
            //second player turn
            System.out.println("Player: " + current_player);
            MinimaxDecide(game, 1, h);
            PrintCurrent(game);
            PlayerCount(game);
            System.out.println("false: " + p0_rem + " true: " + p1_rem);
            //AlphaBetaDecide();
            current_player = !current_player;
        }
    }
    public void PlayerCount(ArrayList<ArrayList<Pawn>> game){
        p0_rem = 0;
        p1_rem = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j <8; j++){
                if(game.get(i).get(j).occupied) {
                    if (!game.get(i).get(j).player) {
                        p0_rem++;
                    }
                    else {p1_rem ++;}
                }
            }
        }
    }
    public void MinimaxDecide(ArrayList<ArrayList<Pawn>> game, int heuristic, boardmaker h){
        double bestScore = -1000;
        int bestDX = 0;
        int bestPawn = 0;
        ArrayList<Minimax> MyTuples = FindPawns(game, current_player);
        ArrayList<Minimax> TheirTuples = FindPawns(game, !current_player);

        //System.out.println();
        //for( Minimax node : MyTuples){ System.out.print(node.x); }
        //System.out.println();
        //for( Minimax node : MyTuples){ System.out.print(node.y); }
        double minimum;

        for(int i = 0; i < MyTuples.size(); i++) {
            for (int j = -1; j < 2; j++) {
                if(isValidMove(i,j,MyTuples, TheirTuples, current_player)){

                    Guess(MyTuples, TheirTuples, i, j, current_player);
                    //TheirTuples = Adjust(MyTuples, TheirTuples);
                    minimum = Min(gameState(MyTuples, TheirTuples,h), 2, heuristic, h);
                    //System.out.println("x: " + MyTuples.get(i).x + " y: " + MyTuples.get(i).y + " score: " + minimum);
                    if (minimum > bestScore) {
                        bestScore = minimum;
                        bestDX = j;
                        bestPawn = i;
                    }
                    MyTuples = FindPawns(game, current_player);
                }
            }
        }
        MovePawn(MyTuples.get(bestPawn).x, MyTuples.get(bestPawn).y, bestDX, game);
    }

    public double Min(ArrayList<ArrayList<Pawn>> game, int depth, int heuristic, boardmaker h){
        int complete = complete(game);
        //System.out.println(complete);
        if(complete != 0){System.out.println("Complete!");}
        if(complete == -1) return 1000;
        if(complete == 1) return -1000;
        ArrayList<Minimax> MyTuples = FindPawns(game, current_player);
        ArrayList<Minimax> TheirTuples = FindPawns(game, !current_player);
        if(depth == 0){ return(HeuristicParse(heuristic, TheirTuples.size(), MyTuples.size())); }
        double bestScore = 1000;
        double maximum;
        for(int i = 0; i < TheirTuples.size(); i++){
            for(int j = -1; j < 2; j++){
                if(isValidMove(i,j, TheirTuples, MyTuples, !current_player)){
                    Guess(TheirTuples, MyTuples, i,j, !current_player);
                    //Adjust(TheirTuples,MyTuples);
                    maximum = Max( gameState(MyTuples, TheirTuples, h), depth-1, heuristic, h);
                    if(maximum < bestScore){
                        bestScore = maximum;
                    }
                    MyTuples = FindPawns(game, current_player);
                    TheirTuples = FindPawns(game, !current_player);
                }
            }
        }
        return bestScore;
    }

    public double Max(ArrayList<ArrayList<Pawn>> game, int depth, int heuristic, boardmaker h){
        int complete = complete(game);
        if(complete == -1){ return -1000;}
        if(complete == 1){return 1000;}
        ArrayList<Minimax> MyTuples = FindPawns(game, current_player);
        ArrayList<Minimax> TheirTuples = FindPawns(game, !current_player);
        if(depth == 0){ return(HeuristicParse(heuristic, MyTuples.size(), TheirTuples.size())); }
        double bestScore = -1000;
        double minimum;
        for(int i = 0; i < MyTuples.size(); i++) {
            for (int j = -1; j < 2; j++) {
                if (isValidMove(i, j, MyTuples, TheirTuples, current_player)) {
                    Guess(MyTuples, TheirTuples, i, j, current_player);
                    //Adjust(MyTuples, TheirTuples);
                    minimum = Max(gameState(MyTuples, TheirTuples, h), depth - 1, heuristic, h);
                    if (minimum > bestScore) {
                        bestScore = minimum;
                    }
                    MyTuples = FindPawns(game, current_player);
                    TheirTuples = FindPawns(game, !current_player);
                }
            }
        }
        return bestScore;
    }

    public ArrayList<ArrayList<Pawn>> gameState(ArrayList<Minimax> MyTuples, ArrayList<Minimax> TheirTuples, boardmaker h){
        ArrayList<ArrayList<Pawn>> m = h.Board();
        for(int i = 0; i < MyTuples.size(); i ++){
            m.get(MyTuples.get(i).x).get(MyTuples.get(i).y).Place(current_player);
        }
        for(int i = 0; i < TheirTuples.size(); i++){
            m.get(TheirTuples.get(i).x).get(TheirTuples.get(i).y).Place(!current_player);
        }
        //System.out.println("GameState:");
        //PrintCurrent(m);
        return m;
    }

    public double HeuristicParse(int heuristic, int myPawns, int theirPawns){
        switch (heuristic){
            case 0:
                return Defensive1(myPawns);
            case 1:
                return Offensive1(theirPawns);
            case 3:
                return Defensive2(myPawns, theirPawns);
            case 4:
                return Offensive2(myPawns, theirPawns);
        }
        return 0;
    }
    public boolean isValidMove(int current, int dx, ArrayList<Minimax> MyTuples, ArrayList<Minimax> TheirTuples, boolean player){
        int newX = MyTuples.get(current).x + dx;
        int newY;
        if(!player){ newY = MyTuples.get(current).y + 1; }
        else {newY = MyTuples.get(current).y - 1;}

        //check to make sure it's within the bounds of the project
        if(newX < 0 || newX > 7){return false;}
        if(newY < 0 || newY > 7){return false;}
        //check to make sure not sharing space with another of our pawns
        for(int i = 0; i < MyTuples.size(); i++){
            if(newX == MyTuples.get(i).x){
                if(newY == MyTuples.get(i).y){
                    return false;
                }
            }
        }
        //check to make sure we aren't forward blocked by the enemy
        for(int i = 0; i < TheirTuples.size(); i++){
            if(newX == TheirTuples.get(i).x){
                if(newY == TheirTuples.get(i).y){
                    if(dx == 0) return false;
                }
            }
        }
        return true;
    }

    public void Guess(ArrayList<Minimax> MyTuples, ArrayList<Minimax> TheirTuples, int current, int dx, boolean player){
        MyTuples.get(current).setX(MyTuples.get(current).x + dx);
        if(player == false) { MyTuples.get(current).setY(MyTuples.get(current).y + 1);
        } else {MyTuples.get(current).setY(MyTuples.get(current).y - 1);}

        for(int i = 0; i < MyTuples.size(); i++){
            for(int j = 0; j < TheirTuples.size(); j++){
                if(MyTuples.get(i).x == TheirTuples.get(j).x && MyTuples.get(i).y == TheirTuples.get(j).y){
                    TheirTuples.remove(j);
                }
            }
        }
    }

    public ArrayList<Minimax> Adjust(ArrayList<Minimax> MyTuples, ArrayList <Minimax> TheirTuples){
        for(int i = 0; i < MyTuples.size(); i++){
            for(int j = 0; j < TheirTuples.size(); j++){
                if(MyTuples.get(i).x == TheirTuples.get(j).x && MyTuples.get(i).y == TheirTuples.get(j).y){
                    TheirTuples.remove(j);
                }
            }
        }
        return TheirTuples;
    }
    public void AlphaBetaDecide(ArrayList<ArrayList<Pawn>> game){

    }


    public ArrayList<Minimax> FindPawns(ArrayList<ArrayList<Pawn>> game, boolean player){
        ArrayList<Minimax> state = new ArrayList();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(game.get(i).get(j).player == player && game.get(i).get(j).occupied){
                    Minimax temp = new Minimax();
                    temp.x = i;
                    temp.y = j;
                    state.add(temp);
                }
            }
        }
        return state;
    }
    private int complete(ArrayList<Minimax> MyTuples, ArrayList<Minimax> TheirTuples){
        if (MyTuples.size() == 0) {return -1;}
        if (TheirTuples.size() == 0) {return -1;}
        for(int i = 0; i < MyTuples.size(); i ++){
            if(MyTuples.get(i).y == 7 && !current_player){ return 1;}
        }
        for(int i = 0; i < TheirTuples.size(); i ++){
            if(TheirTuples.get(i).y == 0 && current_player){ return -1;}
        }

        return 0;
    }

    private int complete(ArrayList<ArrayList<Pawn>> game){
        for(int i = 0; i < 7; i++) {
            if (game.get(i).get(7).occupied && !game.get(i).get(7).player) {
                System.out.println("Complete by edge p0");
                complete_bool = true;
               if(!current_player){return 1;}
               else return -1;
            }
            else if(game.get(i).get(0).occupied && game.get(i).get(0).player){
                System.out.println("Complete by edge p1");
                complete_bool = true;
                if(current_player){return 1;}
                else return -1;
            }
        }
        return 0;
    }

    public void MovePawn(int x, int y, int dx, ArrayList<ArrayList<Pawn>> game){
        boolean player = game.get(x).get(y).player;
        game.get(x).get(y).Remove();
        //System.out.println("Moving " + x + y + " to " + (x+dx));
        if(!player) {
            //if (game.get(x + dx).get(y + 1).occupied == true) {
              //  game.get(x + dx).get(y + 1).Capture();
            //}
            //else
            {game.get(x+dx).get(y+1).Place(player);}
        }else//{
            //if(game.get(x + dx).get(y-1).occupied == true) {
             //   game.get(x + dx).get(y - 1).Capture();
            //}
            //else
                {game.get(x+dx).get(y-1).Place(player);}
        //}
    }

    public double Defensive1(int myPawns){
        double i = 2 * myPawns + Math.random();
        //System.out.println(i);
        return i;
    }

    public double Offensive1(int theirPawns){
        return 2 * (30 - theirPawns) + Math.random();
    }

    public double Defensive2(int myPawns, int theirPawns){ return 0;}
    public double Offensive2(int myPawns, int theirPawns){ return 0;}

    public void PrintCurrent(ArrayList<ArrayList<Pawn>> game){
        int i = 0;
        for(ArrayList<Pawn> line : game){
            System.out.println();
            for (Pawn value : line) {
                if (!value.occupied) {
                    System.out.print("  ");
                } else if (!value.player) {
                    System.out.print(" O");
                } else System.out.print(" X");
            }
        }

        System.out.println();
        //System.out.println("p0 remaining: " + p0_rem);
        //System.out.println("p1 remaining: " + p1_rem);
    }
}
