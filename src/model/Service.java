package model;

import dao.ServiceDAO;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Service {
    
    private final boolean DEBUG_MODE = false;
    private final long SERVICE_INTERLVAL = 1000;
    
    private ServiceDAO dao = new ServiceDAO();
    
    private List<DataAcessObjectResult> actionList = new ArrayList();
    private List<DataAcessObjectResult> failActionList = new ArrayList();
    
    public Service () {
        startLoop();
    }
    
    private void startLoop () {
        start();
        while(true){
            update();
            sleep();
        }
    }
    
    private void start () {
        print("Service started!");
    }
    
    private void update () {
        print("Service updated!");
        actionList.add(dao.deleteAllCancelledProcess());
        actionList.add(dao.updateAllProcessCompleted());
        InvokeDaoActions();
        tryInvokeFailDaoActions();
    }
    
    private void sleep () {
        print("Service sleeping");
        try{
            Thread.sleep(SERVICE_INTERLVAL);
        }catch(Exception ex){
            ex = null;
        }
    }
    
    private void InvokeDaoActions () {
        while(actionList.size() > 0){
            final int FIRST = 0;
            print(actionList.get(FIRST).method+ ", -> success:" + actionList.get(FIRST).success + ", -> message: " + actionList.get(FIRST).message);
            if(!actionList.get(FIRST).success){
                failActionList.add(actionList.get(FIRST));
            }else{
                actionList.remove(actionList.get(FIRST));
            }
        }
    }
    
    private void tryInvokeFailDaoActions () {
        try{
            for(DataAcessObjectResult objFail : failActionList){
                
                Method method = ServiceDAO.class.getMethod(objFail.method, null);
                DataAcessObjectResult result = (DataAcessObjectResult) method.invoke(dao, null);
                if(result.success){
                    failActionList.remove(objFail);
                }
                method = null;
                result = null;
                
            }
        }catch(Exception ex){
            print(ex.getMessage());
            ex = null;
        }
    }
    
    private void print (String msg) {
        if(DEBUG_MODE){
            System.out.println(msg);
        }
        msg = null;
    }
    
}
