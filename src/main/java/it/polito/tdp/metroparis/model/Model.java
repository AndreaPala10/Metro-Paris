package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {

	private Graph<Fermata, DefaultEdge> grafo;
	List<Fermata> fermate;
	private Map<Integer, Fermata> fermateIdMap;

	public void creaGrafo() {
		// CREA L'OGGETTO GRAFO
		grafo = new SimpleGraph<Fermata, DefaultEdge>(DefaultEdge.class);

		// AGGIUNGI I VERTICI
		MetroDAO dao = new MetroDAO();
		this.fermate = dao.readFermate();
		
		fermateIdMap=new HashMap<>();
		for (Fermata f : this.fermate) {
			this.fermateIdMap.put(f.getIdFermata(), f);
		}

		Graphs.addAllVertices(this.grafo, this.fermate);

		// AGGIUNGI GLI ARCHI

		// METODO 1: CONSIDERO TUTTI I POTENZIALI ARCHI
//		long tic=System.currentTimeMillis();
//		for (Fermata partenza : this.grafo.vertexSet()) {
//			for (Fermata arrivo : this.grafo.vertexSet()) {
//				if (dao.isConnesse(partenza, arrivo)) {
//					this.grafo.addEdge(partenza, arrivo);
//				}
//			}
//		}
//		long toc=System.currentTimeMillis();
//		System.out.println("Elapsed time metodo1 : "+(toc-tic));

		// METODO 2: DATA UNA FERMATA, TROVO LA LISTA DI QUELLE ADIACENTI
		long tic=System.currentTimeMillis();
		for (Fermata partenza : this.grafo.vertexSet()) {
			List<Fermata> collegate = dao.trovaCollegate(partenza);

			for (Fermata arrivo : collegate) {
				this.grafo.addEdge(partenza, arrivo);
			}
		}
		
		long toc=System.currentTimeMillis();
		System.out.println("Elapsed time metodo2: "+(toc-tic));
		
		//METODO 2a : DATA UNA FERMATA, TROVIAMO LA LISTA DI ID CONNESSI
		tic=System.currentTimeMillis();

		for (Fermata partenza : this.grafo.vertexSet()) {
			List<Fermata> collegate = dao.trovaIdCollegate(partenza, fermateIdMap);

			for (Fermata arrivo : collegate) {
				this.grafo.addEdge(partenza, arrivo);
			}
		}
		
		toc=System.currentTimeMillis();
		System.out.println("Elapsed time metodo2a: "+(toc-tic));	
		
		//METODO 3 : FACCIO UNA QUERY PER PRENDERMI TUTTI GLI EDGES CON UNA SOLA RICHIESTA
		tic=System.currentTimeMillis();

		List<coppieF> allCoppie=dao.getAllCoppie(fermateIdMap);
		for(coppieF coppia : allCoppie) {
			this.grafo.addEdge(coppia.getPartenza(), coppia.getArrivo());
		}
		toc=System.currentTimeMillis();
		System.out.println("Elapsed time metodo3: "+(toc-tic));	
		

		System.out.println("Grafo creato con " + this.grafo.vertexSet().size() + " vertici e "
				+ this.grafo.edgeSet().size() + " archi");
		System.out.println(this.grafo);

	}

	public List<Fermata> getAllFermate(){
		MetroDAO dao = new MetroDAO();
		return dao.readFermate();
	}
	
	public boolean isGrafoLoaded() {
		return this.grafo.vertexSet().size()>0;
	}
}
