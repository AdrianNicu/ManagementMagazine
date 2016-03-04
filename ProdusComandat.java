
public class ProdusComandat
{
	private Produs pr;
	private double taxa;
	private int cantitate;
	
	public void setProdus (Produs pr)
	{
		this.pr=pr;
	}
	public Produs getProdus()
	{
		return this.pr;
	}
	public void setTaxa(double taxa)
	{
		this.taxa=taxa;
	}
	public double getTaxa()
	{
		return this.taxa;
	}
	public void setCantitate(int cantitate)
	{
		this.cantitate=cantitate;
	}
	public int getCantitate()
	{
		return this.cantitate;
	}
}
