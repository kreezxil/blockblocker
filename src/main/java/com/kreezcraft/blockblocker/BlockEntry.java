package com.kreezcraft.blockblocker;

import java.util.ArrayList;
import java.util.List;

public class BlockEntry {
	
	public static final BlockEntry EMPTY = new BlockEntry("",0,null);
	
	private String id;
	private List<Integer> meta = new ArrayList<Integer>();
	private List<Integer> dim = new ArrayList<Integer>();
	
	
	public BlockEntry(String id, int meta, Integer dimIn){
		this.id = id;
		this.meta.add(meta);
		if(dimIn != null){
			this.dim.add(dimIn);
		}
	}
	
	public BlockEntry(String id, int meta){
		this(id,meta,null);
	}

	public String getId() {
		return id;
	}

	public List<Integer> getMeta() {
		return meta;
	}

	public List<Integer> getDim() {
		if(dim == null){
			List<Integer> tmp = new ArrayList<Integer>();
			tmp.clear();
			return tmp;
		}
		return dim;
	}
	
	public void addMeta(int in){
		if(!meta.contains(in)){
			meta.add(in);
		}
	}
	
	public void addDim(int in){
		if(!dim.contains(in)){
			dim.add(in);
		}
	}
	
	public boolean isEmpty(BlockEntry entry){
		if(entry.getId()=="") {
			return true;
		} else {
			return false;
		}
	}
	
	public String makeString() {
		StringBuilder string = new StringBuilder();
		//string.setLength(1000);
		string.append(this.getId() + " ");
		for(int meta : this.getMeta()){
			string.append(meta + ", ");
		}
		if(!this.getDim().isEmpty()){
			string.append("in dimentions ");
			for(int dim : this.getDim()){
				string.append(dim + " ");
			}
		}
		string.trimToSize();
		return string.toString();
	}
}
