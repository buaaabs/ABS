package hha.chart;

/**
 * 如果startActivity之前没有setValue，则抛出此异常
 * @author hyf
 *
 */
public class NotSetValueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String mesString = "没有设置数据";
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return mesString;
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		System.out.println(mesString);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mesString;
	}
}
