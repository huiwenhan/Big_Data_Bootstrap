package me.huiwen.example.hypergraphdb.minimalistic.sample1;

import java.util.HashSet;
import java.util.Set;

import org.hypergraphdb.HGHandle;
import org.hypergraphdb.HGPlainLink;

public class HGBergeLink extends HGPlainLink{

private int tailIndex = 0;
	
	public HGBergeLink(HGHandle...targets)
	{
	    super(targets);
	}
	
	public HGBergeLink(int tailIndex, HGHandle...targets)
	{
		super(targets);
		this.tailIndex = tailIndex;
	}
	
	public HGBergeLink(HGHandle [] head, HGHandle [] tail)
	{
		HGHandle [] targets = new HGHandle[head.length + tail.length];
		System.arraycopy(head, 0, targets, 0, head.length);
		System.arraycopy(tail, 0, targets, head.length, tail.length);
		tailIndex = head.length;
	}

	public Set<HGHandle> getHead()
	{
		HashSet<HGHandle> set = new HashSet<HGHandle>();
		for (int i = 0; i < tailIndex; i++)
			set.add(getTargetAt(i));
		return set;
	}
	
	public Set<HGHandle> getTail()
	{
		HashSet<HGHandle> set = new HashSet<HGHandle>();
		for (int i = tailIndex; i < getArity(); i++)
			set.add(getTargetAt(i));
		return set;		
	}
	
	public int getTailIndex()
	{
		return tailIndex;
	}

	public void setTailIndex(int tailIndex)
	{
		this.tailIndex = tailIndex;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
