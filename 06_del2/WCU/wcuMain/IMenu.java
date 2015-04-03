package wcuMain;

public interface IMenu {

	public abstract String ask();

	public abstract void showSTART();

	public abstract void showGET_PROD_NR();

	public abstract void showSET_CONTAINER(int nr);

	public abstract void showADD_PRODUCT(int nr);

	public abstract void showREMOVE_CONTAINER(int nr);

	public abstract void show(String txt);

}