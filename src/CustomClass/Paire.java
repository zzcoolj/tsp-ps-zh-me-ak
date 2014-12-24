package CustomClass;

public class Paire<T1,T2> {
	
	T1 first;
	T2 second;
	
	public Paire() {
		// TODO Auto-generated constructor stub
		first = null;
		second = null;
	}
	
	public Paire(T1 a, T2 b)
	{
		this.first = a;
		this.second = b;
	}
	
	public T1 getFirst()
	{
		return first;
	}
	
	public T2 getSecond()
	{
		return second;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		
		
		try {
			
			Paire<T1,T2> other = (Paire<T1,T2>)obj;
			
			boolean b1 = this.getFirst().equals(other.getFirst()) || this.getFirst().equals(other.getSecond());
			boolean b2 = this.getSecond().equals(other.getSecond()) || this.getSecond().equals(other.getFirst());
			
			if(b1&&b2)
				return true;
			
		} catch (ClassCastException e) {
			// TODO: handle exception
			
			return false;
		}
		
		return false;
		
	}

}
