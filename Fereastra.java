import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.javafx.scene.layout.region.SliceSequenceConverter;


public class Fereastra extends JFrame implements ActionListener
{
	JTextField t1,t2,t3;
	JLabel l1,l2,l3,l,lcontinuare;
	JButton b1,b2,b3,load;
	JPanel p1,p2,p4,p5,p6;
	Gestiune gestiune=Gestiune.getInstace();
	
	public Fereastra(String titlu) throws IOException
	{
		super(titlu);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout(new FlowLayout());
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setSize(680,780);
		center(this);
		setResizable(false);
		getContentPane().setBackground(Color.cyan);
		t1=new JTextField("C:\\Users\\Adrian\\Desktop\\TemaPOO\\produse.txt",30);
		t2=new JTextField("C:\\Users\\Adrian\\Desktop\\TemaPOO\\taxe.txt",30);
		t3=new JTextField("C:\\Users\\Adrian\\Desktop\\TemaPOO\\facturi.txt",30);
		l=new JLabel("Introduceti calea catre fisierele cautate sau apasati butonul");
		lcontinuare=new JLabel(" Browse pentru a le selecta din ierarhia de fisiere");
		l.setForeground(new Color(139,71,137));
		lcontinuare.setForeground(new Color(139,71,137));
		Font font=new Font("Serif",Font.BOLD,19);
		l.setFont(font);lcontinuare.setFont(font);
		l.setBorder(new EmptyBorder(100,0,10,0));lcontinuare.setBorder(new EmptyBorder(10,80,10,120));
		add(l);add(lcontinuare);
		l1=new JLabel("Selectati fisierul produse.txt");l1.setForeground(new Color(139,71,137));
		l1.setBorder(new EmptyBorder(20,0,35,0));
		l2=new JLabel("Selectati fisierul taxe.txt");l2.setForeground(new Color(139,71,137));
		l3=new JLabel("Selectati fisierul facturi.txt");l3.setForeground(new Color(139,71,137));
		b1=new JButton("Browse");
		b2=new JButton("Browse");
		b3=new JButton("Browse");
		p1=new JPanel();
		p2=new JPanel();
		
		p4=new JPanel();p4.add(t1);p4.setBorder(new EmptyBorder(20,0,20,0));p4.setBackground(Color.cyan);
		p5=new JPanel();p5.add(t2);p5.setBorder(new EmptyBorder(20,0,20,0));p5.setBackground(Color.cyan);
		p6=new JPanel();p6.add(t3);p6.setBorder(new EmptyBorder(20,0,20,0));p6.setBackground(Color.cyan);
		p1.add(l1);p1.add(l2);p1.add(l3);p1.setLayout(new GridLayout(0,1));p1.setBackground(Color.cyan);
		p2.add(p4);p2.add(p5);p2.add(p6);p2.setLayout(new GridLayout(0,1));
		p2.setBackground(Color.cyan);
		load=new JButton("Incarca");
		p4.add(b1);p5.add(b2);p6.add(b3);
		add(p1);add(p2);
		
		JPanel picturePanel = new JPanel() {
			private static final long serialVersionUID=46L;
			String path=System.getProperty("user.dir")+"/sales_productivity.png";
		    private Image img = ImageIO.read(new File(path));
		    @Override
		    public void paintComponent(Graphics g) { 
		        super.paintComponent(g);

		        g.drawImage(img, 0,0,250,300, this);
		    }
		};
		picturePanel.setPreferredSize(new Dimension(250,300));
		add(picturePanel);
		add(load);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		load.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fc=new JFileChooser("C:\\Users\\Adrian\\Desktop\\TemaPOO");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
		fc.setFileFilter(filter);
		int returnValue;
		if (e.getSource().equals(load))
		{
			try{
			String path1, path2,path3;
			path1=t1.getText();
			path2=t2.getText();
			path3=t3.getText();
			
			gestiune.citire(path1,path2,path3);
			gestiune.afisare();
			JOptionPane.showMessageDialog(this,"Fisierul out.txt a fost generat cu succes!");
			Administrare a=new Administrare("Administrare produse");
			a.setVisible(true);
			dispose();
			}
			catch (IOException ex){JOptionPane.showMessageDialog(this,"Incarcarea nu a fost reusita.Reincercati!");};
		}
		if (e.getSource().equals(b1))
		{
			returnValue=fc.showOpenDialog(this);
			if (returnValue==JFileChooser.APPROVE_OPTION)
			{
				t1.setText(fc.getSelectedFile().getAbsolutePath());
			}
		}
		if (e.getSource().equals(b2))
		{
			returnValue=fc.showOpenDialog(this);
			if (returnValue==JFileChooser.APPROVE_OPTION)
			{
				t2.setText(fc.getSelectedFile().getAbsolutePath());
			}
		}
		if (e.getSource().equals(b3))
		{
			returnValue=fc.showOpenDialog(this);
			if (returnValue==JFileChooser.APPROVE_OPTION)
			{
				t3.setText(fc.getSelectedFile().getAbsolutePath());
			}
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
