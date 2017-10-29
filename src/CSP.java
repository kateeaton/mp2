import java.lang.reflect.Array;
import java.util.ArrayList;

public class CSP {
    private Character value;
    private ArrayList<Character> domain = new ArrayList<>();
    public ArrayList<Boolean> visited = new ArrayList<>();
    //public ArrayList<ArrayList<Character>> charMap = new ArrayList<>();
    public Character[][] charMap = new Character[5][5];
    public Integer x;
    public Integer y;
    public Integer i;
    public boolean initialValue;
    public boolean parent;
    public Character getValue(){
        return value;
    }
    public ArrayList<Character> getDomain(){return domain;}
    public void addDomain(Character arg){
        domain.add(arg);
    }
    public void subDomain(Character arg){
        domain.remove(arg);
    }
    public Boolean isDomainEmpty(){
        return domain.isEmpty();
    }
    public void setDomain(ArrayList<Character> arg){domain.addAll(arg);}
    public void setMap(Character inValue){charMap[x][y] = inValue;}
    public void setMapValue(Integer newX, Integer newY, Character inValue){
        charMap[newX][newY] = inValue;
    }
    public void upperCase(){
        for(int i=0; i<charMap.length; i++){
            for(int j=0; j<charMap[i].length; j++){
                charMap[i][j] = Character.toUpperCase(charMap[i][j]);
            }
        }
    }
    public void printMap(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++) {
                System.out.print(charMap[i][j]);
            }
            System.out.println();
        }
    }
    //public void updateMap(Character inValue, Integer newX, Integer newY){charMap.get(newX).set(newY, inValue);}
    public void updateMap(Character[][] newMap){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                charMap[i][j] = newMap[i][j];
            }
        }
    }
    public void setValue(Character arg){
        value = arg;
    }


}
