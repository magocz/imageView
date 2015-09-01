package dataprovider.data;

public class ImageVO {
	private String name;
	private int id;
	/*
	 * REV: prefiks powinien byc zdefiniowany w klasie, w ktorej tworzony jest obiekt Image
	 * Klasa VO tylko przechowuje dane.
	 */
	private String path = "file:///";

	public ImageVO() {
	}

	public ImageVO(String name, int id, String path) {
		this.name = name;
		this.id = id;
		this.path += path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
