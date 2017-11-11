import java.lang.reflect.Array;
import java.util.ArrayList;
/*The representation of each space in the game*/
public class CSP {
    //Character value associated with the space
    private Character value;
    //possible values of the space
    private ArrayList<Character> domain = new ArrayList<>();
    //save if a space has already been visited for a specific value
    public ArrayList<Boolean> visited = new ArrayList<>();
    //character values of maze at the point of assigning this space a value. only used in dumb version
    public Character[][] charMap = new Character[7][7];
    //x coordinate of space
    public Integer x;
    // y coordinate of space
    public Integer y;
    //holds where in the domain this value was assigned, only used in dumb version
    public Integer i;
    //manhattan distance from space to final value
    public Integer distance;
    //true if it is immutable
    public boolean initialValue;
    //true if it is the starting initial value
    public boolean parent;
    public Character getValue(){
        return value;
    }
    public ArrayList<Character> getDomain(){return domain;}
    public CSP(){}
    // add arg to domain
    public void addDomain(Character arg){
        if(!domain.contains(arg)){
            domain.add(arg);
        }
    }
    //subtract arg from domain
    public void subDomain(Character arg){
        if(domain.contains(arg)){
            domain.remove(arg);
        }
    }
    //add argument to domain
    public void setDomain(ArrayList<Character> arg){domain.addAll(arg);}
    //set the current map value to input
    public void setMap(Character inValue){charMap[x][y] = inValue;}
    //set a specific map value to input
    public void setMapValue(Integer newX, Integer newY, Character inValue){
        charMap[newX][newY] = inValue;
    }
    //set the entire character map to uppercase letters
    public void upperCase(){
        for(int i=0; i<charMap.length; i++){
            for(int j=0; j<charMap[i].length; j++){
                charMap[i][j] = Character.toUpperCase(charMap[i][j]);
            }
        }
    }
    //change the current map to be the input map
    public void updateMap(Character[][] newMap){
        for(int i=0; i<newMap.length; i++){
            for(int j=0; j<newMap[i].length; j++){
                charMap[i][j] = newMap[i][j];
            }
        }
    }
    //set the value to be arg
    public void setValue(Character arg){
        value = arg;
    }


}
