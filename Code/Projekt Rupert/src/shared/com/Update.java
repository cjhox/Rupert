package shared.com;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Update<T> implements Serializable {

	private static final long	serialVersionUID	= 6807225796840524275L;

	protected List<T>			datas				= new ArrayList<T>();

	public Update() {
		super();
	}

	public boolean add(T data) {
		return datas.add(data);
	}

	public List<T> getUpdate() {
		return datas;
	}

	@Override
	public String toString() {
		return "Update [datas=" + datas + "]";
	}
}