package leetcode1round;

import static org.junit.Assert.*;

import org.junit.Test;

public class AlienDict269Test {
	public String[] s1s = new String[] {"wrt","wrf","er","ett","rftt"};
	public String ans1 = "wertf";
	public AlienDict269 a = new AlienDict269();

	@Test
	public void test() {
		assertEquals(ans1, a.alienOrder(s1s));
	}

}
