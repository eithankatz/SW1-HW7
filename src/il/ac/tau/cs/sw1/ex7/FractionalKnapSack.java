package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>
{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1)
    {
        capacity = c;
        lst = lst1;
    }

    public static class Item  implements Comparable<Item>
    {
        double weight, value;
        Item(double w, double v) 
        {
            weight = w;
            value = v;
        }

        @Override
        public String toString() 
        {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }

		public int compareTo(Item other) 
		{
			// comparison by relative-weight		
			return Double.compare(this.weight/this.value, other.weight/other.value); 
		}
    }

    @Override
    public Iterator<Item> selection() 
    {
    	Collections.sort(lst);
        return lst.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) 
    {
    	return sum(candidates_lst) < capacity;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) 
    {
    	if (sum(candidates_lst) + element.weight <= capacity)
    	{//Adding entire item
    		candidates_lst.add(element);
    	}
    	else
    	{//Adding part of the item
    		double weightDiff = capacity - sum(candidates_lst);			//How much weight is missing
    		double relativePart = weightDiff/element.weight;	//What part of element to add
    		if(relativePart > 0.0)
    		{
    			Item relativeElement = new FractionalKnapSack.Item(relativePart * element.weight,relativePart * element.value);
    			candidates_lst.add(relativeElement);
    		}
    	}
    }

    @Override
    public boolean solution(List<Item> candidates_lst) 
    {
        return (sum(candidates_lst) == capacity) || (candidates_lst.size() == lst.size());
    }
	
	public double sum(List<Item> list) 
	{
		 double sum = 0; 
	     for (int i = 0; i < list.size(); i++)
	            sum += list.get(i).weight;
	     return sum;
	}
}
