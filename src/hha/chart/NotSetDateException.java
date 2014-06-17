package hha.chart;


/**
 * 如果startActivity之前没有setDate，则抛出此异常
 * @author hyf
 *
 */
public class NotSetDateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private final String mesString = "没有设置日期";
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
