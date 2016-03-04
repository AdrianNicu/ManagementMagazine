import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.JComboBox;


public class Cautare extends JDialog implements ActionListener
{
	JTextField t1;
	JLabel l1,l2,l3,l;
	JButton ok;
	JPanel p1,p2;
	Gestiune gestiune=Gestiune.getInstace();
	private JComboBox<String> cb1;
	private JComboBox<String> cb2;
	
	public Cautare(JFrame parinte)
	{
		super(parinte);
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		setSize(500,250);
		this.setModal(true);
		
		Font font=new Font("Serif",Font.BOLD,19);
		l=new JLabel("Introduceti datele produsului cautat:");
		l.setFont(font);
		l.setForeground(Color.cyan);
		l.setBorder(new EmptyBorder(30,150,0,150));
		l1=new JLabel("Denumire:");
		l1.setBorder(new EmptyBorder(3,0,4,0));
		l1.setForeground(Color.cyan);
		l2=new JLabel("Categorie:");
		l2.setBorder(new EmptyBorder(0,0,4,0));
		l2.setForeground(Color.cyan);
		l3=new JLabel("Tara de origine:");
		l3.setBorder(new EmptyBorder(0,0,4,0));
		l3.setForeground(Color.cyan);
		
		t1=new JTextField(20);
		t1.setFont(new Font("Serif",Font.BOLD,13));
		t1.setBackground(Color.cyan);
		t1.setForeground(new Color(170,71,180));
		
		ok=new JButton("Cauta");
		ok.addActionListener(this);
		p1=new JPanel();
		p2=new JPanel();
		p1.add(l1);p1.add(l2);p1.add(l3);
		p1.setPreferredSize(new Dimension(100,100));
		p2.setPreferredSize(new Dimension(220,120));
		p2.add(t1);
		getContentPane().add(l);getContentPane().add(p1);getContentPane().add(p2);getContentPane().add(ok);
		getContentPane().setBackground(new Color(170,71,180));
		p1.setBackground(new Color(170,71,180));
		p2.setBackground(new Color(170,71,180));
		
		cb1 = new JComboBox<String>();
		cb1.setPreferredSize(t1.getPreferredSize());
		for (int j=0;j<gestiune.categorii.size();j++)
		{
			cb1.addItem(gestiune.categorii.get(j));
		}
		
		p2.add(cb1);
		
		cb2 = new JComboBox<String>();
		cb2.setPreferredSize(t1.getPreferredSize());
		for (int j=0;j<gestiune.tarib.size();j++)
		{
			cb2.addItem(gestiune.tarib.elementAt(j));
		}
		p2.add(cb2);
		center(this);
		
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String nume,tara,categorie,s="Produsul poate fi gasit in urmatoarele magazine:";
		nume=t1.getText();
		categorie=(String)cb1.getSelectedItem();
		tara=(String)cb2.getSelectedItem();
		for (int j=0;j<gestiune.magazine.size();j++)
		{
			for (int q=0;q<gestiune.magazine.get(j).getFacturi().size();q++)
			{
				for (int k=0;k<gestiune.magazine.get(j).getFacturi().elementAt(q).getProduseComandate().size();k++)
				{
					if ( gestiune.magazine.get(j).getFacturi().elementAt(q).getProduseComandate().get(k).getProdus().getDenumire().equals(nume) &&
							gestiune.magazine.get(j).getFacturi().elementAt(q).getProduseComandate().get(k).getProdus().getCategorie().equals(categorie)&&
							gestiune.magazine.get(j).getFacturi().elementAt(q).getProduseComandate().get(k).getProdus().getTaraOrigine().equals(tara) )
					{
						if ( !s.contains(gestiune.magazine.get(j).getDenumireMagazin()) )
							s+=" "+gestiune.magazine.get(j).getDenumireMagazin();
					}
				}
			}
		}
		if (s.equals("Produsul poate fi gasit in urmatoarele magazine:"))
		{
			JOptionPane.showMessageDialog(this,"Produsul nu a fost gasit!");
		}
		else
		{
			JOptionPane.showMessageDialog(this,s);
		}
		dispose();
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
