package threadtest;

public class MyThread implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true)  
            System.out.println(new java.util.Date().getTime());  
	}

	
	class SleepThread extends Thread  
    {  
        public void run()  
        {  
            try 
            {  
                sleep(2000);  
            }  
            catch (Exception e)  
            {  
            }  
        }  
    }  
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  MyThread thread = new MyThread();  
	        SleepThread sleepThread = thread.new SleepThread(); 
	        sleepThread.start(); // ��ʼ�����߳�sleepThread  
	      //  sleepThread.join();  // ʹ�߳�sleepThread�ӳ�2��  
	    /*    thread.start();  
	        boolean flag = false;  
	        while (true)  
	        {  
	            sleep(5000);  // ʹ���߳��ӳ�5��  
	            flag = !flag;  
	            if (flag)  
	                thread.suspend();   
	            else 
	                thread.resume();  
	        } */ 
	    }  

}
