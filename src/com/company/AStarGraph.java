package com.company;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AStarGraph {
    private ArrayList<Vertex> vertices;
    public AStarGraph() {
        vertices=new ArrayList<Vertex>();
    }
    public void addvertex(Vertex v) {
        vertices.add(v);
    }
    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }

    public boolean A_Star(Vertex start, Vertex destination)
    {   if (start==null || destination==null)
        return false;
        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>();
        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>();
        Openlist.offer(start);
        Vertex Current;
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;
        //Initialize h with chosen heuristic
        Scanner scanner = new Scanner(System.in);
        System.out.println("choose number 1 for Manhatten or 2 for Euclidean:");
        int number = scanner.nextInt();
        for (int i =0; i<vertices.size();i++)
        {
            if(number==1){
            vertices.get(i).seth(Manhattan(vertices.get(i),destination));
            System.out.println(Manhattan(vertices.get(i),destination));
            }
            if (number==2) {
                vertices.get(i).seth(Euclidean(vertices.get(i), destination));
                System.out.println(Euclidean(vertices.get(i),destination));
            }

        }
        Openlist.remove(start);
        start.setg(0.0);
        start.calculatef();
        Openlist.offer(start);
        //Start algorithm
        System.out.println("Start Algorithm");
        //Implement the Astar algorithm

        while (!Openlist.isEmpty()){
            Current = Openlist.remove();// maybe
            if (Current.equals(destination)){
                return true;
            }
                Closedlist.add(Current);
            CurrentNeighbors = Current.getNeighbours();
                for (int i = 0; i <CurrentNeighbors.size() ; i++) {
                    Neighbor = Current.getNeighbours().get(i);

                   double tempgofv = Current.getg() + Current.getNeighbourDistance().get(i);

                    if (tempgofv<Neighbor.getg()){
                     Neighbor.setPrev(Current); //= Current;
                     Neighbor.setg(tempgofv); //= tempgofv;
                     Neighbor.calculatef();


                     if (!Closedlist.contains(Neighbor) && !Openlist.contains(Neighbor)){
                         Openlist.offer(Neighbor);
                     }
                     else if (Closedlist.contains(Neighbor)&& Openlist.contains(Neighbor)){
                         Openlist.remove(Neighbor);
                         Openlist.offer(Neighbor);
                     }
                    }


                }
            }


        return false;
    }
    public Double Manhattan(Vertex from,Vertex goal){
        double x = Math.abs(goal.getx()-from.getx());
        double y = Math.abs(goal.gety()-from.gety());
        double z = (x+y);
        //System.out.println("Manhatten path:"+z);
        return z;
    }


    public Double Euclidean( Vertex from,Vertex to){
        //Implement this
        double x = Math.abs(to.getx()-from.getx());
        double y = Math.abs(to.gety()-from.gety());
        double z = Math.sqrt((x*x)+(y*y));


        //System.out.println("euclidean path:"+z);
        return z;
    }
}

class Vertex implements Comparable<Vertex>{
    private String id;
    private ArrayList<Vertex> Neighbours=new ArrayList<Vertex>();
    private ArrayList<Double> NeighbourDistance =new ArrayList<Double>();
    private Double f;
    private Double g;
    private Double h;
    private Integer x;
    private Integer y;
    private Vertex prev =null;
    public Vertex(String id, int x_cor,int y_cor){
        this.id=id;
        this.x=x_cor;
        this.y = y_cor;
        f=Double.POSITIVE_INFINITY;
        g=Double.POSITIVE_INFINITY;
        h=0.0;
    }
    public void addOutEdge(Vertex toV, Double dist){
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }
    public ArrayList<Vertex> getNeighbours(){
        return Neighbours;
    }
    public ArrayList<Double> getNeighbourDistance(){
        return NeighbourDistance;
    }
    public String getid(){ return id;};
    public Integer getx(){ return x; }
    public Integer gety(){return y; }
    public Double getf() { return f; }
    public void calculatef(){ f=g+h; }

    public Double getg() { return g; }

    public void setg(Double newg){ g=newg; }
    public Double geth(){ return h; }
    public void seth(Double estimate){ h=estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v){prev=v;}
    public void printVertex(){
        System.out.println(id + " g: "+g+ ", h: "+h+", f: "+f);
    }
    @Override
    public int compareTo(Vertex o) {
//Implement this

        calculatef();
        if (this.getf() > o.getf())
            return 1;
        if (this.getf() < o.getf())
            return -1;
//
        return 0;
    }
}

