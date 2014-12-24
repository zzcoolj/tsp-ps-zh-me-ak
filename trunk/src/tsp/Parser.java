package tsp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;



import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;




public class Parser {

	
	private String urlXML,urlTsp;

	public Parser(String urlXML, String urlTsp) {
		
		this.urlXML = urlXML;
		this.urlTsp = urlTsp;

	}
	
	public void parse(Graph g) {
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty("javax.xml.stream.isValidating", Boolean.FALSE);
		
		File file = new File(urlXML);
		
		
		//LinkedHashMap<Vertex, ArrayList<Double>> hash = new LinkedHashMap<Vertex, ArrayList<Double>>();
		

		try {
			XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(file));
			
			String vertexS = "vertex";
			String edgeS = "edge";
						
			boolean state = true;
			int cptVilles = 0;
			int cptEdge = 0;
			ArrayList<Vertex> mesVilles = new ArrayList<Vertex>();
			
			ArrayList<Double> values = new ArrayList<Double>();
			
			while (reader.hasNext() && state) 
			{
				
				
		        int type = reader.next();
		        
		        switch (type) {
		        case XMLStreamReader.START_ELEMENT:
		            // Si c'est un début de balise user, alors on lance le traitement d'un utilisateur
		            if (vertexS.equals(reader.getLocalName()))
		            {
		            	mesVilles.add(new Vertex(cptVilles));
		            }
		            else if (edgeS.equals(reader.getLocalName()))
		            {
		            	//System.out.println("reader.getAttributeValue(cptEdge)) = "+reader.getAttributeValue(0));
		            	if(cptEdge==cptVilles)
		            	{
		            		values.add(Double.MAX_VALUE);
		            		values.add(Double.valueOf(reader.getAttributeValue(0)));
		            	}	
		            	else
		            	{
		            		values.add(Double.valueOf(reader.getAttributeValue(0)));
		            	}
		            }
		            	
		            break;
		            
		            
		        case XMLStreamReader.END_ELEMENT:
	                // Si c'est la fin de la balise user, on change l'indicateur de boucle
	                if (reader.getLocalName().equals(vertexS))
	                {
	                	//Ajout à la liste de vertex à la hashmap
	                	
	                	
	                	//System.out.println("Values +///// "+values);
	                	
	                	d.getCouts().put(new Vertex(cptVilles), values);
	                	
	                	values = new ArrayList<Double>();
	                	//System.out.println("cptvilles = "+cptVilles);
	                	cptVilles++;
	                	cptEdge = 0;
	                }
	                else if(reader.getLocalName().equals("graph"))
	                {
	                	state = false;
	                	d.getCouts().get(new Vertex(cptVilles-1)).add(Double.MAX_VALUE);
	                }
	                else if(reader.getLocalName().equals("edge"))
	                {
	                	cptEdge++;
	                }
	                	
	                break;
		    }
		        
		        // traitements
		    }
			g.setCities(mesVilles);
			System.out.println("Fin parsing");
			
			
			/*for(Entry<Vertex,ArrayList<Double>> entry : hash.entrySet())
			{
				System.out.println("Vertex = "+entry.getKey());
				System.out.println(entry.getValue());
			}*/
			
		} catch (FileNotFoundException | XMLStreamException e) {
			// TODO Auto-generated catch block
			System.out.println("Veuillez charger un autre fichier : "+urlXML+" introuvable ou inexistant ou incorrecte");
			System.exit(2);
		}
		
		
		
	}
	
	/*private static String getChildElementContent(Element e, String childName) {
	    NodeList children = e.getElementsByTagName(childName);
	    if(children.getLength() > 0) {
	        return children.item(0).getAttributes().getNamedItem("cost").getNodeValue();
	    }
	    return "";
	}*/


}
