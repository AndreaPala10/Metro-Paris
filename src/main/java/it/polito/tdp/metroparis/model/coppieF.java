package it.polito.tdp.metroparis.model;

import java.util.Objects;

public class coppieF {

	Fermata partenza;
	Fermata arrivo;
	
	public coppieF(Fermata partenza, Fermata arrivo) {
		super();
		this.partenza = partenza;
		this.arrivo = arrivo;
	}

	/**
	 * @return the partenza
	 */
	public Fermata getPartenza() {
		return partenza;
	}

	/**
	 * @param partenza the partenza to set
	 */
	public void setPartenza(Fermata partenza) {
		this.partenza = partenza;
	}

	/**
	 * @return the arrivo
	 */
	public Fermata getArrivo() {
		return arrivo;
	}

	/**
	 * @param arrivo the arrivo to set
	 */
	public void setArrivo(Fermata arrivo) {
		this.arrivo = arrivo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrivo, partenza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		coppieF other = (coppieF) obj;
		return Objects.equals(arrivo, other.arrivo) && Objects.equals(partenza, other.partenza);
	}
	
	
	
	
	
}
