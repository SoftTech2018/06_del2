package functionality;

public interface ITransmitter {

	/**
	 * Viser op til 3 RM20 tekster på vægten og afventer et svar fra operatøren.
	 * @param txt1
	 * @param txt2
	 * @param txt3
	 * @return Svaret fra operatøren
	 */
	public abstract String RM20(String txt1, String txt2, String txt3);

	/**
	 * Viser en tekst på vægten
	 * @param txt
	 * @return Returbesked fra vægt (ES = fejl)
	 */
	public abstract boolean P111(String txt);

	/**
	 * Beder om aflæsning af vægten
	 * @return Den aflæste vægt
	 */
	public abstract String S();

	/**
	 * Tarer vægten
	 * @return
	 */
	public abstract String T();

	public abstract boolean D();

	public abstract boolean DW();

}