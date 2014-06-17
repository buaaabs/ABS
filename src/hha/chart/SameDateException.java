package hha.chart;
/**
 * 如果传入日期有重复，会造成同一时间有两个值，则抛出此异常
 * @author hyf
 *
 */
public class SameDateException extends Exception {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private final String mesString = "日期有重复值！";
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
