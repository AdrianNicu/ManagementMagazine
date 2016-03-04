import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Logare extends JFrame implements ActionListener
{
	private static final long serialVersionUID=45L;
	Image image;
	JButton ok=new JButton("Autentifica");
	JLabel l1=new JLabel("Nume utilizator: ");
	JLabel l2=new JLabel("Parola: ");
	JLabel l3=new JLabel("Introduceti numele de utilizator si parola dumneavoastra: ");
	JTextField t1=new JTextField(20);
	JPasswordField t2=new JPasswordField(20);
	public Logare(String titlu) throws IOException
	{
		super(titlu);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout(new FlowLayout());
		setSize(680,680);
		center(this);
		
		
		
		l3.setBorder(new EmptyBorder(10,10,10,10));
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JPanel p3=new JPanel();
		JPanel picturePanel = new JPanel() {
			private static final long serialVersionUID=46L;
			String path=System.getProperty("user.dir")+"/pic1.jpg";
		    private Image img = ImageIO.read(new File(path));
		    @Override
		    public void paintComponent(Graphics g) { 
		        super.paintComponent(g);

		        g.drawImage(img, 0,0,680,300, this);
		    }
		};
		picturePanel.setPreferredSize(new Dimension(680,300));
		add(picturePanel);
		setSize(680,580);
		picturePanel.setIgnoreRepaint(true);
		l3.setForeground(new Color(170,71,180));add(l3);
		l3.setMinimumSize(new Dimension(15,20));
		setResizable(false);
		getContentPane().setBackground(Color.cyan);
		add(l3);
		l2.setBorder(new EmptyBorder(0,0,0,50));
		p1.setBorder(new EmptyBorder(10,30,10,10));
		p2.setBorder(new EmptyBorder(10,30,10,10));
		p1.add(l1);p1.add(t1);
		p2.add(l2);p2.add(t2);
		add(p1);add(p2);
		p3.setBorder(new EmptyBorder(10,150,10,150));
		p3.setBackground(Color.cyan);
		ok.addActionListener(this);
		p3.add(ok);add(p3);
		
		
		setVisible(true);
		
	}
	public static void center (Window w)
	{
		Dimension ws = w.getSize();
		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int newX = ( ss.width - ws.width ) / 2;
		int newY = ( ss.height- ws.height ) / 2;
		w.setLocation( newX, newY );
	}
	public static void centerComponent(Window w,Component c)
	{
		Dimension ws = c.getSize();
		Dimension ss = w.getSize();
		int newX = ( ss.width - ws.width ) / 2;
		int newY = ( ss.height- ws.height ) / 2;
		c.setLocation( newX, newY );
	}
	public void actionPerformed(ActionEvent e) 
	{
		String pass,user,buf;
		String s1=t1.getText();
		String s2=t2.getText();
		boolean k=false;
		Scanner s;
		try{
			StringTokenizer st;
			s=new Scanner(new FileReader("users.txt"));
			while (s.hasNext())
			{
				buf=s.nextLine();
				st=new StringTokenizer(buf," ");
				user=st.nextToken();
				pass=st.nextToken();
				if (user.equals(s1) && pass.equals(s2))
				{
					k=true;
					break;
				}
			}
			if (k==true)
			{
				Fereastra f=new Fereastra("Pagina de incarcare fisiere");
				f.setVisible(true);
				dispose();
			}
		}
		catch (IOException ex){System.out.println("Exceptie IO");};
		
		
	}
}
