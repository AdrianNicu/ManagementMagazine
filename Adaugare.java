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


public class Adaugare extends JDialog implements ActionListener
{
	JTextField t1,t4;
	JLabel l1,l2,l3,l4,l;
	JButton ok;
	JPanel p1,p2;
	MyTableModel tm;
	private JComboBox cb1;
	private JComboBox cb2;
	Gestiune gestiune=Gestiune.getInstace();
	
	public Adaugare(JFrame parinte,MyTableModel mtm)
	{
		super(parinte);
		this.tm=mtm;
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		setSize(540,291);
		this.setModal(true);
		
		Font font=new Font("Serif",Font.BOLD,19);
		l=new JLabel("Introduceti datele noului produs:");
		l.setFont(font);
		l.setForeground(Color.cyan);
		l.setBorder(new EmptyBorder(30,150,0,150));
		l1=new JLabel("Denumire:");
		l1.setBorder(new EmptyBorder(3,0,4,0));
		l1.setForeground(Color.cyan);
		l2=new JLabel("Pret Produs  :");
		l2.setBorder(new EmptyBorder(0,0,4,0));
		l2.setForeground(Color.cyan);
		l3=new JLabel("Tara de origine:");
		l3.setBorder(new EmptyBorder(0,0,4,0));
		l3.setForeground(Color.cyan);
		l4=new JLabel("Categorie:");
		l4.setForeground(Color.cyan);
		t1=new JTextField(20);
		t1.setFont(new Font("Serif",Font.BOLD,13));
		t1.setBackground(Color.cyan);
		t1.setForeground(new Color(170,71,180));
		t4=new JTextField(20);
		t4.setFont(new Font("Serif",Font.BOLD,13));
		t4.setBackground(Color.cyan);
		t4.setForeground(new Color(170,71,180));
		ok=new JButton("Adauga");
		ok.addActionListener(this);
		p1=new JPanel();
		p2=new JPanel();
		p1.add(l1);p1.add(l2);p1.add(l3);p1.add(l4);
		p1.setPreferredSize(new Dimension(100,100));
		p2.setPreferredSize(new Dimension(220,120));
		p2.add(t1);
		p2.add(t4);
		getContentPane().add(l);getContentPane().add(p1);getContentPane().add(p2);getContentPane().add(ok);
		getContentPane().setBackground(new Color(170,71,180));
		p1.setBackground(new Color(170,71,180));
		p2.setBackground(new Color(170,71,180));
		
		cb1 = new JComboBox();
		cb1.setPreferredSize(new Dimension(100,20));
		for (int j=0;j<gestiune.tarib.size();j++)
		{
			cb1.addItem(gestiune.tarib.elementAt(j));
		}
		cb1.setPreferredSize(t1.getPreferredSize());
		p2.add(cb1);
		
		cb2 = new JComboBox();
		for (int j=0;j<gestiune.categorii.size();j++)
		{
			cb2.addItem(gestiune.categorii.get(j));
		}
		cb2.setPreferredSize(t1.getPreferredSize());
		p2.add(cb2);
		center(this);
		
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		Produs prod=new Produs();
		boolean exista=false;
		prod.setDenumire(t1.getText());
		prod.SetCategorie((String)cb2.getSelectedItem());
		prod.setTaraOrigine((String)cb1.getSelectedItem());
		prod.setPret(Double.parseDouble(t4.getText()));
		for (int i=0;i<gestiune.produse.size();i++)
		{
			if ( gestiune.produse.get(i).getDenumire().equals(prod.getDenumire()) &&
				gestiune.produse.get(i).getCategorie().equals(prod.getCategorie()) &&
				gestiune.produse.get(i).getTaraOrigine().equals(prod.getTaraOrigine()) )
			{
				exista=true;
			}
		}
		if (!exista)
		{
			gestiune.produse.add(prod);
			tm.setData(gestiune.produse);
			tm.fireTableChanged(new TableModelEvent(tm));
			gestiune.scrieProduse(gestiune.produse);
			dispose();
		}
		else
		{
			JOptionPane.showMessageDialog(this,"Produsul deja exista!");
		}
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
