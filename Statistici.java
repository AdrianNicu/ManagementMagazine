import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Statistici extends JFrame
{
	JTextArea ta;
	JScrollPane scrl;
	static DecimalFormat df=new DecimalFormat("#.###");
	Gestiune gestiune=Gestiune.getInstace();
	
	public Statistici(String titlu) throws IOException
	{
		super(titlu);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout(new FlowLayout());
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setSize(780,790);
		center(this);
		setResizable(false);
		getContentPane().setBackground(Color.cyan);
		ta=new JTextArea();
		Font font=new Font("Serif",Font.BOLD,19);
		ta.setFont(font);
		ta.setForeground(new Color(170,71,180));
		ta.setBackground(Color.cyan);
		
		
		
		double max=gestiune.magazine.get(0).getTotalCuTaxe();
		String buf="Magazinul cu cele mai mari vanzari(total cu taxe):\n     ";
		Magazin mag=gestiune.magazine.get(0);
		for (int i=0;i<gestiune.magazine.size();i++)
		{
			if (gestiune.magazine.get(i).getTotalCuTaxe()> max)
			{
				mag=gestiune.magazine.get(i);
				max=gestiune.magazine.get(i).getTotalCuTaxe();
			}
		}
		buf+=mag.getDenumireMagazin()+" "+df.format(mag.getTotalFaraTaxe())+" "+df.format(mag.getTotalCuTaxe())+" "+df.format(mag.getTotalCuTaxeScutite());
		buf+="\n\nMagazinele cu cele mai mari vanzari pentru fiecare tara:";
		for (int j=0;j<gestiune.tarib.size();j++)
		{
			max=gestiune.magazine.get(0).getTotalTaraCuTaxe( gestiune.tarib.elementAt(j) );
			mag=gestiune.magazine.get(0);
			for (int q=0;q<gestiune.magazine.size();q++)
			{
				if (gestiune.magazine.get(q).getTotalTaraCuTaxe( gestiune.tarib.elementAt(j) )  > max)
				{
					mag=gestiune.magazine.get(q);
					max=gestiune.magazine.get(q).getTotalTaraCuTaxe( gestiune.tarib.elementAt(j) );
				}
			}
			buf+="\n     "+gestiune.tarib.elementAt(j)+": "+mag.getDenumireMagazin()+" "+df.format(mag.getTotalFaraTaxe())+" "+
					df.format(mag.getTotalCuTaxe())+" "+df.format(mag.getTotalCuTaxeScutite());
		}
		
		buf+="\n\nMagazinele cu cele mai mari vanzari pe categorii:";
		mag=gestiune.magazine.get(0);
		
		for (int j=0;j<gestiune.categorii.size();j++)
		{
			max=gestiune.magazine.get(0).getTotalCategorieCuTaxe( gestiune.categorii.get(j) );
			mag=gestiune.magazine.get(0);
			for (int q=1;q<gestiune.magazine.size();q++)
			{
				if (gestiune.magazine.get(q).getTotalCategorieCuTaxe( gestiune.categorii.get(j) )>max)
				{
					mag=gestiune.magazine.get(q);
					max=gestiune.magazine.get(q).getTotalCategorieCuTaxe( gestiune.categorii.get(j) );
				}
			}
			buf+="\n     "+gestiune.categorii.get(j)+": "+mag.getDenumireMagazin()+" "+df.format(mag.getTotalFaraTaxe())+
					" "+df.format(mag.getTotalCuTaxe())+" "+
					df.format(mag.getTotalCuTaxeScutite());
		}		
		
		Factura fact=gestiune.magazine.get(0).facturi.get(0);
		mag=gestiune.magazine.get(0);
		max=gestiune.magazine.get(0).facturi.get(0).getTotalFaraTaxe();
		for (int j=0;j<gestiune.magazine.size();j++)
		{
			for (int q=0;q<gestiune.magazine.get(j).facturi.size();q++)
			{
				if (gestiune.magazine.get(j).facturi.get(q).getTotalFaraTaxe()> max)
				{
					max=gestiune.magazine.get(j).facturi.get(q).getTotalFaraTaxe();
					fact=gestiune.magazine.get(j).facturi.get(q);
					mag=gestiune.magazine.get(j);
				}
			}
		}
		buf+="\n\nFactura cu suma totala cea mai mare (fara taxe) apartine magazinului "+mag.getDenumireMagazin()+"\n"+
				fact.toString();
		ta.setText(buf);
		ta.setEditable(false);
		scrl=new JScrollPane(ta);
		scrl.setPreferredSize(new Dimension(700,500));
		
		
		JPanel picturePanel = new JPanel() {
			private static final long serialVersionUID=46L;
			String path=System.getProperty("user.dir")+"/statistics2.jpg";
		    private Image img = ImageIO.read(new File(path));
		    @Override
		    public void paintComponent(Graphics g) { 
		        super.paintComponent(g);

		        g.drawImage(img, 0,0,700,270, this);
		    }
		};
		picturePanel.setPreferredSize(new Dimension(700,250));
		add(picturePanel);
		add(scrl);
		
	}
	public static void center (Window w)
	{
		Dimension ws = w.getSize();
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int newX = ( ss.width - ws.width ) / 2;
		int newY = ( ss.height- ws.height ) / 2;
		w.setLocation( newX, newY );
	}
}
