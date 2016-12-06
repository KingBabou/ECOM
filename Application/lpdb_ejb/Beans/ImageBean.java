package Beans;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "IMAGE")
public final class ImageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "ID_ANNONCE")
	private int id_annonce;

	@Column(name = "URL")
	private String url;

	public ImageBean() {}

	public ImageBean(int id, int id_annonce, String url) {
		this.id = id;
		this.id_annonce = id_annonce;
		this.url = url;
	}

	public int getId() {return this.id;}
	public void setId(int id) {this.id = id;};

	public int getIdAnnonce() {return this.id_annonce;}
	public void setIdAnnonce(int id_annonce) {this.id_annonce = id_annonce;}

	public String getUrl() {return this.url;}
	public void setUrl(String url) {this.url = url;}

	@Override
	public String toString() {
		return "{" + "\"type\":\"Image\"" +
				", \"id\":" + this.id +
				", \"id_annonce\":\"" + this.id_annonce + "\"" +
				", \"url\":\"" + this.url + "\"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ImageBean)) return false;

		ImageBean image = (ImageBean)o;

		if (this.id != image.id) return false;
		if (this.id_annonce != image.id_annonce) return false;
		if (!this.url.equals(image.url)) return false;

		return true;
	}

}
