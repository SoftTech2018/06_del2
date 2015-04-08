package wcuMain;

public interface IMenuController {

	public abstract void action();

	public abstract String getInput();

	public abstract void start();

	public abstract void loop();
	
	public abstract int getOprID();
	
	public abstract void setOprID(int id);
	
	public abstract int getVareID();
	
	public abstract void setVareID(int id);
	
	public double getTara();
	
	public void setTara(double tara);
	
	public abstract double getAfvejning();
	
	public abstract void setAfvejning(double afvejning);

}