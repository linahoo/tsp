package tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class desertPSO {
	static final int Swarm = 50;
	static final int TestNumber = 1500;
	private static final int V_MAX = 12;
	static final String File = "berlin52.txt";
	private static ArrayList<Particle> particles = new ArrayList<Particle>();
	private static ArrayList<Particle> copyParticles = new ArrayList<Particle>();
	private static City[] map ;
	static  int CITY_COUNT = 0;
	private static Particle gBestParticle = new Particle();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		inicialize();
		
		for(int i = 0 ; i < TestNumber;i++){
			countAndfindBest();
			velocity();
			nextPosition();
//			System.out.println("***");
//			printRoute();
		}
		System.out.println("***");
		printRoute();
	}
	private static void inicialize() {
		map = Common.getCities(File);
		CITY_COUNT = map.length;
		
		for(int i=0;i<Swarm;i++){
			Particle p = new Particle();
			for(int j = 0; j < CITY_COUNT; j++)
	        {
	            p.setCity(j, j);
	        }

			particles.add(p);
	        for(int j = 0; j < CITY_COUNT; j++)
	        {
	        	randomRoute(p);
	        }
	        
	        getTotalDistance(p);
	    } // i
		printRoute();
	}
	private static class Particle {
	    private int [] mRoute = new int[CITY_COUNT];
	    private double mpBest = 0;
	    private double mVelocity = 0.0;
	    private int [] pBestRoute = new int [CITY_COUNT];
	
	    
	    public int compareTo(Particle p)
	    {
	    	if(this.getBest() < p.getBest()){
	    		return -1;
	    	}else if(this.getBest() > p.getBest()){
	    		return 1;
	    	}else{
	    		return 0;
	    	}
	    }
	    public void setPBestRoute(Particle p){
	    	for(int i = 0;i < CITY_COUNT; i ++){
	    	this.pBestRoute[i] = p.getCity(i);
	    	}
	    }
	    public int[] getPBestRoute(){
	    	
	    	return this.pBestRoute;
	    	
	    }
	    public int getCity(final int index)
	    {
	    	return this.mRoute[index];
	    }
	    
	    public void setCity(final int index, final int value)
	    {
	        this.mRoute[index] = value;
	        return;
	    }
	
	    public double getBest()
	    {
	    	return this.mpBest;
	    }

	    public void setBest(final double value)
	    {
	    	this.mpBest = value;
	    	return;
	    }
	
	    public double getVelocity()
	    {
	    	return this.mVelocity;
	    }
	    
	    public void setVelocity(final double velocityScore)
	    {
	       this.mVelocity = velocityScore;
	       return;
	    }
    } 
	private static void getTotalDistance(Particle p) {
			    
	    p.setBest(0.0);
	    
	    for(int i = 0; i < CITY_COUNT; i++)
	    {
	        if(i == CITY_COUNT - 1){
	        	p.setBest(p.getBest() + getDistance(p.getCity(CITY_COUNT - 1), p.getCity(0))); // Complete trip.
	        }else{
	        	p.setBest(p.getBest() + getDistance(p.getCity(i), p.getCity(i + 1)));
	        }
	    }
		
	}
	private static double getDistance(int firstCity, int secondCity) {
		City cityA = null;
		City cityB = null;

	    cityA = map[firstCity];
	    cityB = map[secondCity];

	    double x = cityA.getX() - cityB.getX();
		double y = cityA.getY() - cityB.getY();
		
		return Math.sqrt(x*x+y*y);
	}
	private static void randomRoute(Particle p) {
		int cityA = new Random().nextInt(CITY_COUNT);
		int cityB = new Random().nextInt(CITY_COUNT);

				
		int temp = p.getCity(cityA);
		p.setCity(cityA, p.getCity(cityB));
		p.setCity(cityB, temp);
		
	}
	private static void printRoute() {
		Particle p;
		for(int i = 0; i < Swarm; i++){
            p = particles.get(i);
            System.out.print("Route: ");
            for(int j = 0; j < CITY_COUNT; j++)
            {
                System.out.print(p.getCity(j) + ", ");
            } // j

            getTotalDistance(p);
            System.out.print("Distance: " + p.getBest() + "\n");

            }
	}
	private static void printRoute(Particle p) {


            System.out.print("Route: ");
            for(int j = 0; j < CITY_COUNT; j++)
            {
                System.out.print(p.getCity(j) + ", ");
            } // j

            getTotalDistance(p);
            System.out.print("Distance: " + p.getBest() + "\n");

      
	}
	private static void countAndfindBest() {
		copyParticles = copyParticles();

		int changes = 0;
		do{
			changes = 0;
			int listSize = particles.size();
			for(int i = 0; i < listSize - 1; i++)
			{
				if(particles.get(i).compareTo(particles.get(i + 1)) == 1){
					Particle temp = particles.get(i);
					particles.set(i, particles.get(i + 1));
					particles.set(i + 1, temp);
					changes++;
				}
			}

		}while(changes != 0);
		
		gBestParticle = particles.get(0);
	}
	
	private static ArrayList<Particle> copyParticles() {
		ArrayList<Particle> ps = new ArrayList<Particle>();
		
		for(int i=0;i<particles.size();i++){
			ps.add(particles.get(i));
		}
		return ps;
	}
	private static void velocity() {
		double worstResults = 0;

		double vValue = 0.0;
		
		// after sorting, worst will be last in list.
	    worstResults = copyParticles.get(Swarm - 1).getBest();

	    for(int i = 0; i < Swarm; i++)
	    {
	        vValue = (V_MAX * particles.get(i).getBest()) / worstResults;
	        //路線距離比最差值長，全換
	        if(particles.get(i).getBest() > worstResults){
	        	particles.get(i).setVelocity(V_MAX);
	        }
	        //距離越接近最差值，換越多個
	        else{
	        	particles.get(i).setVelocity(vValue);
	        }
	        
	        particles.get(i).setPBestRoute(particles.get(i));
	    }
		
	}
	private static void nextPosition() {
		// Best is at index 0, so start from the second best.
				
	    for(int i = 1; i < Swarm; i++){
	    		int point = 0;
	    		Particle p = particles.get(i);
	    		// The higher the velocity score, the more changes it will need.
	    		int changes = (int)Math.floor(Math.abs(particles.get(i).getVelocity()));
	    		int head = new Random().nextInt(CITY_COUNT/2);
//	    		System.out.println("&&"+changes);
	    		List<Boolean> visitList=new ArrayList<Boolean>(Arrays.asList(new Boolean[CITY_COUNT]));
	    		for(int j = 0 ;j<visitList.size();j++){
	    			visitList.set(j, false);
	    		}
	    		
	        for(int j = head;j < (head+changes);j++){
	        	p.setCity(point,gBestParticle.getCity(j));
	        	visitList.set(gBestParticle.getCity(j),true);
//	        	System.out.println("@"+j+"@"+gBestParticle.getCity(j));
	        	point++;
	        }
	        int[] pBestRoute = p.getPBestRoute();
	        for(int k = 0;k < pBestRoute.length; k++){
//	        	System.out.println("*"+k+"*"+pBestRoute[k]);
	        	if(!visitList.get(pBestRoute[k]) && point < CITY_COUNT){
	        		p.setCity(point,pBestRoute[k]);
	        		visitList.set(pBestRoute[k],true);
//	        		System.out.println("#"+k+"#"+pBestRoute[k]);
	        		point++;
	        	}
	        	
	        }
	        // Update pBest value.
	        getTotalDistance(p);
	    } // i
		
	}



}
