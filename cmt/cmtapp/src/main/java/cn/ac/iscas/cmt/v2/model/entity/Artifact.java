package cn.ac.iscas.cmt.v2.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "artifact")
public class Artifact{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@Getter @Setter private Long id;
	
	@Getter @Setter private String name;
	@Getter @Setter private String type;
	@Getter @Setter private String author;
	@Getter @Setter private String component;
	@Getter @Setter private String version;
	@Getter @Setter private int download;
	@Getter @Setter private String addition;
	@Getter @Setter private Long vernum;
	@Getter @Setter private Date lastDate;
	@Getter @Setter private int score;
	@Getter @Setter private int alldown;
	@Getter @Setter private String tags;
	@Getter @Setter private String category;
	@Getter @Setter private String github;
//	@Getter @Setter private List<Dependency> dependencies;
//	@Getter @Setter private List<Dependency> os_support;
//	@Getter @Setter private List<Dependency> requirements;
	
	
	
	
	
	public String tosql(){
		StringBuffer sb = new StringBuffer();
		sb.append(author);
		sb.append(',');
		sb.append(name);
		sb.append(',');
		sb.append(name);
		sb.append(',');
		sb.append(version);
		sb.append(',');
		sb.append(download);
		sb.append(',');
		sb.append(lastDate);
		sb.append(',');
		sb.append(alldown);
		sb.append(',');
		sb.append(vernum);
		sb.append(',');
		sb.append(',');
		sb.append(',');
		sb.append(',');
		sb.append(',');
		sb.append(addition);
		sb.append(',');
		if (null != tags) {
			sb.append(String.join("/", tags));
		}
		sb.append(',');
		sb.append(',');
		sb.append(github);
		return sb.toString();
	}
	public String toDocument(){
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append('/');
		sb.append(tags);
		sb.append('/');
		sb.append(addition);
		return sb.toString();
	}
	public String toCsvShort(){
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(',');
		sb.append(addition);
		return sb.toString();
	}
	
}
