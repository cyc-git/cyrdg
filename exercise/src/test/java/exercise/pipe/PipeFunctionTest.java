package exercise.pipe;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;

public class PipeFunctionTest {

	@Test
	public void pipeTest() {
		PipeFunction pipeFunction = new PipeFunction();
		Function<Integer, Integer> increment = (i) -> ++i;

		assertEquals(Integer.valueOf(5), pipeFunction.pipe(5));
		assertEquals(Integer.valueOf(6), pipeFunction.pipe(5, increment));
		assertEquals(Integer.valueOf(8), pipeFunction.pipe(5, increment, increment, increment));
	}
}
