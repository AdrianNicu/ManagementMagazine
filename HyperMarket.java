
public class HyperMarket extends Magazin {

	private String tip="HyperMarket";
	public HyperMarket()
	{
		super();
		
	}
	
	@Override
	public double calculScutireTaxe()
	{
		double scutire=0,total=this.getTotalCuTaxe();
		for (int i=0;i<facturi.size();i++)
		{
			if (facturi.elementAt(i).getTotalCuTaxe()>total/2)
			{
				scutire=1;
				break;
			}
		}
		return scutire;
		
	}
	public String getTip()
	{
		return this.tip;
	}

}
