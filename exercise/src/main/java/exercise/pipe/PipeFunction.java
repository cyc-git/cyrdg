package exercise.pipe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

public class PipeFunction {

	@SafeVarargs
	public final <T> T pipe(T param, Function<T, T>... functions) {
		return this.innerPipe(param, new LinkedList<Function<T, T>>(Arrays.asList(functions)));
	}

	private <T> T innerPipe(T param, Queue<Function<T, T>> functions) {
		if (functions.isEmpty()) {
			return param;
		}
		return innerPipe(functions.poll().apply(param), functions);
	}
}
