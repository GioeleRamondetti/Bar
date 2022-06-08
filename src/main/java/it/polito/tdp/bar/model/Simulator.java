package it.polito.tdp.bar.model;

import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	// modello del tavolo dato che il bancone è infinito me ne sbatto
	private List<Tavolo> tavoli;
	// parametri della simulazione
	private int NUMERO_EVENTI=2000;
	private int  T_ARRIVO_MAX=10;
	private int NUM_PERSONE_MAX=10;
	private int DURATA_MIN=60;
	private int DURATA_MAX=120;
	private double tolleranza_max=0.9;
	private double occupazione_max=0.5;
	// coda eventi
	private PriorityQueue<Event> queue;
	// aggiornamento stato del mondo
	//stats
	private Statistiche statistiche;
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e=this.queue.poll();
			processEvent(e);
		}
	}
	private void processEvent(Event e) {
		switch(e.getType()) {
			case ARRIVO_GRUPPO_CLIENTI:
				//conta clienti tot
				this.statistiche.incrementaclientitot(e.getNpersone());
				// cerco tavolo
				Tavolo tavolo=null;
				for(Tavolo t:this.tavoli) {
					if(!t.isOccupato() && t.getPosti()>=e.getNpersone() && e.getNpersone()>=this.occupazione_max*t.getPosti()) {
						tavolo=t;
						break;
					}else {
						// tavolo non trovato
					}
				}
				if(tavolo!=null) {
					System.out.println("trovato tavolo "+tavolo.getPosti()+" per persone "+e.getNpersone());
					statistiche.incrementaclientisoddisfatti(e.getNpersone());
					tavolo.setOccupato(true);
					e.setTavolo(tavolo);
					// dopo unpo i clienti se ne vanno
					this.queue.add(new Event(e.getTime().plus(e.getDurata()),EventType.TAVOLO_LIBERATO,e.getNpersone(),e.getDurata(),tavolo,e.getTolleranza()));
				}else {
					// solo bancone
					double tolleranzabancone=Math.random()*100;
					if(tolleranzabancone<=e.getTolleranza()) {
						statistiche.incrementaclientisoddisfatti(e.getNpersone());
						System.out.println("persone si fermano  "+e.getNpersone());
						
					}else {
						statistiche.incrementaclientiinsoddisfatti(e.getNpersone());
						System.out.println("persone vanno a casa  "+e.getNpersone());
					}
					
				}
			break;
			case TAVOLO_LIBERATO:
				e.getTavolo().setOccupato(false);
				break;
		}
		
	}
	public void init() {
		this.queue=new PriorityQueue<Event>();
		this.statistiche=new Statistiche();
		creaTavoli();
		creaEventi();
	}
	private void creaEventi() {
		// 
		// tempo parte da 0
		Duration Arrivo=Duration.ofMinutes(0);
		for(int i=0;i<this.NUMERO_EVENTI ;i++) {
			int nPersone=(int)(Math.random()*this.NUM_PERSONE_MAX+1);
			Duration durata=Duration.ofMinutes(this.DURATA_MIN+ (int)(Math.random()*60+1));
			double tolleranza=Math.random()+this.tolleranza_max;
			Event e=new Event(Arrivo,EventType.ARRIVO_GRUPPO_CLIENTI,nPersone,durata,null,tolleranza);
			this.queue.add(e);
			Arrivo=Arrivo.plusMinutes((int)(Math.random()*this.T_ARRIVO_MAX+1));
			
		}
		
		
		
	}
	private void creaTavolo(int quantita,int dimensione) {
		for(int i=0;i<quantita;i++) {
			this.tavoli.add(new Tavolo(dimensione,false));
		}
	}
	private void creaTavoli() {
		creaTavolo(2,10);
		creaTavolo(4,8);
		creaTavolo(4,6);
		creaTavolo(5,4);
		Collections.sort(this.tavoli,new Comparator<Tavolo>() {

			@Override
			public int compare(Tavolo o1, Tavolo o2) {
				// TODO Auto-generated method stub
				return o1.getPosti()-o2.getPosti();
			}
			
		});
		
	}
}
