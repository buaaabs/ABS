package hha.chart;

/**
 * 如果startActivity时检测到数据数量与日期数量不符，则抛出此异常
 * @author hyf
 *
 */
public class DateNotMatchValueException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private final String mesString = "日期数量不等于数据数量";
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