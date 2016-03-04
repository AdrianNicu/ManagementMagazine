
public class MediumMarket extends Magazin
{
	private String tip="MediumMarket";
	public MediumMarket()
	{
		super();
		
	}
	
	@Override
	public double calculScutireTaxe()
	{
		double totalCategorie,total=this.getTotalCuTaxe(),scutiri=0;
		for (int q=0;q<gestiune.categorii.size();q++)
		{
			totalCategorie=0;
			for (int i=0;i<facturi.size();i++)
			{
				for (int j=0;j<facturi.elementAt(i).produseComandate.size();j++)
				{
					if (facturi.elementAt(i).produseComandate.elementAt(j).getProdus().getCategorie().equals(gestiune.categorii.get(q)))
					{
						totalCategorie+=facturi.elementAt(i).produseComandate.elementAt(j).getProdus().getPret() *
								facturi.elementAt(i).produseComandate.elementAt(j).getCantitate() +
								facturi.elementAt(i).produseComandate.elementAt(j).getTaxa();
					}
				}
			}
			if (totalCategorie>total/2)
			{
				scutiri=5;
			}
		}
		return scutiri;
		
	}
	public String getTip()
	{
		return this.tip;
	}

}
