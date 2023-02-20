package tn.esprit.business;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tn.esprit.entites.Logement;
import tn.esprit.entites.RendezVous;

import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rendezvous")
public class RendezVousBusiness {
	List<RendezVous> listeRendezVous;
	LogementBusiness logementMetier=new LogementBusiness();

	public RendezVousBusiness() {
		listeRendezVous=new ArrayList<RendezVous>();
		listeRendezVous.add(new RendezVous(1, "31-10-2015","15:30", logementMetier.getLogementsByReference(4), "55214078"));
		listeRendezVous.add(new RendezVous(2, "20-12-2015","9:00", logementMetier.getLogementsByReference(1), "21300811"));
		listeRendezVous.add(new RendezVous(3, "17-09-2015","9:15", logementMetier.getLogementsByReference(4), "98102102"));
	
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addRendezVous(RendezVous rendezVous){
		int refLogement=rendezVous.getLogement().getReference();
		Logement logement=logementMetier.getLogementsByReference(refLogement);
		if(logement!=null){
			rendezVous.setLogement(logement);
			return listeRendezVous.add(rendezVous);}
		return false;
	}

	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public boolean updateRendezVous(int idRendezVous, RendezVous rendezVous){
		for(RendezVous r:listeRendezVous){
			if(r.getId()==idRendezVous){
				int index=listeRendezVous.indexOf(rendezVous);
				Logement logement=logementMetier.getLogementsByReference(rendezVous.getLogement().getReference());
				if(logement!=null){
					listeRendezVous.set(index, rendezVous);
					return true;
				}	
			}
		}
		return false;
	}
	public RendezVous getRendezVousById(int id){
		RendezVous rendezVous=null;
		for(RendezVous r:listeRendezVous){
			if(r.getId()==id)
				rendezVous=r;
		}
		return rendezVous;
	}

	@DELETE
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})

	public boolean deleteRendezVous(@PathParam("id") int id){
		Iterator<RendezVous> iterator=listeRendezVous.iterator();
		while(iterator.hasNext()){
			RendezVous r=iterator.next();
			if(r.getId()==id){
				iterator.remove();
				return true;
			}
		}
		return false;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RendezVous> getListeRendezVous() {
		return listeRendezVous;
	}

	public void setListeRendezVous(List<RendezVous> listeRendezVous) {
		this.listeRendezVous = listeRendezVous;
	}

	@GET
	@Path("/{reference}")
	@Produces(MediaType.APPLICATION_XML)
	public List<RendezVous> getListeRendezVousByLogementReference(@PathParam("reference") int reference) {
		List<RendezVous> liste=new ArrayList<RendezVous>();
		for(RendezVous r:listeRendezVous){
			if(r.getLogement().getReference()==reference)
				liste.add(r);
		}
		return liste;
	}
	
	
	

}
