import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class Administrare extends JFrame implements TableModelListener, ActionListener, ListSelectionListener
{
	private static final long serialVersionUID=47L;
	JTable t;
	MyTableModel tm;
	JScrollPane sp;
	JButton ord1,ord2,cautare,addButon,sterge,cauta,paginaUrm;
	int liniaSelectata=0;
	boolean s_aStersLinia=true;
	ListSelectionModel lsm;
	JTextArea ta;
	JPanel panelButoane;
	Gestiune gestiune=Gestiune.getInstace();
	
	public Administrare(String titlu) throws IOException
	{
		super(titlu);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout(new FlowLayout());
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setSize(780,790);
		center(this);
		setResizable(false);
		getContentPane().setBackground(Color.cyan);
		ord1=new JButton("Ordoneaza dupa denumire");
		ord2=new JButton("Ordoneaza dupa tara");
		ord2.setPreferredSize(ord1.getPreferredSize());
		addButon=new JButton("Adauga produs");
		addButon.setPreferredSize(ord1.getPreferredSize());
		sterge=new JButton("Sterge produs");
		sterge.setPreferredSize(ord1.getPreferredSize());
		cauta=new JButton("Cauta produs");
		cauta.setPreferredSize(ord1.getPreferredSize());
		ord1.addActionListener(this);
		ord2.addActionListener(this);
		addButon.addActionListener(this);
		sterge.addActionListener(this);
		cauta.addActionListener(this);
		paginaUrm=new JButton("Pagina urmatoare");
		paginaUrm.addActionListener(this);
		paginaUrm.setPreferredSize(ord1.getPreferredSize());
		ta=new JTextArea("                  Pentru a edita produsele modificati celulele tabelului."
				+ "\nPentru a sterge un produs selectati o linie a tabelului si apasati butonul de stergere.");
		Font font=new Font("Serif",Font.BOLD,19);
		ta.setFont(font);
		ta.setForeground(new Color(170,71,180));
		ta.setBackground(Color.cyan);
		ta.setEditable(false);
		ta.setBorder(new EmptyBorder(40,0,30,0));
		panelButoane=new JPanel();
		panelButoane.add(ord1);panelButoane.add(ord2);panelButoane.add(addButon);panelButoane.add(cauta);panelButoane.add(sterge);panelButoane.add(paginaUrm);
		panelButoane.setPreferredSize(new Dimension(200,200));
		panelButoane.setBackground(Color.cyan);
		tm=new MyTableModel();
		tm.setData(gestiune.produse);
		tm.addTableModelListener(this);
		t=new JTable(tm);
		t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsm=t.getSelectionModel();
		lsm.addListSelectionListener(this);
		sp=new JScrollPane(t);
		sp.setPreferredSize(new Dimension(500,270));
		
		JPanel picturePanel = new JPanel() {
			private static final long serialVersionUID=46L;
			String path=System.getProperty("user.dir")+"/pic3.jpg";
		    private Image img = ImageIO.read(new File(path));
		    @Override
		    public void paintComponent(Graphics g) { 
		        super.paintComponent(g);

		        g.drawImage(img, 0,0,780,350, this);
		    }
		};
		picturePanel.setPreferredSize(new Dimension(780,350));
		
		add(picturePanel);add(ta);add(sp);add(panelButoane);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(ord1))
		{
			Collections.sort(gestiune.produse,new ComparatorNume());
			tm.setData(gestiune.produse);
			tm.fireTableChanged(new TableModelEvent(tm));
		}
		if (e.getSource().equals(ord2))
		{
			Collections.sort(gestiune.produse,new ComparatorTara());
			tm.setData(gestiune.produse);
			tm.fireTableChanged(new TableModelEvent(tm));
		}
		if (e.getSource().equals(addButon))
		{
			Adaugare adaugare=new Adaugare(this,tm);
		}
		if (e.getSource().equals(sterge) && s_aStersLinia==false)
		{
			for (int i=0;i<gestiune.produse.size();i++)
			{
				if ( gestiune.produse.get(i).getDenumire().equals(tm.getValueAt(liniaSelectata, 0)) &&
						gestiune.produse.get(i).getCategorie().equals(tm.getValueAt(liniaSelectata, 1)) &&
						gestiune.produse.get(i).getTaraOrigine().equals(tm.getValueAt(liniaSelectata, 2)) )
				{
					gestiune.produse.remove(i);
					tm.setData(gestiune.produse);
					tm.fireTableChanged(new TableModelEvent(tm));
					s_aStersLinia=true;
					return;
				}
			}
		}
		if ( e.getSource().equals(cauta) )
		{
			Cautare cautare=new Cautare(this);
		}
		if ( e.getSource().equals(paginaUrm) )
		{
			try{
				Statistici statistici=new Statistici("Statistici");
				statistici.setVisible(true);
				dispose();
			}catch(IOException ex){System.out.println("Exceptie la gasirea pozei");}
		}
	}
	public void valueChanged(ListSelectionEvent e)
	{
		if (e.getValueIsAdjusting()) return;
		ListSelectionModel model= (ListSelectionModel)e.getSource();
		liniaSelectata=model.getMinSelectionIndex();
		s_aStersLinia=false;
		
	}
	public void tableChanged(TableModelEvent e)
	{
		int row=e.getFirstRow();
		int col=e.getColumn();
		if (col==3 && !((MyTableModel)(e.getSource())).ok)
		{
			((MyTableModel)(e.getSource())).ok=true;
			int op=JOptionPane.showConfirmDialog(this, "Sunteti sigur ca doriti sa editati pretul produsului ?");
			if ( (op==JOptionPane.CANCEL_OPTION || op==JOptionPane.NO_OPTION) )
			{
				
				((MyTableModel)(e.getSource())).setValueAt(((MyTableModel)(e.getSource())).lastModified, row, col);
				
			}
			else
			{
				((MyTableModel)(e.getSource())).ok=false;
				gestiune.produse=((MyTableModel)(e.getSource())).getData();
				gestiune.scrieProduse(gestiune.produse);
				return;
			}
		}
		else
		{
			
			((MyTableModel)(e.getSource())).ok=false;
			
		}
		gestiune.produse=((MyTableModel)(e.getSource())).getData();
		gestiune.scrieProduse(gestiune.produse);
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
class MyTableModel extends AbstractTableModel
{
	private static final long serialVersionUID=42L;
	private final String[] dataColumns = { "Nume", "Categorie", "Tara Origine", "Pret" };
	private Object[][] data;
	Object lastModified="blablablablabla";
	public boolean ok=false;
	int rows,collumns;
	
	 public MyTableModel()
	 {
		 rows=0;
		 collumns=4;
	 }
	 public void setData(ArrayList<Produs> pr)
	 {
		 rows=pr.size();
		 data=new Object[rows][collumns];
		 for (int i=0;i<pr.size();i++)
		 {
			 data[i][0]=pr.get(i).getDenumire();
			 data[i][1]=pr.get(i).getCategorie();
			 data[i][2]=pr.get(i).getTaraOrigine();
			 data[i][3]=pr.get(i).getPret(); 
		 }
	 }
	 public ArrayList<Produs> getData()
	 {
		 ArrayList<Produs> list=new ArrayList<Produs>();
		 Produs p;
		 for (int i=0;i<data.length;i++)
		 {
			 p=new Produs();
			 p.setDenumire((String)data[i][0]);
			 p.SetCategorie((String)data[i][1]);
			 p.setTaraOrigine((String)data[i][2]);
			 p.setPret((double)data[i][3]);
			 list.add(p);
		 }
		 return list;
	 }
	 @Override
	   	public String getColumnName(int column)
	 	{
	        return dataColumns[column];
	    }

	    @Override
	    public int getRowCount() 
	    {
	        return data.length;
	    }

	    @Override
	    public int getColumnCount() 
	    {
	        return dataColumns.length;
	    }

	    @Override
	    public Object getValueAt(int rowIndex, int columnIndex)
	    {
	        return data[rowIndex][columnIndex];
	    }
	    @Override
	    public void setValueAt(Object value,int rowIndex, int columnIndex)
	    {
	    	if (columnIndex==3)
	    	{
	    		lastModified= data[rowIndex][columnIndex];
		    	data[rowIndex][columnIndex]=Double.parseDouble((String)value);
		    	fireTableCellUpdated(rowIndex, columnIndex);
	    	}
	    	else
	    	{
	    		Object aux=data[rowIndex][columnIndex];
	    		data[rowIndex][columnIndex]=value;
	    		for (int i=0;i<data.length;i++)
	    		{
	    			if (i!=rowIndex && data[i][0].equals(data[rowIndex][0]) && data[i][1].equals(data[rowIndex][1])
	    				&& data[i][2].equals(data[rowIndex][2]))
	    			{
	    				JOptionPane.showMessageDialog(null, "Produsul deja exista");
	    				data[rowIndex][columnIndex]=aux;
	    				return;
	    			}
	    		}
	    		fireTableCellUpdated(rowIndex, columnIndex);
	    	}
	    	
	    	
	    }
	    @Override
	    public boolean isCellEditable(int row, int column) 
	    {
	    	if (row==0)
	    		return false;
	    	return true;
	    }
}
class ComparatorNume implements Comparator<Produs>
{
	public int compare(Produs p1,Produs p2)
	{
		return p1.getDenumire().compareTo(p2.getDenumire());
	}
}
class ComparatorTara implements Comparator<Produs>
{
	public int compare(Produs p1,Produs p2)
	{
		return p1.getTaraOrigine().compareTo(p2.getTaraOrigine());
	}
}