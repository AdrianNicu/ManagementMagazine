
public class MiniMarket extends Magazin {

	private String tip="MiniMarket";
	public MiniMarket()
	{
		super();
		
	}
	@Override
	public double calculScutireTaxe() {
		double total_dintr_o_tara,total=this.getTotalCuTaxe(),scutire=0;
		for (int i=0;i<gestiune.tari.size();i++)
		{
			total_dintr_o_tara=this.getTotalTaraCuTaxe(gestiune.tari.elementAt(i));
			if (total_dintr_o_tara>total/2)
			{
				scutire=10;
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
