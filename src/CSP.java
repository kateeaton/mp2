import java.util.ArrayList;

public class CSP {
    private Character value;
    private ArrayList<Character> domain;
    public Character getValue(){
        return value;
    }
    public void addDomain(Character arg){
        domain.add(arg);
    }
    public void subDomain(Character arg){
        domain.remove(arg);
    }
    public Boolean isDomainEmpty(){
        return domain.isEmpty();
    }
    public void setValue(Character arg){
        value = arg;
    }


}
