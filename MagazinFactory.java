import java.util.Vector;


public class MagazinFactory
{
	public static Magazin createMagazin(String tip, String nume,Vector<Factura> facturi)
	{
		Magazin M;
		if (tip.equals("MiniMarket"))
		{
			M=new MiniMarket();
			M.setFacturi(facturi);
			M.setDenumireMagazin(nume);
			return M;
		}
		if (tip.equals("MediumMarket"))
		{
			M=new MediumMarket();
			M.setFacturi(facturi);
			M.setDenumireMagazin(nume);
			return M;
		}
		if (tip.equals("HyperMarket"))
		{
			M=new HyperMarket();
			M.setFacturi(facturi);
			M.setDenumireMagazin(nume);
			return M;
		}
		return null;	
	}
	
}
