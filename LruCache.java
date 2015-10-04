import java.util.*;
import java.lang.*;

public class LruCache{
	
	public static class Entry{
		String key;
		Object value;
		Entry pre,nex;
	}		

	public static void main(String[] args){
		LruCache cache = new LruCache(4);
		for(int i=0;i<5;i++){
			Entry e = new Entry();
			e.key=i+"";
			e.value = i;
			cache.put(e.key,e);
		}
		Entry en = cache.get("0");
		System.out.println(en==null?"null":en.value+"");
		en = cache.get("1");
		System.out.println(en==null?"null":en.value+"");
	}
	
	int CacheSize;
	int currentSize;
	Entry head;
	Entry last;
	Hashtable<String,Entry> nodes;

	public LruCache(int size){
		CacheSize = size;
		currentSize = 0;
		head = last = null;
		nodes = new Hashtable<String,Entry>(size);	
	}

	private void moveToHead(Entry entry){
		if (head==null||last==null){
			head=last =entry;
			return ;	
		}
		if(head==entry) return;
		if(entry.pre!=null) entry.pre.nex = entry.nex;
		if(entry.nex!=null) entry.nex.pre = entry.pre;
		if(last==entry) last = last.pre;

		head.pre = entry;
		entry.nex = head;
		head = entry;
		head.pre = null;
		
		
	}
	
	private void removeLast(){
		if(last!=null){
			last= last.pre;
			if(last==null) head=null;
			else last.nex = null;
		}	
	}

	public void put(String key,Entry entry){
		Entry old = nodes.get(key);
		if(old==null){
			if(currentSize>=CacheSize){
				nodes.remove(last.key);
				removeLast();
				currentSize--;
			}
			nodes.put(key,entry);
			moveToHead(entry);
			currentSize++;
		}
	}

	public Entry get(String key){
		Entry entry = nodes.get(key);
		if(entry!=null){
			moveToHead(entry);
			return entry;
		}else
			return null;
	}

	public void remove(String key){
		Entry entry = nodes.get(key);
		if(entry!=null){
			removeEntry(entry);
			nodes.remove(key);
			currentSize--;
		}
	}

	private void removeEntry(Entry entry){
		if(entry.pre!=null) entry.pre.nex = entry.nex;
		if(entry.nex!=null) entry.nex.pre = entry.pre;
		if(entry==last) last=last.pre;
		if(entry==head) head = head.nex;
	}

/*
	public String toString(){
		
	}
*/
}
