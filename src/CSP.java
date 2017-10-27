import java.lang.reflect.Array;
import java.util.ArrayList;

public class CSP {
    private Character value;
    private ArrayList<Character> domain = new ArrayList<>();
    public ArrayList<Boolean> visited = new ArrayList<>();
    public Integer x;
    public Integer y;
    public boolean initialValue;
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
    public void setValue(Character arg){
        value = arg;
    }


}
