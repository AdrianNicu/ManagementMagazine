import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

 

public abstract class Magazin implements IMagazin
{
	private String nume;
	public Vector<Factura> facturi;
	static DecimalFormat df=new DecimalFormat("#.###");
	Gestiune gestiune=Gestiune.getInstace();
	
	public void setFacturi(Vector<Factura> facturi)
	{
		this.facturi=facturi;
	}
	public Vector<Factura> getFacturi()
	{
		return this.facturi;
	}
	public void setDenumireMagazin(String nume)
	{
		this.nume=nume;
	}
	public String getDenumireMagazin()
	{
		return this.nume;
	}
	@Override
	public double getTotalFaraTaxe()
	{
		double total=0;
		for (int i=0;i<facturi.size();i++)
		{
			total+=facturi.elementAt(i).getTotalFaraTaxe();
		}
		return total;
	}

	@Override
	public double getTotalCuTaxe()
	{
		double total=0;
		for (int i=0;i<facturi.size();i++)
		{
			total+=facturi.elementAt(i).getTotalCuTaxe();
		}
		return total;
	}

	@Override
	public double getTotalCuTaxeScutite()
	{
		double procent=this.calculScutireTaxe(),total=this.getTotalCuTaxe();
		return total-(procent/100)*total;
	}

	@Override
	public double getTotalTaraFaraTaxe(String tara) {
		double total=0;
		for (int i=0;i<facturi.size();i++)
		{
			total+=facturi.elementAt(i).getTotalTaraFaraTaxe(tara);
		}
		return total;
	}

	@Override
	public double getTotalTaraCuTaxe(String tara) {
		double total=0;
		for (int i=0;i<facturi.size();i++)
		{
			total+=facturi.elementAt(i).getTotalTaraCuTaxe(tara);
		}
		return total;
	}

	@Override
	public double getTotalTaraCuTaxeScutite(String tara)
	{
		double total=this.getTotalTaraCuTaxe(tara);
		return (total - (this.calculScutireTaxe()/100) * total);
		
	}
	public String toString()
	{
		String s=this.nume+"\n\n"+"Total "+df.format(this.getTotalFaraTaxe())+" "+ df.format(this.getTotalCuTaxe())+" "+df.format(this.getTotalCuTaxeScutite()) +"\n\n" +"Tara\n";
		for (int i=0;i<gestiune.tari.size();i++)
		{
			s+=gestiune.tari.elementAt(i)+" "+df.format(this.getTotalTaraFaraTaxe(gestiune.tari.elementAt(i)))+" "
					+df.format(this.getTotalTaraCuTaxe(gestiune.tari.elementAt(i)))+
					" "+df.format(this.getTotalTaraCuTaxeScutite(gestiune.tari.elementAt(i)))+ "\n";
		}
		Collections.sort(facturi,new C2());
		for (int i=0;i<facturi.size();i++)
		{
			s+="\n"+facturi.elementAt(i).toString();
		}
		return s;
	}
	public double getTotalCategorieCuTaxe(String cat)
	{
		double total=0;
		for (int j=0;j<facturi.size();j++)
		{
			total+=facturi.get(j).getTotalCategorieCuTaxe(cat);
		}
		return total;
	}
	public double getTotalCategorieFaraTaxe(String cat)
	{
		double total=0;
		for (int j=0;j<facturi.size();j++)
		{
			total+=facturi.get(j).getTotalCategorieFaraTaxe(cat);
		}
		return total;
	}
	public double getTotalCategorieCuTaxeScutite(String cat)
	{
		double total=this.getTotalCategorieCuTaxe(cat);
		return (total - (this.calculScutireTaxe()/100) * total);
	}
	public abstract String getTip();
}

class C2 implements Comparator<Factura>
{
	public int compare(Factura f1,Factura f2)
	{
		
		if (f1.getTotalCuTaxe()- f2.getTotalCuTaxe()>0)
		{
			return 1;
		}
		if (f1.getTotalCuTaxe()- f2.getTotalCuTaxe()<0)
		{
			return -1;
		}
		return 0;
	}
}