public class Calculator {
    int nOpDone;
    double lastRes;

    void build()
    {
        this.nOpDone = 0;
        this.lastRes = 0;
    }

    double add(double x, double y) 
    {
        double output = x+y;
        this.nOpDone++;
        this.lastRes = output;
        
        return output;
    }
    
    double sub(double x, double y) 
    {
        double output = x-y;
        this.nOpDone++;
        this.lastRes = output;
        
        return output;
    }
    
    double mul(double x, double y) 
    {
        double output = x*y;
        this.nOpDone++;
        this.lastRes = output;
        
        return output;
    }
    
    double div(double x, double y) 
    {
        double output = x/y;
        this.nOpDone++;
        this.lastRes = output;
        
        return output;
    }
}
