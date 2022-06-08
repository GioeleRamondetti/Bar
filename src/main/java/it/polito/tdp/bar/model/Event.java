package it.polito.tdp.bar.model;

import java.time.Duration;

public class Event implements Comparable<Event>{
	public enum EventType{// event tipe gli do il nome che voglio 
		ARRIVO_GRUPPO_CLIENTI,
		TAVOLO_LIBERATO
	}
	
	private EventType type;
	private Duration time; // mi immagino un numero progressivo da 0 
	private int npersone;
	private Duration durata;
	private Tavolo tavolo;
	private double tolleranza;

	
	public Event(Duration time,EventType type, int npersone, Duration durata, Tavolo tavolo, double tolleranza) {
		super();
		this.type = type;
		this.time = time;
		this.npersone = npersone;
		this.durata = durata;
		this.tavolo = tavolo;
		this.tolleranza = tolleranza;
	}
	public double getTolleranza() {
		return tolleranza;
	}
	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public void setTime(Duration time) {
		this.time = time;
	}
	public void setNpersone(int npersone) {
		this.npersone = npersone;
	}
	public void setDurata(Duration durata) {
		this.durata = durata;
	}
	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}
	public EventType getType() {
		return type;
	}
	public Duration getTime() {
		return time;
	}
	public int getNpersone() {
		return npersone;
	}
	public Duration getDurata() {
		return durata;
	}
	public Tavolo getTavolo() {
		return tavolo;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
}
