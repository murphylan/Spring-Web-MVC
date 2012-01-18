package san.entity.tree;

public class TreeDto {

	private AttrDto attr = new AttrDto();
	private DataDto data = new DataDto();
	private String state;

	public AttrDto getAttr() {
		return attr;
	}

	public void setAttr(AttrDto attr) {
		this.attr = attr;
	}

	public DataDto getData() {
		return data;
	}

	public void setData(DataDto data) {
		this.data = data;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
