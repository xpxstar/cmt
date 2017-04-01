package cn.ac.iscas.cmt.v2.model.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Category {
	@Setter @Getter private String name;
	@Setter @Getter private String path;
	@Setter @Getter private List<Category> sub;
	public Category(String n) {
		path=n;
		String[] cates = n.split("/");
		name = cates[cates.length-1];
	}
	public void fillSub(String... subs){
		if(null == sub){
			sub = new ArrayList<>();
		}
		for(String str:subs){
			sub.add(new Category(str));
		}
	}
	
	public void addSub(Category... newone){
		if(null == sub){
			sub = new ArrayList<>();
		}
		for(Category str:newone){
			sub.add(str);
		}
	}
}
