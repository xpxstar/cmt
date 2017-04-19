package cn.ac.iscas.cmt.v2.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "c_file")
public class FileSource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@Getter @Setter private Long id;
	
	@Getter @Setter private String md5;
	@Getter @Setter private String credential;
	@Getter @Setter private String typ;
}
