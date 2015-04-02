package functionality;

import java.io.FileNotFoundException;

public interface IReadFiles {

	/**
	 * Find et produktnavn ud fra et produktnummer
	 * @param productNumber Produktnummer som ønskes lokaliseret
	 * @return Produktnavnet
	 * @throws FileNotFoundException
	 */
	public abstract String getProductName(int productNumber)
			throws FileNotFoundException;

	/**
	 * Opdater log med information om afvejning.
	 * @param oprNr Operatør-nummer
	 * @param vareNr Vare-nummer / Produkt-nummer
	 * @param afvejet Mængde afvejet
	 * @param lager Restmængden på lageret
	 * @throws FileNotFoundException
	 */
	public abstract void writeLog(int oprNr, int vareNr, double afvejet,
			double lager) throws FileNotFoundException;

	/**
	 * Opdater lagerstatus for et produkt
	 * @param productNumber Produktnummer
	 * @param amountUsed Mængde brugt
	 * @return True = update gik godt, false = produktnummer kunne ikke findes
	 * @throws FileNotFoundException
	 */
	public abstract boolean updProductInventory(int productNumber,
			double amountUsed) throws FileNotFoundException;

	/**
	 * Find lagermængden for et specifikt produkt
	 * @param productNumber Produktnummer
	 * @return Lagermængden for produktet
	 * @throws FileNotFoundException
	 */
	public abstract double getProductInventory(int productNumber)
			throws FileNotFoundException;

}