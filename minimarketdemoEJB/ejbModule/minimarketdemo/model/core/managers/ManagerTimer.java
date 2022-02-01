package minimarketdemo.model.core.managers;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

@Stateless
public class ManagerTimer {

    /**
     * Default constructor. 
     */
    public ManagerTimer() {
        // TODO Auto-generated constructor stub
    }
	
	@SuppressWarnings("unused")
	//@Schedule(second="*/10", minute="*", hour="8-23", dayOfWeek="Mon-Fri",
    //  dayOfMonth="*", month="*", year="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) {
        System.out.println("@Schedule called at: " + new java.util.Date());
    }
}