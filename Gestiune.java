import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

class C1 implements Comparator<Magazin>
{
	public int compare(Magazin m1,Magazin m2)
	{
		if (m1.getTip().compareTo(m2.getTip())!=0)
			return -m1.getTip().compareTo(m2.getTip());
		if (m1.getTotalFaraTaxe()- m2.getTotalFaraTaxe()>0)
		{
			return 1;
		}
		if (m1.getTotalFaraTaxe()- m2.getTotalFaraTaxe()<0)
		{
			return -1;
		}
		return 0;
	}
}

public class Gestiune
{
	private static Gestiune gestiune=new Gestiune();
	public  ArrayList<Produs> produse=new ArrayList<Produs>();
	public  Vector<String> tari=new Vector<String>();
	public  Vector<String> tarib;
	public  Vector<String> taric=new Vector<String>();
	TreeMap<String,TreeMap<String,Double>> taxe=new TreeMap<String,TreeMap<String,Double>>();
	public  ArrayList<Magazin> magazine=new ArrayList<Magazin>();
	public  ArrayList<String> categorii=new ArrayList<String>();
	
	public Gestiune(){};
	public static Gestiune getInstace()
	{
		return gestiune;
	}
	public void citire(String path1,String path2,String path3) throws IOException
	{
		Scanner s=new Scanner(new FileReader(path1));
		Scanner s2=new Scanner(new FileReader(path2));
		Scanner s3=new Scanner(new FileReader(path3));
		String buf,buf2,categorie,nume="",numeProdus,tip="",tara;
		StringTokenizer st;
		double procent;
		Vector<Factura> facturi=new Vector<Factura>();
		Factura f;
		ProdusComandat pc;
		Produs prod;
		int nr_tara,cantitate;
		TreeMap<String,Double>[] h;
		boolean primulMagazin=true;
		Magazin m;
		
		buf=s.nextLine();
		st=new StringTokenizer(buf," ");
		st.nextToken();st.nextToken();//trecem peste primele 2 cuvinte descriptive din fisier
		while (st.hasMoreTokens())
		{
			buf2=st.nextToken();
			tari.add(buf2);
		}
		while (s.hasNextLine())
		{
			buf=s.nextLine();
			st=new StringTokenizer(buf," ");
			nume=st.nextToken();
			categorie=st.nextToken();
			if (!categorii.contains(categorie))
				categorii.add(categorie);
			nr_tara=0;
			while (st.hasMoreTokens())
			{
				buf2=st.nextToken();
				prod=new Produs();
				prod.setDenumire(nume);
				prod.SetCategorie(categorie);
				prod.setTaraOrigine(tari.elementAt(nr_tara));
				prod.setPret(Double.parseDouble(buf2));
				nr_tara++;
				produse.add(prod);
			}
		}
		s.close();
		
		
		h=new TreeMap[tari.size()];
		for (int i=0;i<tari.size();i++)
		{
			h[i]=new TreeMap<String,Double>();
		}
		buf=s2.nextLine();
		st=new StringTokenizer(buf," ");
		st.nextToken();
		for (int j=0;j<tari.size();j++)
		{
			buf2=st.nextToken();
			taric.add(buf2);
		}
		while (s2.hasNextLine())
		{
			buf=s2.nextLine();
			st=new StringTokenizer(buf," ");
			categorie=st.nextToken();
			for (int i=0;i<taric.size();i++)
			{
				buf2=st.nextToken();
				procent=Double.parseDouble(buf2);
				(h[i]).put(categorie,procent);
			}
		}
		for (int i=0;i<taric.size();i++)
		{
			taxe.put(taric.elementAt(i),h[i]);
		}
		s2.close();
		

		while (s3.hasNextLine())
		{
			buf=s3.nextLine();
			if (buf.contains("Magazin"))
			{
				if (primulMagazin==true)
					primulMagazin=false;
				else
				{
					m=MagazinFactory.createMagazin(tip, nume, facturi);
					magazine.add(m);
				}
				s3.nextLine();
				st=new StringTokenizer(buf,":");
				st.nextToken();
				tip=st.nextToken();
				nume=st.nextToken();
				facturi=new Vector<Factura>();
				
			}
			if (buf.contains("Factura"))
			{
				f=new Factura();
				f.setDenumire(buf);
				s3.nextLine();
				while (s3.hasNextLine())
				{
					buf=s3.nextLine();
					if (buf.equals(""))
						break;
					st=new StringTokenizer(buf," ");
					pc=new ProdusComandat();
					numeProdus=st.nextToken();
					tara=st.nextToken();
					buf2=st.nextToken();
					cantitate=Integer.parseInt(buf2);
					pc.setCantitate(cantitate);
					for (int i=0;i<produse.size();i++)
					{
						if (produse.get(i).getDenumire().equals(numeProdus) && produse.get(i).getTaraOrigine().equals(tara))
						{
							pc.setProdus(produse.get(i));
							break;
						}
					}
					pc.setTaxa( (taxe.get(tara).get(pc.getProdus().getCategorie()))*pc.getProdus().getPret()*pc.getCantitate()/100 );
					f.addProdusComandat(pc);
				}
				facturi.add(f);
			}
		}
		m=MagazinFactory.createMagazin(tip, nume, facturi);
		magazine.add(m);
		
	}
	public void afisare() throws IOException
	{
		BufferedWriter b=new BufferedWriter(new FileWriter("out.txt"));
		tarib=(Vector<String>)tari.clone();
		Collections.sort(magazine,new C1());
		Collections.sort(tari);
		int z=0;
		
		if (z<magazine.size() && magazine.get(z).getTip().equals("MiniMarket"))
		{
			b.write("MiniMarket\n");
		}
		while (z<magazine.size() && magazine.get(z).getTip().equals("MiniMarket"))
		{
			b.write(magazine.get(z).toString()+"\n");
			z++;
		}
		if (z<magazine.size() && magazine.get(z).getTip().equals("MediumMarket"))
		{
			b.write("MediumMarket\n");
		}
		while (z<magazine.size() && magazine.get(z).getTip().equals("MediumMarket"))
		{
			b.write(magazine.get(z).toString()+"\n");
			z++;
		}
		if (z<magazine.size() && magazine.get(z).getTip().equals("HyperMarket"))
		{
			b.write("HyperMarket\n");
		}
		while (z<magazine.size() && magazine.get(z).getTip().equals("HyperMarket"))
		{
			b.write(magazine.get(z).toString()+"\n");
			z++;
		}
		b.close();
	}
	public static void scrieProduse(ArrayList<Produs> pr) 
	{
		try
		{
			BufferedWriter b=new BufferedWriter(new FileWriter("produse.txt"));
			String buf="Produs Categorie";
			boolean gasit;
			for (int i=0;i<gestiune.tarib.size();i++)
			{
				buf+=" "+gestiune.tarib.elementAt(i);
			}
			b.write(buf+"\n");
			int[] v=new int[pr.size()];
			for (int i=0;i<pr.size();i++)
			{
				if (v[i]!=1)
				{
					buf=pr.get(i).getDenumire()+" "+pr.get(i).getCategorie();
					
					for (int q=0;q<gestiune.tarib.size();q++)
					{
						gasit=false;
						for (int j=i;j<pr.size();j++)
						{
							if ( pr.get(j).getDenumire().equals(pr.get(i).getDenumire()) 
									&& pr.get(j).getCategorie().equals(pr.get(i).getCategorie())
									&& pr.get(j).getTaraOrigine().equals(gestiune.tarib.elementAt(q)) )
							{
								buf+=" "+pr.get(j).getPret();
								v[j]=1;
								gasit=true;
							}
						}
						if (!gasit)
							buf+=" 0";
					}
					buf+="\n";
					b.write(buf);
					buf="";
				}
			}
			
			b.close();
		}
		catch (IOException ex){};
	}
	
}
