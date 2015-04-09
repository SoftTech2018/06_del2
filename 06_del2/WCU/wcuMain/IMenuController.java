package wcuMain;

public interface IMenuController {

	public abstract void action();

	public abstract void start();

	void connect(String host, int port);

}