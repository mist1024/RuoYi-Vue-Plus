import org.junit.Test;
/**
 * @author qianlan
 * @version 1.0
 * @date 2021/8/18 11:18
 */
public class testNode {

	@Test
	public void test1(){
		String name = "12,3,1,哲, ,90";
		String[] nameArray = name.split(",");
		System.out.println(nameArray.length);
		for (String s : nameArray) {
			System.out.println(s);
		}
	}

}
