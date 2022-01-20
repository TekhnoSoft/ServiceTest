package dao;

import model.DataAcessObjectResult;

public class ServiceDAO {
    
    public DataAcessObjectResult deleteAllCancelledProcess () {
        DataAcessObjectResult result = new DataAcessObjectResult();
        try{
            result.method = "deleteAllCancelledProcess";
            result.success = true;
            result.message = "All cancelled process deleted!";
        }catch(Exception ex){
            ex = null;
        }
        return result;
    }
    
    public DataAcessObjectResult updateAllProcessCompleted () {
        DataAcessObjectResult result = new DataAcessObjectResult();
        try{
            result.method = "updateAllProcessCompleted";
            result.success = true;
            result.message = "All updated completed process!";
        }catch(Exception ex){
            ex = null;
        }
        return result;
    }
    
}
