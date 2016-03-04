import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;


public class Factura
{
	private String denumire;
	 Vector<ProdusComandat> produseComandate=new Vector<ProdusComandat>();
	 static DecimalFormat df=new DecimalFormat("#.###");
	 Gestiune gestiune=Gestiune.getInstace();
	
	public void setProduseComandate(Vector<ProdusComandat> produseComandate)
	{
		this.produseComandate=produseComandate;
	}
	public Vector<ProdusComandat> getProduseComandate()
	{
		return this.produseComandate;
	}
	public void addProdusComandat(ProdusComandat pc)
	{
		produseComandate.add(pc);
	}
	public void setDenumire(String denumire)
	{
		this.denumire=denumire;
	}
	public double getTotalFaraTaxe()
	{
		double total=0;
		for (int i=0;i<produseComandate.size();i++)
		{
			total+=produseComandate.elementAt(i).getProdus().getPret()* produseComandate.elementAt(i).getCantitate();
		}
		return total;
	}
	public double getTotalCuTaxe()
	{
		double total=0;
		for (int i=0;i<produseComandate.size();i++)
		{
			total+=produseComandate.elementAt(i).getProdus().getPret()* produseComandate.elementAt(i).getCantitate()+produseComandate.elementAt(i).getTaxa();
		}
		return total;
	}
	public double getTaxe()
	{
		double total=0;
		for (int i=0;i<produseComandate.size();i++)
		{
			total+=produseComandate.elementAt(i).getTaxa();
		}
		return total;
	}
	public double getTotalTaraFaraTaxe(String tara)
	{
		double total=0;
		for (int i=0;i<produseComandate.size();i++)
		{
			if (produseComandate.elementAt(i).getProdus().getTaraOrigine().equals(tara))
				total+=produseComandate.elementAt(i).getProdus().getPret()* produseComandate.elementAt(i).getCantitate();
		}
		return total;
	}
	public double getTotalTaraCuTaxe(String tara)
	{
		double total=0;
		for (int i=0;i<produseComandate.size();i++)
		{
			if (produseComandate.elementAt(i).getProdus().getTaraOrigine().equals(tara))
				total+=produseComandate.elementAt(i).getProdus().getPret()* produseComandate.elementAt(i).getCantitate()+produseComandate.elementAt(i).getTaxa();
		}
		return total;
	}
	public double getTaxeTara(String tara)
	{
		double total=0;
		for (int i=0;i<produseComandate.size();i++)
		{
			if (produseComandate.elementAt(i).getProdus().getTaraOrigine().equals(tara))
				total+=produseComandate.elementAt(i).getTaxa();
		}
		return total;
	}
	public String toString()
	{
		String s=this.denumire+"\n\n"+"Total "+df.format(this.getTotalFaraTaxe())+" "+ df.format(this.getTotalCuTaxe())+"\n\n"+"Tara\n";
		for (int i=0;i<gestiune.tari.size();i++)
		{
			s+=gestiune.tari.elementAt(i)+" "+df.format(this.getTotalTaraFaraTaxe(gestiune.tari.elementAt(i)))+" "+df.format(this.getTotalTaraCuTaxe(gestiune.tari.elementAt(i)))+"\n";
		}
		return s;
	}
	public double getTotalCategorieCuTaxe(String cat)
	{
		double total=0;
		for (int j=0;j<produseComandate.size();j++)
		{
			if ( produseComandate.elementAt(j).getProdus().getCategorie().equals(cat) )
			{
				total+=produseComandate.elementAt(j).getProdus().getPret()*produseComandate.elementAt(j).getCantitate()+
						produseComandate.elementAt(j).getTaxa();
			}
		}
		return total;
	}
	public double getTotalCategorieFaraTaxe(String cat)
	{
		double total=0;
		for (int j=0;j<produseComandate.size();j++)
		{
			if ( produseComandate.elementAt(j).getProdus().getCategorie().equals(cat) )
			{
				total+=produseComandate.elementAt(j).getProdus().getPret()*produseComandate.elementAt(j).getCantitate();
			}
		}
		return total;
	}
}
