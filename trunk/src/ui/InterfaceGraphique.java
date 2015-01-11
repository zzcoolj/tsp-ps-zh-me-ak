package ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

import CustomClass.PaireVertex;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import tsp.Graph;
import tsp.Vertex;

public class InterfaceGraphique extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//TODO width height sont un peu plus grands que JPanel
	private int width, height;
	private int tailleVille;
	private int distanceX;
	private int distanceY;
	
	Object[] vertexsAller;
	Object[] vertexsRetour;
	int nbV;
	mxGraph graph;
	//Double[][] couts;//for test
	LinkedHashMap<PaireVertex, Double> couts;
	
	ArrayList<Forme> formes;

	public InterfaceGraphique(int width, int height) {
		this.width = width;
		this.height = height;
		this.tailleVille = width/100;
		this.distanceX = tailleVille/2;
		this.distanceY = distanceY/2+2;// if faut distanceX>distanceY	
	}

	/*
	public void draw(Graph g) {
		nbV = g.getNbVilles();
		couts = g.toTab();
		jgraphT(nbV, couts);
	}
	*/
	public void draw(Graph g) {
		nbV = g.getNbVilles();
		couts = g.getCouts();
		jgraphT(nbV, couts);
	}
	
	public void drawStream(Graph g) {
		draw(g);
	}

	private void jgraphT(int nbV, LinkedHashMap<PaireVertex, Double> couts) {
		
		graph = new mxGraph();
		//graph.addListener(mxEvent., function(sender, evt) {graph.zoomIn(); scrollTo(graph, evt.properties.event.layerX, evt.properties.event.layerY);});
		
		
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();

		Random rX = new Random();
		Random rY = new Random(10);

		try {

			// dessiner les sommets
			vertexsAller = new Object[nbV];//for test
			vertexsRetour = new Object[nbV];//for test
			for (int i = 0; i < nbV; i++) {
				int posX = rX.nextInt(width-2*tailleVille);
				int posY = rY.nextInt(height-2*tailleVille);
				/* regardez graphe<<vertex>> pour expliquer pourquoi tailleVille+2*distance */
				vertexsAller[i] = graph.insertVertex(parent, null, i, posX, posY, tailleVille+2*distanceX, tailleVille+2*distanceX, "fillColor=#34495e");
				vertexsRetour[i] = graph.insertVertex(parent, null, "", posX+distanceX, posY+distanceY, tailleVille, tailleVille, "fillColor=#34495e");
			}
			
			if (nbV > 50) {

			} else {
				// dessiner les chemins
				/*
				 * regardez graphe<<table>> pour expliquer pourquoi
				 * nbV*(nbV-1)/2
				 */

				Object[] edgesAller = new Object[nbV * (nbV - 1) / 2];
				int num = 0;
				for (int i = 0; i < nbV; i++) {
					for (int j = 0; j < nbV; j++) {
						if (i < j) {
							/*
							edgesAller[num] = graph.insertEdge(parent, null,
									couts[i][j], vertexsAller[i],
									vertexsAller[j], "strokeColor=#34495e");
									*/
							edgesAller[num] = graph.insertEdge(parent, null,
									couts.get(new PaireVertex(new Vertex(i), new Vertex(j))), vertexsAller[i],
									vertexsAller[j], "strokeColor=#34495e");
							num++;
						}
					}
				}

				Object[] edgesRetour = new Object[nbV * (nbV - 1) / 2];
				num = 0;
				for (int i = 0; i < nbV; i++) {
					for (int j = 0; j < nbV; j++) {
						if (i > j) {
							/*
							if (!(couts[i][j].equals(couts[j][i]))) {
							*/
							if (!(couts.get(new PaireVertex(new Vertex(i), new Vertex(j))).equals(couts.get(new PaireVertex(new Vertex(j), new Vertex(i)))))) {
								/*
								edgesRetour[num] = graph
										.insertEdge(parent, null, couts[i][j],
												vertexsRetour[i],
												vertexsRetour[j],
												"strokeColor=#5e6035");// change couleur
												*/
								edgesRetour[num] = graph
										.insertEdge(parent, null, couts.get(new PaireVertex(new Vertex(i), new Vertex(j))),
												vertexsRetour[i],
												vertexsRetour[j],
												"strokeColor=#5e6035");// change couleur							
								num++;
							} else {
								/*
								edgesRetour[num] = graph.insertEdge(parent,
										null, couts[i][j], vertexsAller[i],
										vertexsAller[j], "strokeColor=#34495e");
										*/
								edgesRetour[num] = graph.insertEdge(parent,
										null, couts.get(new PaireVertex(new Vertex(i), new Vertex(j))), vertexsAller[i],
										vertexsAller[j], "strokeColor=#34495e");
								num++;
							}
						}
					}
				}

			}
			
			
			// controler les couleurs
			graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", vertexsAller);
			graph.setCellStyles(mxConstants.STYLE_OPACITY, "1", vertexsRetour);
			
			/*
			 * Object e23 = graph.insertEdge(parent, null, "Edge Optimale", v2,
			 * v3, "strokeColor=red"); //
			 * graph.setCellStyles(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, //
			 * "green", new Object[]{v1});
			 * graph.setCellStyles(mxConstants.STYLE_FONTCOLOR, "white", new
			 * Object[] { v1, v2, v3 });
			 */			
		} finally {
			graph.getModel().endUpdate();
		}
		
		// graph.setEnabled(false);
		graph.setCellsMovable(false);
		graph.setAllowDanglingEdges(false);
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(new Dimension(width, height));
		this.removeAll();
		this.add(graphComponent);
		graph.getModel().endUpdate();
	}
	
	
	public void showSoulutionOptimale(LinkedList<Integer> cheminsOptimals) {
		graph.getModel().beginUpdate();
		try {
			
			if(nbV>50){
				
			}else{
				//supprimer tous les chemins
				Object[][] edgesRemove = new Object[nbV * (nbV - 1)][nbV - 1];
				int k = 0;
				for (int i = 0; i < nbV; i++) {
					for (int j = 0; j < nbV; j++) {
						if (i != j) {
							/*
							if ((i > j) && (!(couts[i][j].equals(couts[j][i])))) {
								edgesRemove[k] = graph.getEdgesBetween(
										vertexsRetour[i], vertexsRetour[j]);
										*/
							if ((i > j) && (!(couts.get(new PaireVertex(new Vertex(i), new Vertex(j))).equals(couts.get(new PaireVertex(new Vertex(j), new Vertex(i))))))) {
								edgesRemove[k] = graph.getEdgesBetween(
										vertexsRetour[i], vertexsRetour[j]);
							} else {
								edgesRemove[k] = graph.getEdgesBetween(
										vertexsAller[i], vertexsAller[j]);
							}
							k++;
						}
					}
				}
				for (k = 0; k < nbV * (nbV - 1); k++) {
					for (Object edge : edgesRemove[k]) {
						graph.getModel().remove(edge);
					}
				}
			}
		
			//ajouter les chemins optimals
			Object[] edgesOptimals = new Object[nbV];
			for(int i=0; i<nbV-1; i++){
			edgesOptimals[i] = graph.insertEdge(graph.getDefaultParent(), null,
					"", vertexsAller[cheminsOptimals.get(i)],
					vertexsAller[cheminsOptimals.get(i+1)], "strokeColor=#34495e");
			}
			edgesOptimals[nbV-1] = graph.insertEdge(graph.getDefaultParent(), null,
					"", vertexsAller[cheminsOptimals.get(nbV-1)],
					vertexsAller[cheminsOptimals.get(0)], "strokeColor=#34495e");
			
		} finally {
			graph.getModel().endUpdate();
		}
		/*mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(new Dimension(width, height));
		this.removeAll();
		this.add(graphComponent);
		System.out.println("validate ?");
		validate();
		System.out.println("j'ai valider");*/
	}
}
